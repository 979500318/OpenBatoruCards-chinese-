package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B1_SkipjackTunaWaterPhantom extends Card {

    public SIGNI_B1_SkipjackTunaWaterPhantom()
    {
        setImageSets("WX24-P4-071");

        setOriginalName("幻水　カツオ");
        setAltNames("ゲンスイカツオ Gensui Katsuo");
        setDescription("jp",
                "@E @[手札から青のカードを１枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。"
        );

        setName("en", "Skipjack Tuna, Water Phantom");
        setDescription("en",
                "@E @[Discard 1 blue card from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power."
        );

		setName("zh_simplified", "幻水 鲣鱼");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(new TargetFilter().withColor(CardColor.BLUE)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -3000, ChronoDuration.turnEnd());
        }
    }
}
