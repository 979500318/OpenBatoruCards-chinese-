package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
public final class SPELL_B_FessoneMagicBLUE extends Card {

    public SPELL_B_FessoneMagicBLUE()
    {
        setImageSets("WXDi-P14-TK03");

        setOriginalName("フェゾーネマジック・BLUE");
        setAltNames("フェゾーネマジックブルー Fezoone Majikku Buruu");
        setDescription("jp",
                "((クラフトであるスペルは、使用後にゲームから除外される))\n\n" +
                "カードを３枚引き、手札を２枚捨てる。"
        );

        setName("en", "Fesonne Magic: BLUE");
        setDescription("en",
                "((This spell can be used from your LRIG Deck during your main phase.))\n" +
                "((Spells that are Craft are removed from the game after they are used.))\n\n" +
                "Draw three cards and discard two cards."
        );
        
        setName("en_fan", "Fessone Magic - BLUE");
        setDescription("en_fan",
                "((This craft is excluded from the game after use))\n\n" +
                "Draw 3 cards, and discard 2 cards from your hand."
        );

		setName("zh_simplified", "音乐节魔术·BLUE");
        setDescription("zh_simplified", 
                "抽3张牌，手牌2张舍弃。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
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

            registerSpellAbility(this::onSpellEff);
        }
        
        private void onSpellEff()
        {
            draw(3);
            discard(2);
        }
    }
}
