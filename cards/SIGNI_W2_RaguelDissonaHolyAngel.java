package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_W2_RaguelDissonaHolyAngel extends Card {

    public SIGNI_W2_RaguelDissonaHolyAngel()
    {
        setImageSets("WXDi-P13-059");

        setOriginalName("聖天　ラグエル//ディソナ");
        setAltNames("セイテンラグエルディソナ Seiten Ragueru Disona");
        setDescription("jp",
                "@A #D @[エナゾーンから#Sのカード１枚をトラッシュに置く]@：対戦相手のレベル１のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Raguel//Dissona, Blessed Angel");
        setDescription("en",
                "@A #D @[Put a #S card from your Ener Zone into your trash]@: Return target level one SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Raguel//Dissona, Holy Angel");
        setDescription("en_fan",
                "@A #D @[Put 1 #S @[Dissona]@ card from your ener zone into the trash]@: Target 1 of your opponent's level 1 SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "圣天 拉贵尔//失调");
        setDescription("zh_simplified", 
                "@A 横置从能量区把#S的牌1张放置到废弃区:对战对手的等级1的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerActionAbility(new AbilityCostList(new DownCost(), new TrashCost(new TargetFilter().dissona().fromEner())), this::onActionEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(1)).get();
            addToHand(target);
        }
    }
}

