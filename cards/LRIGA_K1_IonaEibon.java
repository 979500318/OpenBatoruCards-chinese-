package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;

import java.util.List;

public final class LRIGA_K1_IonaEibon extends Card {

    public LRIGA_K1_IonaEibon()
    {
        setImageSets("WXDi-P13-034");

        setOriginalName("イオナ・エイボン");
        setAltNames("イオナエイボン");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを２枚トラッシュに置く。その後、あなたのトラッシュからそれぞれ共通する色を持たず無色ではないシグニ２枚を対象とし、それらを手札に加える。"
        );

        setName("en", "Iona Eibon");
        setDescription("en",
                "@E: Put the top two cards of your deck into your trash. Then, add two target non-colorless SIGNI that do not share a color from your trash to your hand."
        );
        
        setName("en_fan", "Iona Eibon");
        setDescription("en_fan",
                "@E: Put the top 2 cards of your deck into the trash. Then, target 2 non-colorless SIGNI that don't share a common color from your trash, and add them to your hand."
        );

		setName("zh_simplified", "伊绪奈·艾本");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把2张牌放置到废弃区。然后，从你的废弃区把不持有共通颜色的不是无色的精灵2张作为对象，将这些加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.BLACK);
        setLevel(1);
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

            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            millDeck(2);
            
            TargetFilter filter = new TargetFilter(TargetHint.HAND).own().SIGNI().withColor().fromTrash();
            
            if(hasValidTargets(filter.getExportedData()))
            {
                DataTable<CardIndex> targets = playerTargetCard(2, filter, this::onEnterEffTargetCond);
                addToHand(targets);
            }
        }
        private boolean hasValidTargets(DataTable<CardIndex> data)
        {
            for(int i=0;i<data.size()-1;i++)
            {
                CardDataColor dataColor1 = data.get(i).getIndexedInstance().getColor();
                for(int ii=i+1;ii<data.size();ii++)
                {
                    CardDataColor dataColor2 = data.get(ii).getIndexedInstance().getColor();
                    if(!dataColor1.matches(dataColor2)) return true;
                }
            }
            return false;
        }
        private boolean onEnterEffTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 2 && !listPickedCards.get(0).getIndexedInstance().getColor().matches(listPickedCards.get(1).getIndexedInstance().getColor());
        }
    }
}
