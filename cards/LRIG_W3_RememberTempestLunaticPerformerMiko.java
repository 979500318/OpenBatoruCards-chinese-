package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXFieldBackground;

public final class LRIG_W3_RememberTempestLunaticPerformerMiko extends Card {

    public LRIG_W3_RememberTempestLunaticPerformerMiko()
    {
        setImageSets("WXDi-P13-006", "WXDi-P13-006U");

        setOriginalName("狂奏の巫女　リメンバ・テンペスト");
        setAltNames("キョウソウノミコリメンバテンペスト Kyousou no Miko Rimenba Tenpesuto");
        setDescription("jp",
                "@U：あなたの場にあるすべてのシグニが#Sであるかぎり、対戦相手は追加で%Xを支払わないかぎり【ガード】ができない。\n" +
                "@U：あなたのメインフェイズ開始時、あなたのデッキの一番上を公開する。そのカードが#Sの場合、カードを１枚引く。\n" +
                "@A @[エクシード４]@：このターンと次のターンの間、対戦相手のシグニの@U能力は発動しない。"
        );

        setName("en", "Remember Tempest, Miko of Fantasia");
        setDescription("en",
                "@C: As long as all the SIGNI on your field are #S, your opponent cannot [[Guard]] unless they pay an additional %X.\n@U: At the beginning of your main phase, reveal the top card of your deck. If that card is #S, draw a card.\n@A @[Exceed 4]@: The @U abilities of your opponent's SIGNI do not activate during this turn and the next turn."
        );
        
        setName("en_fan", "Remember Tempest, Lunatic Performer Miko");
        setDescription("en_fan",
                "@C: As long as all of your SIGNI are #S @[Dissona]@ SIGNI, your opponent can't [[Guard]] unless they pay an additional %X.\n" +
                "@U: At the beginning of your main phase, reveal the top card of your deck. If it is a #S @[Dissona]@ card, draw 1 card.\n" +
                "@A @[Exceed 4]@: During this turn and the next turn, the @U abilities of your opponent's SIGNI don't activate."
        );

		setName("zh_simplified", "狂奏的巫女 忆·暴风雨");
        setDescription("zh_simplified", 
                "@C 你的场上的全部的精灵是#S时，对战对手如果不追加把%X:支付，那么不能[[防御]]。\n" +
                "@U 你的主要阶段开始时，你的牌组最上面公开。那张牌是#S的场合，抽1张牌。\n" +
                "@A @[超越 4]@这个回合和下一个回合期间，对战对手的精灵的@U能力不能发动。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD,
                TargetFilter.HINT_OWNER_OP, data -> new EnerCost(Cost.colorless(1)))
            );

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerActionAbility(new ExceedCost(4), this::onActionEff);
        }

        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().not(new TargetFilter().withState(CardStateFlag.IS_DISSONA)).getValidTargetsCount() == 0 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();

            if(cardIndex == null || !cardIndex.getIndexedInstance().isState(CardStateFlag.IS_DISSONA) ||
                draw(1).get() == null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }

        private void onActionEff()
        {
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd().repeat(2));
            GFX.attachToChronoRecord(record, new GFXFieldBackground(getOpponent(), "constellation", 2090,1190, 390,360, new int[]{225,182,34}));
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_USE_ABILITY, getOpponent(), record, this::onActionEffRuleCheck);
        }
        private RuleCheckState onActionEffRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() instanceof AutoAbility &&
                   data.getSourceCardIndex() != null && CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}

