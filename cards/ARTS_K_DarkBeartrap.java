package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AutoAbility;

public final class ARTS_K_DarkBeartrap extends Card {

    public ARTS_K_DarkBeartrap()
    {
        setImageSets("WX24-D5-08", "SPDi37-11");

        setOriginalName("ダーク・ベアトラップ");
        setAltNames("ダークベアトラップ Daaku Beatorappu");
        setDescription("jp",
                "対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらは@>@U：このシグニがアタックしたとき、ターン終了時まで、このシグニのパワーを－20000する。@@を得る。"
        );

        setName("en", "Dark Beartrap");
        setDescription("en",
                "Target up to 2 of your opponent's SIGNI, and until end of turn, they gain:" +
                "@>@U: Whenever this SIGNI attacks, until end of turn, it gets --20000 power."
        );

		setName("zh_simplified", "黑暗·捕熊陷阱");
        setDescription("zh_simplified", 
                "对战对手的精灵2只最多作为对象，直到回合结束时为止，这些得到\n" +
                "@>@U :当这只精灵攻击时，直到回合结束时为止，这只精灵的力量-20000。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(2));
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

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().SIGNI());
            if(data.get() != null)
            {
                for(int i=0;i<data.size();i++)
                {
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                    attachAbility(data.get(i), attachedAuto, ChronoDuration.turnEnd());
                }
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex cardIndexSource = getAbility().getSourceCardIndex();
            cardIndexSource.getIndexedInstance().gainPower(cardIndexSource, -20000, ChronoDuration.turnEnd());
        }
    }
}

