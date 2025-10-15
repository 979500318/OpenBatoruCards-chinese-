package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_ColtNaturalStar extends Card {
    
    public SIGNI_G2_ColtNaturalStar()
    {
        setImageSets("WXDi-P07-082");
        
        setOriginalName("羅星　コルト");
        setAltNames("ラセイコルト Rasei Koruto");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのトラッシュから#Gを持たないレベル１のシグニ１枚を対象とし、それをエナゾーンに置く。"
        );
        
        setName("en", "Colt, Natural Planet");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, put target level one SIGNI without a #G from your trash into your Ener Zone."
        );
        
        setName("en_fan", "Colt, Natural Star");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, target 1 level 1 SIGNI without #G @[Guard]@ from your trash, and put it into the ener zone."
        );
        
		setName("zh_simplified", "罗星 小马座");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，从你的废弃区把不持有#G的等级1的精灵1张作为对象，将其放置到能量区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(5000);
        
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).own().SIGNI().withLevel(1).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            putInEner(target);
        }
    }
}
