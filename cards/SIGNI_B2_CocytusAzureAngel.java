package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_CocytusAzureAngel extends Card {
    
    public SIGNI_B2_CocytusAzureAngel()
    {
        setImageSets("WXDi-P01-065");
        
        setOriginalName("蒼天　コキュートス");
        setAltNames("ソウテンコキュートス Souten Kokyuutosu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それを凍結する。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );
        
        setName("en", "Cocytus, Azure Angel");
        setDescription("en",
                "@E: Freeze target SIGNI on your opponent's field." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Cocytus, Azure Angel");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and freeze it." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );
        
		setName("zh_simplified", "苍天 科塞特斯");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其冻结。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(8000);
        
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            freeze(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
