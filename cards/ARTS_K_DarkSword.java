package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class ARTS_K_DarkSword extends Card {

    public ARTS_K_DarkSword()
    {
        setImageSets("WX24-P1-030");

        setOriginalName("ダーク・ソード");
        setAltNames("ダークソード Daaku Soodo");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。"
        );

        setName("en", "Dark Sword");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power."
        );

        setName("zh_simplified", "黑暗·刀剑");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
        }
    }
}

