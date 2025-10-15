package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleValueType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B3_CodeOrderHoneytoIce extends Card {

    public SIGNI_B3_CodeOrderHoneytoIce()
    {
        setImageSets("WXDi-P05-039");

        setOriginalName("コードオーダー　ハニートアイス");
        setAltNames("コードオーダーハニートアイス Koodo Oodaa Haniito Aisu");
        setDescription("jp",
                "=T ＜きゅるきゅるーん☆＞\n" +
                "^U：対戦相手のターン開始時、対戦相手の手札が２枚以上ある場合、そのターンのドローフェイズの間に対戦相手はカードを合計１枚までしか引けない。\n" +
                "@U：あなたのアタックフェイズ開始時、各プレイヤーはカードを１枚引く。あなたは対戦相手の手札を見て#Gを持たないカード１枚を選び、デッキの一番下に置く。"
        );

        setName("en", "Honeyto Ice, Code: Order");
        setDescription("en",
                "=T <<Kyurukyurun☆>>\n" +
                "^U: At the beginning of your opponent's turn, if your opponent has two or more cards in their hand, they can only draw one card in total during the draw phase that turn.\n" +
                "@U: At the beginning of your attack phase, each player draws a card. You look at your opponent's hand and choose a card without a #G. Put it on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Code Order Honeyto Ice");
        setDescription("en_fan",
                "=T <<Kyurukyurun☆>>\n" +
                "^U: At the beginning of your opponent's turn, if there are 2 or more cards in your opponent's hand, your opponent can only draw up to 1 card in total during the draw phase of that turn.\n" +
                "@U: At the beginning of your attack phase, each player draws 1 card, you look at your opponent's hand and choose 1 card without #G @[Guard]@ from it, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "点单代号 蜜糖冰淇淋");
        setDescription("zh_simplified", 
                "=T<<きゅるきゅるーん☆>>\n" +
                "^U:对战对手的回合开始时，对战对手的手牌在2张以上的场合，那个回合的抽牌阶段期间，对战对手只能抽合计1张最多的牌。\n" +
                "@U 你的攻击阶段开始时，各玩家抽1张牌。你看对战对手的手牌选不持有#G的牌1张，放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isLRIGTeam(CardLRIGTeam.KYURUKYURUN) &&
                    !isOwnTurn() && getCurrentPhase() == GamePhase.UP ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getHandCount(getOpponent()) >= 2)
            {
                setPlayerRuleValue(getOpponent(), PlayerRuleValueType.DRAW_PHASE_MAX, 1, ChronoDuration.nextPhaseEnd(getOpponent(), GamePhase.DRAW));
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            draw(1);
            draw(getOpponent(), 1);

            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);

            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromRevealed()).get();
            returnToDeck(cardIndex, DeckPosition.BOTTOM);

            addToHand(getCardsInRevealed(getOpponent()));
        }
    }
}

