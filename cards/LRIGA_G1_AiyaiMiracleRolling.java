package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_G1_AiyaiMiracleRolling extends Card {

    public LRIGA_G1_AiyaiMiracleRolling()
    {
        setImageSets("WXDi-P12-039");

        setOriginalName("アイヤイ　ミラクルローリング");
        setAltNames("アイヤイミラクルローリング Aiyai Mirakuru Rooringu");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中からカード１枚と、そのカードと共通する色を持たないカードを１枚までエナゾーンに置き、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Aiyai, Miracle Rolling");
        setDescription("en",
                "@E: Look at the top five cards of your deck. Put a card and up to one other card that does not share a color with that card from among them into your Ener Zone. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Aiyai Miracle Rolling");
        setDescription("en_fan",
                "@E: Look at the top 5 cards of your deck. Put 1 card from among them, and up to 1 other card from among them that doesn't share a common color with that card into the ener zone, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "艾娅伊 奇迹逆转");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看5张牌。从中把1张牌和，不持有与那张牌共通颜色的牌1张最多放置到能量区，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AIYAI);
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
            
            DataTable<CardIndex> data = playerTargetCard(new TargetFilter(TargetHint.ENER).own().fromLooked());
            if(data.get() != null)
            {
                TargetFilter filter = new TargetFilter(TargetHint.ENER).own().except(data.get()).fromLooked();
                if(data.get().getIndexedInstance() != null) filter = filter.not(new TargetFilter().withColor(data.get().getIndexedInstance().getColor()));
                data.add(playerTargetCard(0,1, filter).get());
            }
            putInEner(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
