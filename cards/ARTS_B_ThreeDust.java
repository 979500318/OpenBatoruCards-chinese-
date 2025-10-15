package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class ARTS_B_ThreeDust extends Card {

    public ARTS_B_ThreeDust()
    {
        setImageSets("WDK02-007");

        setOriginalName("スリー・ダスト");
        setAltNames("スリーダスト Surii Dasuto");
        setDescription("jp",
                "このアーツはあなたのセンタールリグがレベル３以上の場合にしか使用できない。\n\n" +
                "対戦相手の手札を３枚見ないで選び、捨てさせる。"
        );

        setName("en", "Three Dust");
        setDescription("en",
                "This ARTS can only be used if your center LRIG is level 3 or higher.\n\n" +
                "Choose 3 cards from your opponent's hand without looking, and discard them."
        );

		setName("zh_simplified", "三重·除灰");
        setDescription("zh_simplified", 
                "这张必杀只有在你的核心分身在等级3以上的场合才能使用。\n" +
                "不看对战对手的手牌选3张，舍弃。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
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

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
        }
        
        private ConditionState onARTSEffCond()
        {
            return getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerChoiceHand(3);
            discard(data);
        }
    }
}
