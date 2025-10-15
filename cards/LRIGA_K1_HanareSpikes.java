package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.DataTable;

import java.util.List;

public final class LRIGA_K1_HanareSpikes extends Card {

    public LRIGA_K1_HanareSpikes()
    {
        setImageSets("WXDi-P15-043");

        setOriginalName("ハナレ//スパイクス");
        setAltNames("ハナレスパイクス Hanare Supaikusu");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを２枚トラッシュに置く。その後、あなたのトラッシュからそれぞれ共通する色を持つシグニ２枚を対象とし、それらを場に出す。"
        );

        setName("en", "Hanare//Spikes");
        setDescription("en",
                "@E: Put the top two cards of your deck into your trash. Then, put two target SIGNI that share a color from your trash onto your field."
        );
        
        setName("en_fan", "Hanare//Spikes");
        setDescription("en_fan",
                "@E: Put the top 2 cards of your deck into the trash. Then, target 2 SIGNI that share a common color from your trash, and put them onto the field."
        );

		setName("zh_simplified", "离//钉刺");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把2张牌放置到废弃区。然后，从你的废弃区把持有共通颜色的精灵2张作为对象，将这些出场。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.HANARE);
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
            
            TargetFilter filter = new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash();
            
            if(hasValidTargets(filter.getExportedData()))
            {
                DataTable<CardIndex> data = playerTargetCard(2, filter, this::onEnterEffTargetCond);
                putOnField(data);
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
                    if(dataColor1.matches(dataColor2)) return true;
                }
            }
            return false;
        }
        private boolean onEnterEffTargetCond(List<CardIndex> pickedCards)
        {
            return pickedCards.size() == 2 && pickedCards.get(0).getIndexedInstance().getColor().matches(pickedCards.get(1).getIndexedInstance().getColor());
        }
    }
}
