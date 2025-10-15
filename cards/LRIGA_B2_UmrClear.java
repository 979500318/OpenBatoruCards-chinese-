package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.DataTable;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_UmrClear extends Card {

    public LRIGA_B2_UmrClear()
    {
        setImageSets("WXDi-P00-018");

        setOriginalName("ウムル＝クリアー");
        setAltNames("ウムルクリアー Umuru Kuriaa");
        setDescription("jp",
                "@E：あなたは手札をすべて捨て、捨てたカードの枚数に１を加えた枚数のカードを引く。対戦相手は手札をすべて捨て、捨てたカードの枚数に１を引いた枚数のカードを引く。\n" +
                "@E %X：対戦相手のルリグ１体を対象とし、それを凍結する。"
        );

        setName("en", "Umr =Clear=");
        setDescription("en",
                "@E: Discard all cards from your hand and draw cards equal to the number of cards you discarded plus one. Your opponent discards their hand and draws cards equal to the number of cards they discarded minus one.\n" +
                "@E %X: Freeze target LRIG on your opponent's field."
        );

        setName("en_fan", "Umr-Clear");
        setDescription("en_fan",
                "@E: You discard all cards from your hand, and draw cards equal to the number of cards discarded this way plus 1. Your opponent discards all cards from their hand, and draws cards equal to the number of cards discarded this way minus 1.\n" +
                "@E %X: Target your opponent's LRIG and freeze it."
        );

		setName("zh_simplified", "乌姆尔=洁净");
        setDescription("zh_simplified", 
                "@E :你把手牌全部舍弃，抽舍弃的牌的张数加1的张数的牌。对战对手把手牌全部舍弃，抽舍弃的牌的张数少1的张数的牌。\n" +
                "@E %X:对战对手的分身1只作为对象，将其冻结。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.UMR);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.BLUE);
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
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            DataTable<CardIndex> data = discard(getCardsInHand(getOwner()));
            draw(data.get() != null ? data.size()+1 : 1);
            
            data = discard(getCardsInHand(getOpponent()));
            if(data.get() != null) draw(getOpponent(), data.size()-1);
        }

        private void onEnterEff2()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
            freeze(cardIndex);
        }
    }
}
