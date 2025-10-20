package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_SionTodoPriParaIdol extends Card {

    public SIGNI_K3_SionTodoPriParaIdol()
    {
        setImageSets("WXDi-P10-076");

        setOriginalName("プリパラアイドル　東堂シオン");
        setAltNames("プリパラアイドルトウドウシオン Puripara Aidoru Toudou Shion");
        setDescription("jp",
                "@A %X #D：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたのトラッシュにある＜プリパラ＞のシグニ１枚につき－1000する。この効果は１５枚までしか適用されない。"
        );

        setName("en", "Todo Sion, Pripara Idol");
        setDescription("en",
                "@A %X #D: Target SIGNI on your opponent's field gets --1000 power for each <<Pripara>> SIGNI in your trash until end of turn. This effect can only be applied with up to fifteen cards."
        );
        
        setName("en_fan", "Sion Todo, PriPara Idol");
        setDescription("en_fan",
                "@A %X #D: Target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power for each <<PriPara>> SIGNI in your trash. This effect can only be applied to up to 15 cards."
        );

		setName("zh_simplified", "美妙天堂偶像 东堂诗音");
        setDescription("zh_simplified", 
                "@A %X横置:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的废弃区的<<プリパラ>>精灵的数量，每有1张就-1000。这个效果只能把15张最多适用。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new AbilityCostList(new EnerCost(Cost.colorless(1)), new DownCost()), this::onActionEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, Math.max(-15000, -1000 * new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PRIPARA).fromTrash().getValidTargetsCount()), ChronoDuration.turnEnd());
        }
    }
}
