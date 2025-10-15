package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_B3_HoneytraGreatTrap extends Card {
    
    public SIGNI_B3_HoneytraGreatTrap()
    {
        setImageSets("WXDi-P07-076");
        
        setOriginalName("大罠　ハニトラ");
        setAltNames("ダイビンハニトラ Daibin Hanitora");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手は手札を１枚捨てる。\n" +
                "@A $T1 #C #C：カードを１枚引く。\n\n" +
                "@U：このカードが対戦相手の効果によってデッキか手札からトラッシュに置かれたとき、対戦相手のトラッシュからカード１枚を対象とし、それをデッキの一番上に置いてもよい。"
        );
        
        setName("en", "Honeytra, Master Trickster");
        setDescription("en",
                "@U: At the beginning of your attack phase, your opponent discards a card.\n" +
                "@A $T1 #C #C: Draw a card.\n\n" +
                "@U: When this card is put into your trash from your deck or hand by your opponent's effect, you may put target card from your opponent's trash on the top of their deck."
        );
        
        setName("en_fan", "Honeytra, Great Trap");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, your opponent discards 1 card from their hand.\n" +
                "@A $T1 #C #C: Draw 1 card.\n\n" +
                "@U: When this card is put from your deck or hand into the trash by your opponent's effect, target 1 card from your opponent's trash, and you may return it to the top of their deck."
        );
        
		setName("zh_simplified", "大罠 美人计");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手把手牌1张舍弃。\n" +
                "@A $T1 #C #C:抽1张牌。\n" +
                "@U :当这张牌因为对战对手的效果从牌组或手牌放置到废弃区时，从对战对手的废弃区把1张牌作为对象，可以将其放置到牌组最上面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto.setCondition(this::onAutoEff1Cond);
            
            ActionAbility act = registerActionAbility(new CoinCost(2), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.TRASH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setActiveLocation(CardLocation.DECK_MAIN, CardLocation.HAND);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }
        
        private void onActionEff()
        {
            draw(1);
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TOP).OP().fromTrash()).get();
            
            if(target != null && playerChoiceActivate())
            {
                returnToDeck(target, DeckPosition.TOP);
            }
        }
    }
}
