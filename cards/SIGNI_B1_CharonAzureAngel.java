package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B1_CharonAzureAngel extends Card {
    
    public SIGNI_B1_CharonAzureAngel()
    {
        setImageSets("WXDi-P01-061");
        
        setOriginalName("蒼天　カロン");
        setAltNames("ソウテンカロン Souten Karon");
        setDescription("jp",
                "@E %X：カードを１枚引く。"
        );
        
        setName("en", "Charon, Azure Angel");
        setDescription("en",
                "@E %X: Draw a card."
        );
        
        setName("en_fan", "Charon, Azure Angel");
        setDescription("en_fan",
                "@E %X: Draw 1 card."
        );
        
		setName("zh_simplified", "苍天 卡戎");
        setDescription("zh_simplified", 
                "@E %X:抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            draw(1);
        }
    }
}
