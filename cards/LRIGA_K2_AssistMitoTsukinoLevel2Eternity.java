package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K2_AssistMitoTsukinoLevel2Eternity extends Card {

    public LRIGA_K2_AssistMitoTsukinoLevel2Eternity()
    {
        setImageSets("WXDi-CP01-021");

        setOriginalName("【アシスト】月ノ美兎　レベル２【永遠】");
        setAltNames("アシストツキノミトレベルニエイエン Ashisuto Tsukino Mito Reberu Ni Eien Assist Mito Assist Tsukino");
        setDescription("jp",
                "@E：あなたのトラッシュからすべてのカードをデッキに加えてシャッフルし、あなたのデッキの上からカードを１６枚トラッシュに置く。\n" +
                "@E：あなたのトラッシュからシグニを２枚まで対象とし、それらを場に出す。"
        );

        setName("en", "[Assist] Mito, Level 2 [Eternity]");
        setDescription("en",
                "@E: Shuffle all cards from your trash into your deck. Put the top sixteen cards of your deck into your trash.\n@E: Put up to two target SIGNI from your trash onto your field.\n"
        );
        
        setName("en_fan", "[Assist] Mito Tsukino Level 2 [Eternity]");
        setDescription("en_fan",
                "@E: Shuffle all cards from your trash into your deck, and put the top 16 cards of your deck into the trash.\n" +
                "@E: Target up to 2 SIGNI from your trash, and put them onto the field."
        );

		setName("zh_simplified", "【支援】月之美兔 等级2【永远】");
        setDescription("zh_simplified", 
                "@E :从你的废弃区把全部的牌加入牌组洗切，从你的牌组上面把16张牌放置到废弃区。\n" +
                "@E :从你的废弃区把精灵2张最多作为对象，将这些出场。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MITO);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            returnToDeck(getCardsInTrash(getOwner()), DeckPosition.TOP);
            shuffleDeck();
            
            millDeck(16);
        }
        
        private void onEnterEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable());
            putOnField(data);
        }
    }
}
