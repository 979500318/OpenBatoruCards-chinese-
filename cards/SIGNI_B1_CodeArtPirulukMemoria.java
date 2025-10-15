package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B1_CodeArtPirulukMemoria extends Card {

    public SIGNI_B1_CodeArtPirulukMemoria()
    {
        setImageSets("WXDi-P08-063", "WXDi-P08-063P");

        setOriginalName("コードアート　ピルルク//メモリア");
        setAltNames("コードアートピルルクメモリア Koodo Aato Piruruku Memoria");
        setDescription("jp",
                "@E @[手札からスペルを１枚捨てる]@：あなたのデッキの上からカードを３枚見る。その中から好きな枚数のカードを好きな順番でデッキの一番上に戻し、残りを好きな順番でデッキの一番下に置く。カードを１枚引く。"
        );

        setName("en", "Piruluk//Memoria, Code: Art");
        setDescription("en",
                "@E @[Discard a spell]@: Look at the top three cards of your deck. Put any number of cards on top of your deck in any order. Put the rest on the bottom of your deck in any order. Draw a card."
        );
        
        setName("en_fan", "Code Art Piruluk//Memoria");
        setDescription("en_fan",
                "@E @[Discard 1 spell from your hand]@: Look at the top 3 cards of your deck. Return any number of cards from among them to the top of your deck in any order, and put the rest on the bottom of your deck in any order. Draw 1 card."
        );

		setName("zh_simplified", "必杀代号 皮璐璐可//回忆");
        setDescription("zh_simplified", 
                "@E 从手牌把魔法1张舍弃:从你的牌组上面看3张牌。从中把任意张数的牌任意顺序返回牌组最上面，剩下的任意顺序放置到牌组最下面。抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(new TargetFilter().spell()), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            look(3);

            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.TOP).own().fromLooked());
            returnToDeck(data, DeckPosition.TOP);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            draw(1);
        }
    }
}
