package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_G1_TakaoniFirstPlay extends Card {

    public SIGNI_G1_TakaoniFirstPlay()
    {
        setImageSets("WXDi-P04-072");

        setOriginalName("壱ノ遊　タカオニ");
        setAltNames("イチノユウタカオニ Ichi no Yuu Takaoni");
        setDescription("jp",
                "@E %X：あなたのエナゾーンからレベル２のシグニ１枚を対象とし、それと場にあるこのシグニの場所を入れ替える。"
        );

        setName("en", "Takaoni, First Play");
        setDescription("en",
                "@E %X: Swap this SIGNI on your field with target level two SIGNI in your Ener Zone. "
        );
        
        setName("en_fan", "Takaoni, First Play");
        setDescription("en_fan",
                "@E %X: Target 1 level 2 SIGNI from your ener zone, and exchange its position with this SIGNI."
        );

		setName("zh_simplified", "壹之游 高鬼");
        setDescription("zh_simplified", 
                "@E %X从你的能量区把等级2的精灵1张作为对象，将其与场上的这只精灵的场所交换。（其的@E能力发动）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MOVE).own().SIGNI().withLevel(2).fromEner().playableAs(getCardIndex())).get();
            
            if(target != null)
            {
                putInEner(getCardIndex());
                putOnField(target, getCardIndex().getPreTransientLocation());
            }
        }
    }
}
