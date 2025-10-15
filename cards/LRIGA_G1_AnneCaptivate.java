package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_G1_AnneCaptivate extends Card {

    public LRIGA_G1_AnneCaptivate()
    {
        setImageSets("WXDi-P11-035");

        setOriginalName("アン － 魅セ");
        setAltNames("アンミセ An Mise");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中からあなたのセンタールリグと共通する色を持つカードを２枚までエナゾーンに置き、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Ann - Enchant");
        setDescription("en",
                "@E: Look at the top five cards of your deck. Put up to two cards that share a color with your Center LRIG from among them into your Ener Zone. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Anne Captivate");
        setDescription("en_fan",
                "@E: Look at the top 5 cards of your deck. Put up to 2 cards that share a common color with your center LRIG from among them into the ener zone, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "安 - 魅眼");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看5张牌。从中把持有与你的核心分身共通颜色的牌2张最多放置到能量区，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ENER).own().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromLooked());
            putInEner(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
