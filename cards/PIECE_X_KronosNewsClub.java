package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class PIECE_X_KronosNewsClub extends Card {

    public PIECE_X_KronosNewsClub()
    {
        setImageSets("WXDi-CP02-006");

        setOriginalName("クロノス報道部");
        setAltNames("クロノスホウドウブ Kuronosu Houdoubu");
        setDescription("jp",
                "カードを２枚引き、手札を２枚捨てる。その後、好きな生徒１人との絆を獲得する。"
        );

        setName("en", "Kronos School of Journalism");
        setDescription("en",
                "Draw two cards and discard two cards. Then, start a relationship with any one student.\n"
        );
        
        setName("en_fan", "Kronos News Club");
        setDescription("en_fan",
                "Draw 2 cards, and discard 2 cards from your hand. Then, gain a bond with a student of your choice."
        );

		setName("zh_simplified", "克罗诺斯新闻部");
        setDescription("zh_simplified", 
                "抽2张牌，手牌2张舍弃。然后，获得与任意学生1人的羁绊。\n"
        );

        setType(CardType.PIECE);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            draw(2);
            discard(2);
            
            playerChoiceBond();
        }
    }
}
