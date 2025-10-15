package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_MayflyPhantomInsect extends Card {

    public SIGNI_K3_MayflyPhantomInsect()
    {
        setImageSets("WX25-P2-107");

        setOriginalName("幻蟲　カゲロウ");
        setAltNames("ゲンチュウカゲロウ Genchuu Kagerou");
        setDescription("jp",
                "@E %K %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－12000する。それに【チャーム】が付いている場合、代わりにターン終了時まで、それのパワーを－15000する。"
        );

        setName("en", "Mayfly, Phantom Insect");
        setDescription("en",
                "@E %K %X: Target 1 of your opponent's SIGNI, and until end of turn, it gets --12000 power. If it has a [[Charm]] attached to it, until end of turn, it gets --15000 power instead."
        );

		setName("zh_simplified", "幻虫 蜉蝣");
        setDescription("zh_simplified", 
                "@E %K%X:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-12000。其有[[魅饰]]附加的场合，作为替代，直到回合结束时为止，其的力量-15000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, target.getIndexedInstance().getCardsUnderCount(CardUnderType.ATTACHED_CHARM) == 0 ? -12000 : -15000, ChronoDuration.turnEnd());
        }
    }
}
