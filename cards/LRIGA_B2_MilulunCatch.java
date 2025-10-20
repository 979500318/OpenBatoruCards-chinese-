package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_MilulunCatch extends Card {

    public LRIGA_B2_MilulunCatch()
    {
        setImageSets("WXDi-P14-026");

        setOriginalName("ミルルン☆キャッチ");
        setAltNames("ミルルンキャッチ Mirurun Kyatchi");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "@E %B：対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Milulun ☆ Catch");
        setDescription("en",
                "@E: Down up to two target SIGNI on your opponent's field.\n@E %B: Your opponent discards a card at random."
        );
        
        setName("en_fan", "Milulun☆Catch");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "@E %B: Choose 1 card from your opponent's hand without looking, and your opponent discards it."
        );

		setName("zh_simplified", "米璐璐恩☆捕捉");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵2只最多作为对象，将这些横置。\n" +
                "@E %B:不看对战对手的手牌选1张，舍弃。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MILULUN);
        setColor(CardColor.BLUE);
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
        
        private void onEnterEff2()
        {
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
    }
}
