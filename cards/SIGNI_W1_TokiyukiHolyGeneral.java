package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W1_TokiyukiHolyGeneral extends Card {
    
    public SIGNI_W1_TokiyukiHolyGeneral()
    {
        setImageSets("WXDi-P05-044", "SPDi01-74");
        
        setOriginalName("聖将　トキユキ");
        setAltNames("セイショウトキユキ Seishou Tokiyuki");
        setDescription("jp",
                "@C：対戦相手のターンの間、あなたのセンタールリグが白であるかぎり、このシグニのパワーは＋5000される。"
        );
        
        setName("en", "Tokiyuki, Blessed General");
        setDescription("en",
                "@C: During your opponent's turn, as long as your Center LRIG is white, this SIGNI gets +5000 power."
        );
        
        setName("en_fan", "Tokiyuki, Holy General");
        setDescription("en_fan",
                "@C: During your opponent's turn, as long as your center LRIG is white, this SIGNI gets +5000 power."
        );
        
		setName("zh_simplified", "圣将 北条时行");
        setDescription("zh_simplified", 
                "@C $TP :你的核心分身是白色时，这只精灵的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && getLRIG(getOwner()).getIndexedInstance().getColor().matches(CardColor.WHITE) ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
