package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;

public final class ARTS_G_AbydosSummerVacation extends Card {

    public ARTS_G_AbydosSummerVacation()
    {
        setImageSets(Mask.VERTICAL+"WX25-CD1-10");

        setOriginalName("アビドスの夏休み");
        setAltNames("アビドスノナツヤスミ Abidosu no Natsu Yasumi");
        setDescription("jp",
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それをエナゾーンに置く。\n" +
                "&E４枚以上@0代わりに対戦相手のシグニ１体を対象とし、それをエナゾーンに置く。"
        );

        setName("en", "Abydos Summer Vacation");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 8000 or less, and put it into the ener zone.\n" +
                "&E4 or more@0 Instead, target 1 of your opponent's SIGNI, and put it into the ener zone."
        );

        setName("zh_simplified", "阿拜多斯的暑假");
        setDescription("zh_simplified", 
                "对战对手的力量8000以下的精灵1只作为对象，将其放置到能量区。\n" +
                "&E4张以上@0作为替代，对战对手的精灵1只作为对象，将其放置到能量区。\n" +
                "（你的分身废弃区有4张以上的必杀时，则&E4张以上@0后的文字变为有效）"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff).setRecollect(4);
        }
        
        private void onARTSEff()
        {
            TargetFilter filter = new TargetFilter(TargetHint.ENER).OP().SIGNI();
            if(!getAbility().isRecollectFulfilled()) filter = filter.withPower(0,8000);
            
            CardIndex target = playerTargetCard(filter).get();
            putInEner(target);
        }
    }
}
