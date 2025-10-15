package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_R1_LaylaTheFang extends Card {

    public LRIGA_R1_LaylaTheFang()
    {
        setImageSets("WXDi-P12-028");

        setOriginalName("レイラ・ザ・ファング");
        setAltNames("レイラザファング Reira Za Fangu");
        setDescription("jp",
                "@E @[手札を３枚捨てる]@：対戦相手のライフクロス１枚をクラッシュする。"
        );

        setName("en", "Layla the Fang");
        setDescription("en",
                "@E @[Discard three cards]@: Crush one of your opponent's Life Cloth.\n"
        );
        
        setName("en_fan", "Layla the Fang");
        setDescription("en_fan",
                "@E @[Discard 3 cards from your hand]@: Crush 1 of your opponent's life cloth."
        );

		setName("zh_simplified", "蕾拉·极·尖牙");
        setDescription("zh_simplified", 
                "@E 手牌3张舍弃:对战对手的生命护甲1张击溃。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LAYLA);
        setColor(CardColor.RED);
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

            registerEnterAbility(new DiscardCost(3), this::onEnterEff);
        }

        private void onEnterEff()
        {
            crush(getOpponent());
        }
    }
}
