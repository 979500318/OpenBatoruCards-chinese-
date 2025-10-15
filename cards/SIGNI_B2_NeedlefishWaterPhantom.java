package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B2_NeedlefishWaterPhantom extends Card {

    public SIGNI_B2_NeedlefishWaterPhantom()
    {
        setImageSets("WXDi-P11-067");

        setOriginalName("幻水　ダツ");
        setAltNames("ゲンスイダツ Gensui Datsu");
        setDescription("jp",
                "@E %B：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。このターンにあなたが手札を２枚以上捨てていた場合、代わりにターン終了時まで、それのパワーを－3000する。"
        );

        setName("en", "Needlefish, Phantom Aquatic Beast");
        setDescription("en",
                "@E %B: Target SIGNI on your opponent's field gets --2000 power until end of turn. If you have discarded two or more cards this turn, it gets --3000 power until end of turn instead."
        );
        
        setName("en_fan", "Needlefish, Water Phantom");
        setDescription("en_fan",
                "@E %B: Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power. If you discarded 2 or more cards from your hand this turn, until end of turn, it gets --3000 power instead."
        );

		setName("zh_simplified", "幻水 颌针鱼");
        setDescription("zh_simplified", 
                "@E %B:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。这个回合你把手牌2张以上舍弃过的场合，作为替代，直到回合结束时为止，其的力量-3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.DISCARD && isOwnCard(event.getCallerCardIndex())) < 2 ? -2000 : -3000, ChronoDuration.turnEnd());
        }
    }
}

