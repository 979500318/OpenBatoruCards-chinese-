package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R1_GarashaCrimsonGeneral extends Card {
    
    public SIGNI_R1_GarashaCrimsonGeneral()
    {
        setImageSets("WXDi-P04-053");
        
        setOriginalName("紅将　ガラシャ");
        setAltNames("コウショウガラシャ Koushou Garasha");
        setDescription("jp",
                "@C：あなたのルリグトラッシュにカードがあるかぎり、このシグニのパワーは＋4000される。"
        );
        
        setName("en", "Gracia, Crimson General");
        setDescription("en",
                "@C: As long as you have a card in your LRIG Trash, this SIGNI gets +4000 power."
        );
        
        setName("en_fan", "Garasha, Crimson General");
        setDescription("en_fan",
                "@C: As long as there is a card in your LRIG trash, this SIGNI gets +4000 power."
        );
        
		setName("zh_simplified", "红将 伽罗奢");
        setDescription("zh_simplified", 
                "@C :你的分身废弃区有牌时，这只精灵的力量+4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
        }
        
        private ConditionState onConstEffCond()
        {
            return getTrashCount(getOwner(), DeckType.LRIG) > 0 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
