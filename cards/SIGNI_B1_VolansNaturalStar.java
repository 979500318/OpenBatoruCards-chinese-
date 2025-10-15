package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_VolansNaturalStar extends Card {

    public SIGNI_B1_VolansNaturalStar()
    {
        setImageSets("WXDi-P09-064");

        setOriginalName("羅星　ヴォランス");
        setAltNames("ラセイヴォランス Rasei Voransu");
        setDescription("jp",
                "@E：対戦相手は手札を２枚まで捨ててよい。対戦相手はこの方法で捨てたカード１枚につきカードを１枚引く。"
        );

        setName("en", "Volans, Natural Planet");
        setDescription("en",
                "@E: Your opponent may discard up to two cards. Your opponent draws a card for each card discarded this way."
        );
        
        setName("en_fan", "Volans, Natural Star");
        setDescription("en_fan",
                "@E: Your opponent may discard up to 2 cards from their hand. Your opponent draws a card for each card discarded this way."
        );

		setName("zh_simplified", "罗星 飞鱼座");
        setDescription("zh_simplified", 
                "@E :对战对手可以把手牌2张最多舍弃。对战对手依据这个方法舍弃的牌的数量，每有1张就抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(7000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            DataTable<CardIndex> data = discard(getOpponent(), 0,2);
            
            if(data.get() != null)
            {
                draw(getOpponent(), data.size());
            }
        }
    }
}
