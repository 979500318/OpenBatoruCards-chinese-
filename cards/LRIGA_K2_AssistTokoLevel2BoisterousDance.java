package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_AssistTokoLevel2BoisterousDance extends Card {

    public LRIGA_K2_AssistTokoLevel2BoisterousDance()
    {
        setImageSets("WXDi-CP01-017");

        setOriginalName("【アシスト】とこ　レベル２【乱舞】");
        setAltNames("アシストトコレベルニランブ Ashisuto Toko Reberu Ni Ranbu Assist Toko");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。\n" +
                "@E %X %X %X %X %X：このターン、対戦相手はパワーが10000以下のシグニでアタックできない。"
        );

        setName("en", "[Assist] Toko, Level 2 [Ranbu]");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --5000 power until end of turn.\n@E %X %X %X %X %X: Your opponent cannot attack with SIGNI with power 10000 or less this turn.\n"
        );
        
        setName("en_fan", "[Assist] Toko Level 2 [Boisterous Dance]");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power.\n" +
                "@E %X %X %X %X %X: This turn, your opponent can't attack with SIGNI with power 10000 or less."
        );

		setName("zh_simplified", "【支援】床 等级2【乱舞】");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n" +
                "@E %X %X %X %X %X:这个回合，对战对手的力量在10000以下的精灵不能攻击。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TOKO);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(5)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_ATTACK, getOpponent(), ChronoDuration.turnEnd(), data ->
                CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) &&
                data.getSourceCardIndex().getIndexedInstance().getPower().getValue() <= 10000 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
            );
        }
    }
}
