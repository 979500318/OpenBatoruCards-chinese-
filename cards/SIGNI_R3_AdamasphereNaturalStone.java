package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_R3_AdamasphereNaturalStone extends Card {
    
    public SIGNI_R3_AdamasphereNaturalStone()
    {
        setImageSets("WXDi-D03-017");
        
        setOriginalName("羅石　アダマスフィア");
        setAltNames("ラセキアダマスフィア Raseki Adamasufia");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、%R %Rあなたのデッキの一番上を公開する。そのカードがレベル３のシグニの場合、を支払ってもよい。そうした場合、ターン終了時まで、このシグニは[[アサシン]]を得る。"
        );
        
        setName("en", "Adamanthia, Natural Crystal");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If that card is a level three SIGNI, you may pay %R %R. If you do, this SIGNI gains [[Assassin]] until end of turn."
        );
        
        setName("en_fan", "Adamasphere, Natural Stone");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If that card is a level 3 SIGNI, you may pay %R %R. If you do, until end of turn, this SIGNI gains [[Assassin]]."
        );
        
		setName("zh_simplified", "罗石 金刚珠玉");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的牌组最上面公开。那张牌是等级3的精灵的场合，可以支付%R %R。这样做的场合，直到回合结束时为止，这只精灵得到[[暗杀]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null && CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()))
            {
                int level = cardIndex.getIndexedInstance().getLevelByRef();
                returnToDeck(cardIndex, DeckPosition.TOP);

                if(level == 3 && payEner(Cost.color(CardColor.RED, 2)))
                {
                    attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
                }
            }
        }
    }
}
