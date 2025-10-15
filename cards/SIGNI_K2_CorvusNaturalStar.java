package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K2_CorvusNaturalStar extends Card {
    
    public SIGNI_K2_CorvusNaturalStar()
    {
        setImageSets("WXDi-P01-086");
        
        setOriginalName("羅星　コルバス");
        setAltNames("ラセイコルバス Rasei Korubasu");
        setDescription("jp",
                "~#：対戦相手は自分のシグニ１体を選びトラッシュに置く。"
        );
        
        setName("en", "Corvus, Natural Planet");
        setDescription("en",
                "~#Your opponent chooses a SIGNI on their side of the field and puts it into its owner's trash."
        );
        
        setName("en_fan", "Corvus, Natural Star");
        setDescription("en_fan",
                "~#Your opponent chooses 1 of their SIGNI, and puts it into the trash."
        );
        
		setName("zh_simplified", "罗星 乌鸦座");
        setDescription("zh_simplified", 
                "~#对战对手选自己的精灵1只放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
            CardIndex target = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.TRASH).own().SIGNI()).get();
            trash(target);
        }
    }
}
