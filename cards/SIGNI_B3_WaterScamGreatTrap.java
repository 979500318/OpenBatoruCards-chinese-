package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B3_WaterScamGreatTrap extends Card {
    
    public SIGNI_B3_WaterScamGreatTrap()
    {
        setImageSets("WXDi-P02-070", "SPDi38-24");
        
        setOriginalName("大罠　ウソスイ");
        setAltNames("ダイビンウソスイ Daibin Usosui");
        setDescription("jp",
                "@E：あなたのシグニゾーンに裏向きのカードがある場合、カードを１枚引く。"
        );
        
        setName("en", "Con Aqua, Master Trickster");
        setDescription("en",
                "@E: If there is a face-down card in one of your SIGNI Zones, draw a card."
        );
        
        setName("en_fan", "Water Scam, Great Trap");
        setDescription("en_fan",
                "@E: If there is a face-down card on your SIGNI zones, draw 1 card."
        );
        
		setName("zh_simplified", "大罠 健康水");
        setDescription("zh_simplified", 
                "@E :你的精灵区有里向的牌的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            if(new TargetFilter().own().not(new TargetFilter().faceUp()).fromSafeLocation(CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT).getValidTargetsCount() > 0)
            {
                draw(1);
            }
        }
    }
}
