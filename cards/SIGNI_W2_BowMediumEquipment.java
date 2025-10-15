package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_W2_BowMediumEquipment extends Card {
    
    public SIGNI_W2_BowMediumEquipment()
    {
        setImageSets("WXDi-P01-050");
        
        setOriginalName("中装　ボウ");
        setAltNames("チュウソウボウ Chuusou Bou");
        setDescription("jp",
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Bow, High Armed");
        setDescription("en",
                "~#Return target SIGNI with power 8000 or less on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Bow, Medium Equipment");
        setDescription("en_fan",
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and return it to their hand."
        );
        
		setName("zh_simplified", "中装 芒");
        setDescription("zh_simplified", 
                "~#对战对手的力量8000以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(2);
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,8000)).get();
            addToHand(target);
        }
    }
}
