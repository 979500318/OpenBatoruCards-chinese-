package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W1_PeleHolyAngel extends Card {
    
    public SIGNI_W1_PeleHolyAngel()
    {
        setImageSets("WXDi-P02-047");
        
        setOriginalName("聖天　ペレ");
        setAltNames("セイテンペレ Seiten Pere");
        setDescription("jp",
                "@A %X #D：あなたの他の＜天使＞のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Pele, Blessed Angel");
        setDescription("en",
                "@A %X #D: Return another target <<Angel>> SIGNI on your field to its owner's hand."
        );
        
        setName("en_fan", "Pele, Holy Angel");
        setDescription("en_fan",
                "@A %X #D: Target 1 of your other <<Angel>> SIGNI, and return it to your hand."
        );
        
		setName("zh_simplified", "圣天 佩蕾");
        setDescription("zh_simplified", 
                "@A %X横置:你的其他的<<天使>>精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerActionAbility(new AbilityCostList(new EnerCost(Cost.colorless(1)), new DownCost()), this::onActionEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ANGEL).except(getCardIndex())).get();
            addToHand(target);
        }
    }
}
