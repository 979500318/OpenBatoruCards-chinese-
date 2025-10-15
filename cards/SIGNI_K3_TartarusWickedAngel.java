package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_K3_TartarusWickedAngel extends Card {
    
    public SIGNI_K3_TartarusWickedAngel()
    {
        setImageSets("WXDi-P05-085");
        
        setOriginalName("凶天　タルタロス");
        setAltNames("キョウテンタルタロス Kyouten Tarutarosu");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたのトラッシュから#Gを持たないシグニ３枚を対象とし、それらをデッキに加えてシャッフルする。\n" +
                "@A #D：あなたのデッキの上からカードを５枚トラッシュに置く。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "Tartarus, Doomed Angel");
        setDescription("en",
                "@U: At the end of your turn, shuffle three target SIGNI without a #G from your trash into your deck.\n" +
                "@A #D: Put the top five cards of your deck into your trash." +
                "~#Add target SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Tartarus, Wicked Angel");
        setDescription("en_fan",
                "@U: At the end of your turn, target 3 SIGNI without #G @[Guard]@ from your trash, add them to your deck, and shuffle it.\n" +
                "@A #D: Put the top 5 cards of your deck into the trash." +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );
        
		setName("zh_simplified", "凶天 塔耳塔洛斯");
        setDescription("zh_simplified", 
                "@U 你的回合结束时，从你的废弃区把不持有#G的精灵3张作为对象，将这些加入牌组洗切。\n" +
                "@A #D:从你的牌组上面把5张牌放置到废弃区。" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerActionAbility(new DownCost(), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(3, new TargetFilter(TargetHint.SHUFFLE).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
            if(returnToDeck(data, DeckPosition.TOP) > 0) shuffleDeck();
        }
        
        private void onActionEff()
        {
            millDeck(5);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
            if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
            {
                addToHand(target);
            } else {
                putOnField(target);
            }
        }
    }
}
