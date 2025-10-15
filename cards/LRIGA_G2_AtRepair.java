package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.CardAbilities;

import java.util.List;

public final class LRIGA_G2_AtRepair extends Card {
    
    public LRIGA_G2_AtRepair()
    {
        setImageSets("WXDi-P00-023");
        
        setOriginalName("アト＝リペアー");
        setAltNames("アトリペアー Ato Ripeaa");
        setDescription("jp",
                "@E：あなたのトラッシュから#Gを持たない、それぞれ異なるクラスを持つシグニ７枚を対象とし、それらをデッキに加えてシャッフルする。そうした場合、あなたのデッキの一番上をライフクロスに加える。"
        );
        
        setName("en", "At =Repair=");
        setDescription("en",
                "@E: Shuffle seven target SIGNI with different classes and without #G from your trash into your deck. If you do, add the top card of your deck to your Life Cloth."
        );
        
        setName("en_fan", "At-Repair");
        setDescription("en_fan",
                "@E: Target 7 SIGNI with different classes and without #G @[Guard]@ from your trash, and return them to the deck and shuffle it. If you do, add the top card of your deck to life cloth."
        );
        
		setName("zh_simplified", "亚特=修补");
        setDescription("zh_simplified", 
                "@E 从你的废弃区把不持有#G且，持有不同类别的精灵7张作为对象，将这些加入牌组洗切。这样做的场合，你的牌组最上面的牌加入生命护甲。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AT);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            TargetFilter filter = new TargetFilter(TargetHint.SHUFFLE).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash();
            if(CardAbilities.getSIGNIClasses(filter.getExportedData()).size() >= 7)
            {
                DataTable<CardIndex> data = playerTargetCard(7, filter, this::onEnterEffTargetCond);
                if(data.get() != null)
                {
                    int countReturned = returnToDeck(data, DeckPosition.TOP);
                    
                    if(countReturned > 0)
                    {
                        shuffleDeck();
                        if(countReturned == 7) addToLifeCloth(1);
                    }
                }
            }
        }
        private boolean onEnterEffTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 7 && CardAbilities.getSIGNIClasses(new DataTable<>(listPickedCards)).size() >= 7;
        }
    }
}
