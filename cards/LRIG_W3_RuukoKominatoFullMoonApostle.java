package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.CardFunctionalConditionalHandler;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneWall;

public final class LRIG_W3_RuukoKominatoFullMoonApostle extends Card {

    public LRIG_W3_RuukoKominatoFullMoonApostle()
    {
        setImageSets("WX24-P2-014", "WX24-P2-014U");

        setOriginalName("満月の使徒　小湊るう子");
        setAltNames("マンゲツノシトコミナトルウコ Mangetsu no Shito Kominato Ruuko");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に＜悪魔＞のシグニがある場合、カードを１枚引く。＜天使＞のシグニがある場合、【エナチャージ１】をする。\n" +
                "@A $G1 @[@|夢限の理|@]@ #D：対戦相手のセンタールリグがレベル３以上の場合、各プレイヤーは自分の手札とシグニゾーンとエナゾーンとトラッシュにある、すべてのクラフトをゲームから除外し、すべてのカードをデッキに加えてシャッフルし、カードを６枚引く。このターン、対戦相手はダメージを受けない。"
        );

        setName("en", "Ruuko Kominato, Full Moon Apostle");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is a <<Devil>> SIGNI on your field, draw 1 card. If there is an <<Angel>> SIGNI on your field, [[Ener Charge 1]].\n" +
                "@A $G1 @[@|Eternal Principle|@]@ #D: If your opponent's center LRIG is level 3 or higher, each player shuffles all cards from their hand, SIGNI zones, ener zone, and trash into the deck, excludes all crafts from among them, and draws 6 cards. Your opponent can't be damaged this turn."
        );

		setName("zh_simplified", "满月的使徒 小凑露子");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有<<悪魔>>精灵的场合，抽1张牌。有<<天使>>精灵的场合，[[能量填充1]]。\n" +
                "@A $G1 梦限之理#D:对战对手的核心分身在等级3以上的场合，各玩家把自己的手牌和精灵区和能量区和废弃区的，全部的衍生从游戏除外，全部的牌加入牌组洗切，抽6张牌。这个回合，对战对手不会受到伤害。\n"
        );

        setLRIGType(CardLRIGType.RUUKO);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new DownCost(), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Eternal Principle");
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DEVIL).getValidTargetsCount() > 0)
            {
                draw(1);
            }
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).getValidTargetsCount() > 0)
            {
                enerCharge(1);
            }
        }
        
        private void onActionEff()
        {
            if(getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue() >= 3)
            {
                CardFunctionalConditionalHandler condReturnUnder = cardIndex -> (cardIndex.getIndexedInstance() == null || cardIndex.getIndexedInstance().isState(CardStateFlag.KILL_ME)) && !cardIndex.wasMovedByOverride();

                returnToDeck(getSIGNIOnField(getOwner()), DeckPosition.TOP);
                returnToDeck(new TargetFilter().own().fromSafeLocation(CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT).custom(condReturnUnder).getExportedData(), DeckPosition.TOP);
                returnToDeck(getCardsInHand(getOwner()), DeckPosition.TOP);
                returnToDeck(getCardsInEner(getOwner()).andRemoveIf(CardIndex::wasMovedByOverride), DeckPosition.TOP);
                returnToDeck(getCardsInTrash(getOwner()).andRemoveIf(CardIndex::wasMovedByOverride), DeckPosition.TOP);
                shuffleDeck(getOwner());

                returnToDeck(getSIGNIOnField(getOpponent()), DeckPosition.TOP);
                returnToDeck(new TargetFilter().OP().fromSafeLocation(CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT).custom(condReturnUnder).getExportedData(), DeckPosition.TOP);
                returnToDeck(getCardsInHand(getOpponent()), DeckPosition.TOP);
                returnToDeck(getCardsInEner(getOpponent()).andRemoveIf(CardIndex::wasMovedByOverride), DeckPosition.TOP);
                returnToDeck(getCardsInTrash(getOpponent()).andRemoveIf(CardIndex::wasMovedByOverride), DeckPosition.TOP);
                shuffleDeck(getOpponent());
                
                draw(getOwner(), 6);
                draw(getOpponent(), 6);
                
                ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
                GFX.attachToChronoRecord(record, new GFXZoneWall(getOpponent(),CardLocation.LIFE_CLOTH, "checkerboard"));
                addPlayerRuleCheck(PlayerRuleCheckType.CAN_BE_DAMAGED, getOpponent(), record, data -> RuleCheckState.BLOCK);
            }
        }
    }
}
