package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_G1_MamaHappy extends Card {

    public LRIGA_G1_MamaHappy()
    {
        setImageSets("WXDi-P14-029");

        setOriginalName("ママ♥ハッピー");
        setAltNames("ママハッピー Mama Happii");
        setDescription("jp",
                "@E：【エナチャージ１】\n" +
                "@E：#C #Cを得る。"
        );

        setName("en", "Mama ♥ Happy");
        setDescription("en",
                "@E: [[Ener Charge 1]].\n@E: Gain #C #C."
        );
        
        setName("en_fan", "Mama♥Happy");
        setDescription("en_fan",
                "@E: [[Ener Charge 1]]\n" +
                "@E: Gain #C #C."
        );

		setName("zh_simplified", "妈妈♥快乐");
        setDescription("zh_simplified", 
                "@E :[[能量填充1]]\n" +
                "@E :得到#C #C。\n" +
                "（玩家保留币的上限是5个）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAMA);
        setColor(CardColor.GREEN);
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            enerCharge(1);
        }
        
        private void onEnterEff2()
        {
            gainCoins(2);
        }
    }
}
