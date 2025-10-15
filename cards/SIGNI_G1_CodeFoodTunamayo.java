package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_CodeFoodTunamayo extends Card {
    
    public SIGNI_G1_CodeFoodTunamayo()
    {
        setImageSets("WXDi-P07-080");
        
        setOriginalName("コードフード　ツナマヨ");
        setAltNames("コードフードツナマヨ Koodo Fuudo Tunamayo");
        setDescription("jp",
                "@E：あなたのシグニの下にあるカード１枚を対象とし、それをエナゾーンに置く。"
        );
        
        setName("en", "Tuna Mayo, Code: Eat");
        setDescription("en",
                "@E: Put target card underneath a SIGNI on your field into your Ener Zone."
        );
        
        setName("en_fan", "Code Food Tunamayo");
        setDescription("en_fan",
                "@E: Target 1 card under 1 of your SIGNI, and put it into the ener zone."
        );
        
		setName("zh_simplified", "食物代号 金枪鱼蛋黄酱");
        setDescription("zh_simplified", 
                "@E :你的精灵的下面的1张牌作为对象，将其放置到能量区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(1);
        setPower(2000);
        
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).own().withUnderType(CardUnderCategory.UNDER).fromLocation(CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT)).get();
            putInEner(target);
        }
    }
}
