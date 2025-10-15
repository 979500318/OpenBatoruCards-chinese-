package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class SPELL_X_VictimMemories extends Card {

    public SPELL_X_VictimMemories()
    {
        setImageSets("SP38-007");

        setOriginalName("ヴィクティム・メモリーズ");
        setAltNames("ヴィクティムメモリーズ Buikutimu Memoriizu");
        setDescription("jp",
                "あなたのデッキの上からカードを１０枚見る。その中からカードを１枚まで手札に加え、残りをデッキに加えてシャッフルする。" +
                "~#：あなたのデッキの上からカードを１０枚見る。その中からカードを１枚まで手札に加え、残りをデッキに加えてシャッフルする。"
        );

        setName("en", "Victim Memories");
        setDescription("en",
                "Look at the top 10 cards of your deck. Add up to 1 card from among them to your hand, and shuffle the rest into your deck." +
                "~#Look at the top 10 cards of your deck. Add up to 1 card from among them to your hand, and shuffle the rest into your deck."
        );

		setName("zh_simplified", "祭牲·追想");
        setDescription("zh_simplified", 
                "从你的牌组上面看10张牌。从中把牌1张最多加入手牌，剩下的加入牌组洗切。" +
                "~#从你的牌组上面看10张牌。从中把牌1张最多加入手牌，剩下的加入牌组洗切。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setCost(Cost.colorless(1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerSpellAbility(this::onSpellEff);

            registerLifeBurstAbility(this::onSpellEff);
        }
        
        private void onSpellEff()
        {
            look(10);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            returnToDeck(getCardsInLooked(getOwner()), DeckPosition.TOP);
            shuffleDeck();
        }
    }
}
