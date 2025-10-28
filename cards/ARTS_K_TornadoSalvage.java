package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class ARTS_K_TornadoSalvage extends Card {

    public ARTS_K_TornadoSalvage()
    {
        setImageSets("WXK01-032");

        setOriginalName("トルネード・サルベージ");
        setAltNames("トルネードサルベージ Toruneedo Sarubeeji");
        setDescription("jp",
                "あなたのトラッシュから黒のシグニを３枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "Tornado Salvage");
        setDescription("en",
                "Target up to 3 black SIGNI from your trash, and add them to your hand."
        );

        setName("zh_simplified", "龙卷·营救");
        setDescription("zh_simplified", 
                "从你的废弃区把黑色的精灵3张最多作为对象，将这些加入手牌。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerARTSAbility(this::onARTSEff);
        }
        
        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash());
            addToHand(data);
        }
    }
}
