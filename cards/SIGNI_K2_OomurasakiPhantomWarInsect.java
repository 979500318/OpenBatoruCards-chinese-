package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K2_OomurasakiPhantomWarInsect extends Card {

    public SIGNI_K2_OomurasakiPhantomWarInsect()
    {
        setImageSets("WX25-P2-102", "SPDi45-05","SPDi45-05P");

        setOriginalName("幻闘蟲　オオムラサキ");
        setAltNames("ゲントウチュウオオムラサキ Gentouchuu Oomurasaki");
        setDescription("jp",
                "@E @[手札から＜凶蟲＞のシグニを１枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。それに【チャーム】が付いている場合、代わりにターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Oomurasaki, Phantom War Insect");
        setDescription("en",
                "@E @[Discard 1 <<Misfortune Insect>> SIGNI from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power. If it has a [[Charm]] attached to it, until end of turn, it gets --8000 power instead."
        );

		setName("zh_simplified", "幻斗虫 大紫蛱蝶");
        setDescription("zh_simplified", 
                "@E 从手牌把<<凶蟲>>精灵1张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。其有[[魅饰]]附加的场合，作为替代，直到回合结束时为止，其的力量-8000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.MISFORTUNE_INSECT)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, target.getIndexedInstance().getCardsUnderCount(CardUnderType.ATTACHED_CHARM) == 0 ? -5000 : -8000, ChronoDuration.turnEnd());
        }
    }
}
