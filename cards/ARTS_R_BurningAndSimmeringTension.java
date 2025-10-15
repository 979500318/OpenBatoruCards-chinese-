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
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class ARTS_R_BurningAndSimmeringTension extends Card {

    public ARTS_R_BurningAndSimmeringTension()
    {
        setImageSets("SP23-010", "WDK01-009");

        setOriginalName("剣抜弩焼");
        setAltNames("ケンバツドショウ Kenbatsu Doshou");
        setDescription("jp",
                "このアーツはあなたのセンタールリグが赤の場合にしか使用できない。\n\n" +
                "対戦相手のシグニ１体を対象とし、それをバニッシュする。手札を２枚捨てる。"
        );

        setName("en", "Burning and Simmering Tension");
        setDescription("en",
                "This ARTS can only be used if your center LRIG is red.\n\n" +
                "Target 1 of your opponent's SIGNI, and banish it. Discard 2 cards from your hand."
        );

		setName("zh_simplified", "剑霸弩烧");
        setDescription("zh_simplified", 
                "这张必杀只有在你的核心分身是红色的场合才能使用。\n" +
                "对战对手的精灵1只作为对象，将其破坏。手牌2张舍弃。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
        }
        
        private ConditionState onARTSEffCond()
        {
            return getLRIG(getOwner()).getIndexedInstance().getColor().matches(CardColor.RED) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
            
            discard(2);
        }
    }
}
