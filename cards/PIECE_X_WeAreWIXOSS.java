package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.CardDataImageSet.Mask;

public final class PIECE_X_WeAreWIXOSS extends Card {
    
    public PIECE_X_WeAreWIXOSS()
    {
        setImageSets(Mask.IGNORE+"PR-Di007");
        
        setOriginalName("ウィーアー ＷＩＸＯＳＳ！");
        setAltNames("ウィーアーウィクロス Uii Aa Uikurosu");
        setDescription("jp",
                "対戦相手はあなたと握手をしてもよい。握手をした場合、各プレイヤーはカードを３枚引く。握手をしなかった場合、あなたはカードを１枚引き[[エナチャージ１]]をする。"
        );
        
        setName("en", "We Are WIXOSS!");
        setDescription("en",
                "Your opponent may choose to accept a handshake with you. If you shake hands, each player draws 3 cards. If you do not shake hands, you draw 1 card and [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "我们是WIXOSS！");
        setDescription("zh_simplified", 
                "对战对手可以与你握手。握手的场合，各玩家抽3张牌。不握手的场合，你抽1张牌并[[能量填充1]]。\n"
        );

        setType(CardType.PIECE);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            if(playerChoiceActivate(getOpponent()))
            {
                draw(3);
                draw(getOpponent(), 3);
            } else {
                draw(1);
                enerCharge(1);
            }
        }
    }
}
