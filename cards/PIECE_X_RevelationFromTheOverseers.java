package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst;

public final class PIECE_X_RevelationFromTheOverseers extends Card {

    public PIECE_X_RevelationFromTheOverseers()
    {
        setImageSets("WXDi-P15-005");

        setOriginalName("俯瞰者からの啓示");
        setAltNames("フカンシャカラノケイジ Fukansha kara no Keiji");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中からあなたの場にいるルリグ１体につきそのルリグと共通する色を持つカードをそれぞれ１枚まで公開し、残りを好きな順番でデッキの一番下に置く。この方法で公開したカードを好きな枚数手札に加え、残りをエナゾーンに置く。"
        );

        setName("en", "Revelation from the Overseers");
        setDescription("en",
                "Look at the top five cards of your deck. For each LRIG on your field, reveal up to one card that shares a color with that LRIG from among them. Put the rest on the bottom of your deck in any order. Add any number of cards revealed this way to your hand and put the rest into your Ener Zone."
        );
        
        setName("en_fan", "Revelation from the Overseers");
        setDescription("en_fan",
                "Look at the top 5 cards of your deck. For each LRIG on your field, reveal up to 1 card from among them that shares a common color with that LRIG, and put the rest on the bottom of your deck in any order. Add any number of cards revealed this way to your hand, and put the rest into the ener zone."
        );

		setName("zh_simplified", "来自俯瞰者的启示");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中依据你的场上的分身的数量，每有1只就把持有与那只分身共通颜色的牌各1张最多公开，剩下的任意顺序放置到牌组最下面。这个方法公开的牌任意张数加入手牌，剩下的放置到能量区。\n"
        );

        setType(CardType.PIECE);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

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
            look(5);

            DataTable<CardIndex> data = new DataTable<>();
            forEachLRIGOnField(getOwner(), cardIndex -> {
                data.add(playerTargetCard(0,1, new TargetFilter(TargetHint.REVEAL).own().withColor(cardIndex.getIndexedInstance().getColor()).except(data).fromLooked()).get());
            });
            reveal(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            DataTable<CardIndex> dataToHand = playerTargetCard(0, AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.HAND).own().fromRevealed());
            addToHand(dataToHand);
            
            putInEner(getCardsInRevealed(getOwner()));
        }
    }
}
