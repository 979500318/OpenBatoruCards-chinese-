package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.rulechecks.RuleCheck;
import open.batoru.core.gameplay.rulechecks.player.RuleCheckMustSkipPhase;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_W2_DonaEscape extends Card {

    public LRIGA_W2_DonaEscape()
    {
        setImageSets("WXDi-P09-031");

        setOriginalName("ドーナ『エスケープ！』");
        setAltNames("ドーナエスケープ Doona Esukeepu");
        setDescription("jp",
                "@E @[手札を２枚捨てる]@：このターン、シグニアタックステップをスキップする。"
        );

        setName("en", "Dona \"Escape!\"");
        setDescription("en",
                "@E @[Discard two cards]@: Skip the SIGNI attack step this turn."
        );
        
        setName("en_fan", "Dona \"Escape!\"");
        setDescription("en_fan",
                "@E @[Discard 2 cards from your hand]@: Skip the SIGNI attack step of this turn."
        );

		setName("zh_simplified", "多娜『逃脱！』");
        setDescription("zh_simplified", 
                "@E 手牌2张舍弃:这个回合，精灵攻击步骤跳过。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.DONA);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(5));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(2), this::onEnterEff);
        }

        private void onEnterEff()
        {
            addPlayerRuleCheck(PlayerRuleCheckRegistry.PlayerRuleCheckType.MUST_SKIP_PHASE, getTurnPlayer(), ChronoDuration.turnEnd(), data -> {
                return RuleCheckMustSkipPhase.getDataGamePhase(data) == GameConst.GamePhase.ATTACK_SIGNI ? RuleCheck.RuleCheckState.OK : RuleCheck.RuleCheckState.IGNORE;
            });
        }
    }
}
