package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K1_LittleOniWickedDevil extends Card {

    public SIGNI_K1_LittleOniWickedDevil()
    {
        setImageSets("WX24-D5-11");

        setOriginalName("凶魔　コオニ");
        setAltNames("キョウマコオニ Kyouma Kooni");
        setDescription("jp",
                "@E @[手札から黒のカードを１枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。このターンにあなたが黒のアーツを使用していた場合、代わりにターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Little Oni, Wicked Devil");
        setDescription("en",
                "@E @[Discard 1 black card from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power. If you used a black ARTS this turn, instead, until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "凶魔 小鬼");
        setDescription("zh_simplified", 
                "@E 从手牌把黑色的牌1张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。这个回合你把黑色的必杀使用过的场合，作为替代，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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

            registerEnterAbility(new DiscardCost(new TargetFilter().withColor(CardColor.BLACK)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            boolean hasUsedBlackARTS = GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller()) && event.getCaller().getColor().matches(CardColor.BLACK)) > 0;
            gainPower(target, !hasUsedBlackARTS ? -3000 : -5000, ChronoDuration.turnEnd());
        }
    }
}
