package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R2_NunchaMediumEquipment extends Card {
    
    public SIGNI_R2_NunchaMediumEquipment()
    {
        setImageSets("WXDi-P02-059");
        
        setOriginalName("中装　ヌンチャ");
        setAltNames("チュウソウヌンチャ Chuusou Nuncha");
        setDescription("jp",
                "~#：対戦相手のエナゾーンからカード１枚を対象とし、それをトラッシュに置く。カードを１枚引く。"
        );
        
        setName("en", "Nuncha, High Armed");
        setDescription("en",
                "~#Put target card from your opponent's Ener Zone into their trash. Draw a card."
        );
        
        setName("en_fan", "Nuncha, Medium Equipment");
        setDescription("en_fan",
                "~#Target 1 card from your opponent's ener zone, and put it into the trash. Draw 1 card."
        );
        
		setName("zh_simplified", "中装 双节棍");
        setDescription("zh_simplified", 
                "~#从对战对手的能量区把1张牌作为对象，将其放置到废弃区。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner()).get();
            trash(target);
            
            draw(1);
        }
    }
}
