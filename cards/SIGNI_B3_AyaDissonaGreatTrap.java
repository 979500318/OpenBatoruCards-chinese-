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
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_B3_AyaDissonaGreatTrap extends Card {

    public SIGNI_B3_AyaDissonaGreatTrap()
    {
        setImageSets("WXDi-P13-049", "WXDi-P13-049P");

        setOriginalName("大罠　あや//ディソナ");
        setAltNames("ダイビンアヤディソナ Daibin Aya Disona");
        setDescription("jp",
                "@U：あなたのメインフェイズ以外でこのシグニが場を離れたとき、%Bを支払いあなたのデッキの一番下のカードをトラッシュに置いてもよい。その後、この方法でデッキからトラッシュに置かれたカードがレベル１の場合、カードを３枚引く。レベル２の場合、対戦相手は手札を３枚捨てる。レベル３以上の場合、対戦相手のシグニ１体を対象とし、それをバニッシュする。スペルの場合、対戦相手の手札を見て１枚選び、捨てさせる。\n" +
                "@E：あなたのデッキの一番下のカードを見る。"
        );

        setName("en", "Aya//Dissona, Master Trickster");
        setDescription("en",
                "@U: When this SIGNI leaves the field outside of your main phase, you may pay %B and put the bottom card of your deck into your trash. Then, if the card put into your trash this way is level one, draw three cards. If it is level two, your opponent discards three cards. If it is level three or more, vanish target SIGNI on your opponent's field. If it is a spell, look at your opponent's hand and choose a card. Your opponent discards it.\n@E: Look at the bottom card of your deck."
        );
        
        setName("en_fan", "Aya//Dissona, Great Trap");
        setDescription("en_fan",
                "@U: When this SIGNI leaves the field other than during your main phase, you may pay %B and put the bottom card of your deck into the trash. Then, if the card put into the trash this way is level 1, draw 3 cards. If it is level 2, your opponent discards 3 cards from their hand. If it is level 3 or more, target 1 of your opponent's SIGNI, and banish it. If it is a spell, look at your opponent's hand, and choose 1 card from it, and discard it.\n" +
                "@E: Look at the bottom card of your deck."
        );

		setName("zh_simplified", "大罠 亚弥//失调");
        setDescription("zh_simplified", 
                "@U 当在你的主要阶段以外把这只精灵离场时，可以支付%B:且把你的牌组最下面的牌放置到废弃区。然后，这个方法从牌组放置到废弃区的牌是等级1的场合，抽3张牌。等级2的场合，对战对手把手牌3张舍弃。等级3以上的场合，对战对手的精灵1只作为对象，将其破坏。魔法的场合，看对战对手的手牌选1张，舍弃。\n" +
                "@E :看你的牌组最下面的牌。\n"
        );

        setCardFlags(CardFlag.DISSONA);

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
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return (!isOwnTurn() || getCurrentPhase() != GamePhase.MAIN) && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(payEner(Cost.color(CardColor.BLUE, 1)))
            {
                CardIndex cardIndex = millDeck(1, DeckPosition.BOTTOM).get();
                
                if(cardIndex != null)
                {
                    if(cardIndex.getIndexedInstance().getTypeByRef() != CardType.SPELL)
                    {
                        int level = cardIndex.getIndexedInstance().getLevelByRef();
                        
                        switch(level)
                        {
                            case 0 -> {}
                            case 1 -> draw(3);
                            case 2 -> discard(getOpponent(), 3);
                            default -> {
                                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                                banish(target);
                            }
                        }
                    } else {
                        reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
                        
                        cardIndex = playerTargetCard(new TargetFilter(TargetHint.DISCARD).OP().fromRevealed()).get();
                        discard(cardIndex);
                        
                        addToHand(getCardsInRevealed(getOpponent()));
                    }
                }
            }
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = look(CardLocation.DECK_MAIN, 0);
            returnToDeck(cardIndex, DeckPosition.BOTTOM);
        }
    }
}
