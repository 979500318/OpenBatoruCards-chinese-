package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_R_BurnforaRighteousCause extends Card {

    public ARTS_R_BurnforaRighteousCause()
    {
        setImageSets("WXK01-013");

        setOriginalName("焦身成仁");
        setAltNames("ショウシンセイジン Shoushin Seijin");
        setDescription("jp",
                "カードを４枚引く。対戦相手はあなたの手札を１枚見ないで選び、あなたはそれを捨てる。"
        );

        setName("en", "Burn for a Righteous Cause");
        setDescription("en",
                "Draw 4 cards. Your opponent chooses 1 card from your hand without looking, and you discard it."
        );

        setName("zh_simplified", "焦身成仁");
        setDescription("zh_simplified", 
                "抽4张牌。对战对手不看你的手牌选1张，你将其舍弃。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
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
            draw(4);
            
            CardIndex cardIndex = playerChoiceHand(getOpponent(), 1).get();
            discard(cardIndex);
        }
    }
}
