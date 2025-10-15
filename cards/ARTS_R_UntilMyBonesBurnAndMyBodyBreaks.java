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
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class ARTS_R_UntilMyBonesBurnAndMyBodyBreaks extends Card {

    public ARTS_R_UntilMyBonesBurnAndMyBodyBreaks()
    {
        setImageSets("WDK01-006", "SPK16-5B");

        setOriginalName("焚骨砕身");
        setAltNames("フンコツサイシン Funkotsu Saishin");
        setDescription("jp",
                "対戦相手のシグニ２体を対象とし、それらをバニッシュする。"
        );

        setName("en", "Until My Bones Burn and My Body Breaks");
        setDescription("en",
                "Target 2 of your opponent's SIGNI, and banish them."
        );

		setName("zh_simplified", "焚骨碎身");
        setDescription("zh_simplified", 
                "对战对手的精灵2只作为对象，将这些破坏。（只有1只精灵不能使用）\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 3) + Cost.colorless(3));
        setUseTiming(UseTiming.ATTACK);

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
            return getSIGNICount(getOpponent()) >= 2 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(2, new TargetFilter(TargetHint.BANISH).OP().SIGNI());
            banish(data);
        }
    }
}
