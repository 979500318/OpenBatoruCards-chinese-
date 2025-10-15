package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
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
import open.batoru.data.ability.AbilityConst.AbilityGain;

public final class ARTS_W_UnstableShield extends Card {

    public ARTS_W_UnstableShield()
    {
        setImageSets("WX24-P2-032");

        setOriginalName("アンステイブル・シールド");
        setAltNames("アンステイブルシールド Ansuteibaru Shiirudo");
        setDescription("jp",
                "このアーツは対戦相手のターンにしか使用できない。\n\n" +
                "対戦相手のルリグとシグニを合計２体まで対象とし、ターン終了時まで、それらは能力を失う。"
        );

        setName("en", "Unstable Shield");
        setDescription("en",
                "This ARTS can only be used during your opponent's turn.\n\n" +
                "Target up to 2 of your opponent's LRIG and/or SIGNI, and until end of turn, they lose their abilities."
        );

		setName("zh_simplified", "动荡·之盾");
        setDescription("zh_simplified", 
                "这张必杀只有在对战对手的回合才能使用。\n" +
                "对战对手的分身和精灵合计2只最多作为对象，直到回合结束时为止，这些的能力失去。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1));
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

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
        }
        
        private ConditionState onARTSEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.MUTE).OP().fromField());
            disableAllAbilities(data, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
    }
}
