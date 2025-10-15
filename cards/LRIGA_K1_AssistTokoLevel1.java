package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K1_AssistTokoLevel1 extends Card {
    
    public LRIGA_K1_AssistTokoLevel1()
    {
        setImageSets("WXDi-D02-09LA");
        
        setOriginalName("【アシスト】とこ　レベル１");
        setAltNames("アシストトコレベルイチ Ashisuto Toko Reberu Ichi Assist Toko");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。その後、あなたのトラッシュから＜バーチャル＞のシグニを２枚まで対象とし、それらを手札に加える。"
        );
        
        setName("en", "[Assist] Toko Level 1");
        setDescription("en",
                "@E: Put the top 3 cards of your deck into the trash. Then, target up to 2 <<Virtual>> SIGNI from your trash, and add them to your hand."
        );
        
		setName("zh_simplified", "【支援】床 等级1");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把3张牌放置到废弃区。然后，从你的废弃区把<<バーチャル>>精灵2张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TOKO);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
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
            millDeck(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash());
            addToHand(data);
        }
    }
}
