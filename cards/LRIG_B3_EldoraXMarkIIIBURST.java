package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class LRIG_B3_EldoraXMarkIIIBURST extends Card {

    public LRIG_B3_EldoraXMarkIIIBURST()
    {
        setImageSets("WX24-P3-022", "WX24-P3-022U");

        setOriginalName("エルドラ×マークⅢ　BURST");
        setAltNames("エルドラマークスリーバースト Erudora Maaku Surii Baasuto EldoraxMark III Eldora x Mark III");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に＜水獣＞のシグニがある場合、カードを１枚引き、対戦相手のデッキの一番上を公開する。公開したそのカードが##を持つ場合、カードを１枚引く。\n" +
                "@A $G1 @[@|リクエスト|@]@ %B0：このターンと次のターンの間、あなたのすべての領域にあるカードは@>~#カードを１枚引く。その後、対戦相手のシグニ１体を対象とし、手札を２枚捨ててもよい。そうした場合、それをダウンする。@@@@を得る。"
        );

        setName("en", "Eldora×Mark III BURST");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is a <<Water Beast>> SIGNI on your field, draw 1 card, and reveal the top card of your opponent's deck. If that revealed card has ## @[Life Burst]@, draw 1 card.\n" +
                "@A $G1 @[@|Request|@]@ %B0: During this turn and the next turn, cards in all of your zones gain:" +
                "@>~#Draw 1 card. Then, target 1 of your opponent's SIGNI, and you may discard 2 cards from your hand. If you do, down it."
        );

		setName("zh_simplified", "艾尔德拉×III式 BURST");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，你的场上有<<水獣>>精灵的场合，抽1张牌，对战对手的牌组最上面公开。公开的那张牌持有##的场合，抽1张牌。\n" +
                "@A $G1 :请求%B0这个回合和下一个回合期间，你的全部的领域的牌得到##\n" +
                "@>抽1张牌。然后，对战对手的精灵1只作为对象，可以把手牌2张舍弃。这样做的场合，将其#D。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ELDORA);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
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

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Request");
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.WATER_BEAST).getValidTargetsCount() > 0)
            {
                draw(1);
                
                CardIndex cardIndex = reveal(getOpponent());
                if(cardIndex != null)
                {
                    cardIndex.getIndexedInstance().findLifeBurstAbility().ifPresent(ability -> draw(1));
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }

        private void onActionEff()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().anyLocation().except(
                CardLocation.LRIG,CardLocation.LRIG_ASSIST_LEFT,CardLocation.LRIG_ASSIST_RIGHT, CardLocation.DECK_LRIG, CardLocation.TRASH_LRIG
            ), new AbilityGainModifier(this::onAttachedConstEffSharedModGetSample));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd().repeat(2));
        }
        private Ability onAttachedConstEffSharedModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerLifeBurstAbility(this::onAttachedLifeBurstEff);
        }
        private void onAttachedLifeBurstEff()
        {
            CardIndex cardIndexSource = getAbility().getSourceCardIndex();
            
            draw(1);
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
            
            if(target != null && cardIndexSource.getIndexedInstance().discard(0,2, ChoiceLogic.BOOLEAN).size() == 2)
            {
                cardIndexSource.getIndexedInstance().down(target);
            }
        }
    }
}
