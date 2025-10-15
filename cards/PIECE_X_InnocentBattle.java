package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.Game.GamePlayerRole;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class PIECE_X_InnocentBattle extends Card {
    
    public PIECE_X_InnocentBattle()
    {
        setImageSets("WXDi-P04-005", "PR-Di020");
        
        setOriginalName("イノセントバトル");
        setAltNames("Inosento Batoru");
        setDescription("jp",
                "あなたか対戦相手は自分のトラッシュにあるすべてのカードをデッキに加えてシャッフルする。あなたはカードを１枚引く。"
        );
        
        setName("en", "Innocent Battle");
        setDescription("en",
                "Choose a player. That player shuffles all cards in their trash into their deck. You draw a card."
        );
        
        setName("en_fan", "Innocent Battle");
        setDescription("en_fan",
                "You or your opponent returns all the cards from their trash to their deck, and shuffles it. You draw 1 card."
        );
        
		setName("zh_simplified", "纯真战斗");
        setDescription("zh_simplified", 
                "你或对战对手把自己的废弃区的全部的牌加入牌组洗切。你抽1张牌。\n"
        );

        setType(CardType.PIECE);
        setCost(Cost.colorless(1));
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
            
            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            GamePlayerRole rolePlayer = playerChoiceAction(ActionHint.OWN, ActionHint.OPPONENT) == 1 ? getOwner() : getOpponent();
            
            returnToDeck(getCardsInTrash(rolePlayer), DeckPosition.TOP);
            shuffleDeck(rolePlayer);
            
            draw(1);
        }
    }
}
