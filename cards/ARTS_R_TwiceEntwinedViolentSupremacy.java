package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_R_TwiceEntwinedViolentSupremacy extends Card {

    public ARTS_R_TwiceEntwinedViolentSupremacy()
    {
        setImageSets("WX24-D2-07");

        setOriginalName("烈覇二絡");
        setAltNames("レッパニラク Reppaniraku");
        setDescription("jp",
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "&E４枚以上@0代わりに対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Twice Entwined Violent Supremacy");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 8000 or less, and banish it.\n" +
                "&E4 or more@0 Instead, target 1 of your opponent's SIGNI, and banish it."
        );

		setName("zh_simplified", "烈霸二络");
        setDescription("zh_simplified", 
                "对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n" +
                "&E4张以上@0作为替代，对战对手的精灵1只作为对象，将其破坏。\n" +
                "（你的分身废弃区有4张以上的必杀时，则&E4张以上@0后的文字变为有效）\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
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
            TargetFilter filter = new TargetFilter(TargetHint.BANISH).OP().SIGNI();
            if(!getAbility().isRecollectFulfilled()) filter = filter.withPower(0,8000);
            CardIndex target = playerTargetCard(filter).get();
            banish(target);
        }
    }
}

