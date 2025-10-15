package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B3_H2NaturalSource extends Card {
    
    public SIGNI_B3_H2NaturalSource()
    {
        setImageSets("WXDi-P05-067");
        
        setOriginalName("羅原　Ｈ２");
        setAltNames("ラゲンエイチツー Ragen Eichi Tsuu");
        setDescription("jp",
                "@U：このシグニがライズされたとき、対戦相手の手札を１枚見ないで選び、捨てさせる。\n" +
                "@E %B：カードを１枚引く。"
        );
        
        setName("en", "H2, Natural Element");
        setDescription("en",
                "@U: Whenever this SIGNI is risen, your opponent discards a card at random.\n" +
                "@E %B: Draw a card."
        );
        
        setName("en_fan", "H2, Natural Source");
        setDescription("en_fan",
                "@U: When you rise on this SIGNI, choose 1 card from your opponent's hand without looking, and discard it.\n" +
                "@E %B: Draw 1 card."
        );
        
		setName("zh_simplified", "罗原 H2");
        setDescription("zh_simplified", 
                "@U :当这只精灵被升阶时，不看对战对手的手牌选1张，舍弃。\n" +
                "@E %B:抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.RISE, this::onAutoEff);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
        
        private void onEnterEff()
        {
            draw(1);
        }
    }
}
