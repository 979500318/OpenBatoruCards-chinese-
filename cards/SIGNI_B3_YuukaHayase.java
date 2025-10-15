package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_B3_YuukaHayase extends Card {

    public SIGNI_B3_YuukaHayase()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WXDi-CP02-056");

        setOriginalName("早瀬ユウカ");
        setAltNames("ハヤセユウカ Hayase Yuuka");
        setDescription("jp",
                "@C：対戦相手のターンの間、対戦相手の効果によってあなたの＜ブルアカ＞のシグニ１体が場を離れる場合、「手札を２枚捨てる」を行ってもよい。そうした場合、代わりにターン終了時まで、このシグニはこの能力を失う。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが＜ブルアカ＞の場合、カードを２枚引き、手札を１枚捨てる。" +
                "~{{A @[アップ状態のルリグ２体をダウンする]@：カードを１枚引く。@@" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Hayase Yuuka");
        setDescription("en",
                "@C: During your opponent's turn, if a <<Blue Archive>> SIGNI would leave your field by your opponent's effect, you may perform \"Discard two cards\". If you do, this SIGNI loses this ability until end of turn instead.\n@U: At the beginning of your attack phase, if all the SIGNI on your field are <<Blue Archive>>, draw two cards and discard a card.~{{A @[Down two upped LRIG]@: Draw a card.@@" +
                "~#Put target upped SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Yuuka Hayase");
        setDescription("en_fan",
                "@C: During your opponent's turn, if 1 of your <<Blue Archive>> SIGNI would leave the field due to your opponent's effect, you may \"discard 2 cards from your hand\". If you do, until end of turn, this SIGNI loses this ability instead.\n" +
                "@U: At the beginning of your attack phase, if all of your SIGNI are <<Blue Archive>> SIGNI, draw 2 cards, and discard 1 card from your hand." +
                "~{{A @[Down 2 of your upped LRIG]@: Draw 1 card.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "早濑优香");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，因为对战对手的效果把你的<<ブルアカ>>精灵1只离场的场合，可以进行\n" +
                "@>:手牌2张舍弃@@\n" +
                "。这样做的场合，作为替代，直到回合结束时为止，这只精灵的这个能力失去。\n" +
                "@U :你的攻击阶段开始时，你的场上的全部的精灵是<<ブルアカ>>的场合，抽2张牌，手牌1张舍弃。\n" +
                "~{{A竖直状态的分身2只#D:抽1张牌。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER, OverrideFlag.NON_MANDATORY | OverrideFlag.PRE_OVERRIDE, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler))
            );

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ActionAbility act = registerActionAbility(new DownCost(2, new TargetFilter().anyLRIG()), this::onActionEff);
            act.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() != null && !isOwnCard(event.getSourceCardIndex()) && !CardLocation.isSIGNI(((EventMove)event).getMoveLocation()) &&
                    getHandCount(getOwner()) >= 2;
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addDiscardAction(2).setOnActionCompleted(() -> {
                if(list.getAction(0).isSuccessful()) sourceAbilityRC.disable(ChronoDuration.turnEnd());
            });
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).getValidTargetsCount() == 0)
            {
                draw(2);
                discard(1);
            }
        }

        private void onActionEff()
        {
            draw(1);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().upped()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
