package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_B4_AllosPirulukTET extends Card {

    public LRIG_B4_AllosPirulukTET()
    {
        setImageSets("WDK02-001", "WDK-F01-01");

        setOriginalName("アロス・ピルルク　ＴＥＴ");
        setAltNames("アロスピルルクテト Arosu Piruruku Teto");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、%Bを支払ってもよい。そうした場合、カードを１枚引く。\n" +
                "@E：対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Allos Piruluk TET");
        setDescription("en",
                "@U: Whenever this LRIG attacks, you may pay %B. If you do, draw 1 card.\n" +
                "@E: Choose 1 card from your opponent's hand without looking, and discard it."
        );

		setName("zh_simplified", "阿洛斯·皮璐璐可 TET");
        setDescription("zh_simplified", 
                "@U :当这只分身攻击时，可以支付%B。这样做的场合，抽1张牌。\n" +
                "@E :不看对战对手的手牌选1张，舍弃。\n"
        );

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setLevel(4);
        setLimit(11);
        setCoins(+3);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            if(payEner(Cost.color(CardColor.BLUE, 1)))
            {
                draw(1);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
    }
}
