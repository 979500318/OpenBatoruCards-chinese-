package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_W3_Code2434MikotoRindou extends Card {
    
    public SIGNI_W3_Code2434MikotoRindou()
    {
        setImageSets("WXDi-P00-047");
        
        setOriginalName("コード２４３４　竜胆尊");
        setAltNames("コードニジサンジリンドウミコト Koodo Nijisanji Rindou Mikoto");
        setDescription("jp",
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );
        
        setName("en", "Mikoto Rindou, Code 2434");
        setDescription("en",
                "~#Put target upped SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "Code 2434 Mikoto Rindou");
        setDescription("en_fan",
                "~#Target 1 of your opponent's upped SIGNI, and put it into the trash."
        );
        
		setName("zh_simplified", "2434代号 龙胆尊");
        setDescription("zh_simplified", 
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(3);
        setPower(13000);
        
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
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(cardIndex);
        }
    }
}
