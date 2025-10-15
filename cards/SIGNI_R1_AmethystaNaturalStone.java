package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R1_AmethystaNaturalStone extends Card {
    
    public SIGNI_R1_AmethystaNaturalStone()
    {
        setImageSets("WXDi-P01-055");
        
        setOriginalName("羅石　アメジスタ");
        setAltNames("ラセキアメジスタ Raseki Amejisuta");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、手札を１枚捨ててもよい。そうした場合、カードを１枚引く。"
        );
        
        setName("en", "Amethystal, Natural Crystal");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may discard a card. If you do, draw a card."
        );
        
        setName("en_fan", "Amethysta, Natural Stone");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, you may discard 1 card from your hand. If you do, draw 1 card."
        );
        
		setName("zh_simplified", "罗石 紫水玉");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，可以把手牌1张舍弃。这样做的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            if(discard(0,1).get() != null)
            {
                draw(1);
            }
        }
    }
}
