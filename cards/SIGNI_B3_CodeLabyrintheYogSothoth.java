package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_B3_CodeLabyrintheYogSothoth extends Card {
    
    public SIGNI_B3_CodeLabyrintheYogSothoth()
    {
        setImageSets("WXDi-P00-037");
        
        setOriginalName("コードラビラント　ヨグソトス");
        setAltNames("コードラビラントヨグソトス Koodo Rabiranto Yogusotosu");
        setDescription("jp",
                "=T ＜アンシエント・サプライズ＞\n" +
                "^U $T1：場にあるこのシグニが他のシグニゾーンに移動したとき、%B %Bを支払ってもよい。そうした場合、対戦相手の手札を３枚まで見ないで選び、それらを見て１枚をデッキの一番下に置く。\n" +
                "@A $T1 %B %B：対戦相手はデッキの一番下のカードをトラッシュに置く。その後、そのカードと同じカード名の対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Yog-Sothoth, Code:Labyrinth");
        setDescription("en",
                "=T <<Ancient Surprise>>\n" +
                "^U $T1: When this SIGNI on your field moves to a different SIGNI zone, you may pay %B %B. If you do, choose three cards at random from your opponent's hand and look at them. Put one of them on the bottom of your opponent's deck.\n" +
                "@A $T1 %B %B: Put the bottom card of your opponent's deck into the trash. Then, vanish target SIGNI on your opponent's field with the same name as that card."
        );
        
        setName("en_fan", "Code Labyrinthe Yog-Sothoth");
        setDescription("en_fan",
                "=T <<Ancient Surprise>>\n" +
                "^U $T1: When this SIGNI on the field is moved to another SIGNI zone, you may pay %B %B. If you do, choose up to 3 cards from your opponent's hand without looking, look at them and then put 1 of them on the bottom of your opponent's deck.\n" +
                "@A $T1 %B %B: Your opponent puts the bottom card of their deck into the trash. Then, target 1 of your opponent's SIGNI with the same name as that card, and banish it."
        );
        
		setName("zh_simplified", "迷阁代号 犹格索托斯");
        setDescription("zh_simplified", 
                "=T<<アンシエント・サプライズ>>\n" +
                "^U$T1 :当场上的这只精灵往其他的精灵区移动时，可以支付%B %B。这样做的场合，不看对战对手的手牌选3张最多，看这些并把1张放置到牌组最下面。\n" +
                "@A $T1 %B %B对战对手的牌组最下面的牌放置到废弃区。然后，与那张牌相同牌名的对战对手的精灵1只作为对象，将其破坏。&nbsp;\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility action = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 2)), this::onActionEff);
            action.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE) && CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(payEner(Cost.color(CardColor.BLUE, 2)))
            {
                DataTable<CardIndex> data = playerChoiceHand(0,3);
                
                if(data.get() != null)
                {
                    reveal(data, true);
                    
                    CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().fromRevealed()).get();
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                    
                    addToHand(getCardsInRevealed(getOpponent()));
                }
            }
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = millDeck(getOpponent(), 1, DeckPosition.BOTTOM).get();
            
            if(cardIndex != null)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withName(cardIndex.getCardReference().getOriginalName())).get();
                banish(target);
            }
        }
    }
}
