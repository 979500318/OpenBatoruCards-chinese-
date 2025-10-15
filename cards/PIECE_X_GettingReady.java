package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class PIECE_X_GettingReady extends Card {

    public PIECE_X_GettingReady()
    {
        setImageSets("WX25-P2-048");

        setOriginalName("ゲッティング・レディ");
        setAltNames("ゲッティングレディ Gettingu Redi");
        setDescription("jp",
                "あなたのセンタールリグがアップ状態の場合、カードを２枚引く。あなたのすべてのルリグをアップする。"
        );

        setName("en", "Getting Ready");
        setDescription("en",
                "If your center LRIG is upped, draw 2 cards. Up all of your LRIG."
        );

		setName("zh_simplified", "返场·准备");
        setDescription("zh_simplified", 
                "你的核心分身在竖直状态的场合，抽2张牌。你的全部的分身竖直。\n"
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
            if(!getLRIG(getOwner()).getIndexedInstance().isState(CardStateFlag.DOWNED))
            {
                draw(2);
            }
            
            up(getLRIGs(getOwner()));
        }
    }
}
