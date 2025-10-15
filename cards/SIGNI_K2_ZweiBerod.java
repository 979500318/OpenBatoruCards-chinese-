package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.CardIndexSnapshot;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_K2_ZweiBerod extends Card {

    public SIGNI_K2_ZweiBerod()
    {
        setImageSets("WX24-P4-103");

        setOriginalName("ツヴァイ＝ベロド");
        setAltNames("ツヴァイベロド Tsuvai Berodo");
        setDescription("jp",
                "@E @[アップ状態のルリグを好きな数ダウンする]@：対戦相手のシグニ1体を対象とし、ターン終了時まで、それのパワーをこの方法でダウンしたルリグのレベルの合計1につき－1000する。"
        );

        setName("en", "Zwei-Berod");
        setDescription("en",
                "@E @[Down any number of your upped LRIG]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power for each level of all LRIG downed this way."
        );

		setName("zh_simplified", "ZWEI=志贺样毒素");
        setDescription("zh_simplified", 
                "@E 竖直状态的分身任意数量#D对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据这个方法#D的分身的等级的合计的数量，每有1级就-1000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
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
            
            registerEnterAbility(new DownCost(0,AbilityConst.MAX_UNLIMITED, new TargetFilter().anyLRIG()), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null && !getAbility().getCostPaidData().isEmpty() && getAbility().getCostPaidData() != null)
            {
                gainPower(target, -1000 * getAbility().getCostPaidData().stream().mapToInt(c -> ((CardIndexSnapshot)c).getLevel().getValue()).sum(), ChronoDuration.turnEnd());
            }
        }
    }
}
