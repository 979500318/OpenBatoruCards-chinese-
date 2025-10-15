package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K1_RiceGrasshopperPhantomInsect extends Card {

    public SIGNI_K1_RiceGrasshopperPhantomInsect()
    {
        setImageSets("WX25-P2-098");

        setOriginalName("幻蟲　イナゴ");
        setAltNames("ゲンチュウイナゴ Genchuu Inago");
        setDescription("jp",
                "@E @[手札から＜凶蟲＞のシグニを１枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。あなたの場に他の＜凶蟲＞のシグニがある場合、代わりにターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Rice Grasshopper, Phantom Insect");
        setDescription("en",
                "@E @[Discard 1 <<Misfortune Insect>> SIGNI from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power. If there is another <<Misfortune Insect>> SIGNI on your field, until end of turn, it gets --5000 power instead."
        );

		setName("zh_simplified", "幻虫 蝗虫");
        setDescription("zh_simplified", 
                "@E 从手牌把<<凶蟲>>精灵1张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。你的场上有其他的<<凶蟲>>精灵的场合，作为替代，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
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

            registerEnterAbility(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.MISFORTUNE_INSECT)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                gainPower(target, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.MISFORTUNE_INSECT).except(getCardIndex()).getValidTargetsCount() == 0 ? -3000 : -5000, ChronoDuration.turnEnd());
            }
        }
    }
}
