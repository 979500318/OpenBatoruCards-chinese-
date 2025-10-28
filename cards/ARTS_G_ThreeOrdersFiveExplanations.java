package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.DataTable;

public final class ARTS_G_ThreeOrdersFiveExplanations extends Card {

    public ARTS_G_ThreeOrdersFiveExplanations()
    {
        setImageSets("WXK01-026");

        setOriginalName("三令五申");
        setAltNames("サウンドグッド Saundo Guddo Sound Good");
        setDescription("jp",
                "あなたのエナゾーンからカードを３枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "Three Orders, Five Explanations");
        setDescription("en",
                "Target up to 3 cards from your ener zone, and add them to your hand."
        );

        setName("zh_simplified", "三令五申");
        setDescription("zh_simplified", 
                "从你的能量区把牌3张最多作为对象，将这些加入手牌。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

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
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().fromEner());
            addToHand(data);
        }
    }
}
