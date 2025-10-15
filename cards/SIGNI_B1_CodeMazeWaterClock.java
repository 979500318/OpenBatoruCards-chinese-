package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_B1_CodeMazeWaterClock extends Card {
    
    public SIGNI_B1_CodeMazeWaterClock()
    {
        setImageSets("WXDi-P00-059");
        
        setOriginalName("コードメイズ　ミズドケイ");
        setAltNames("コードメイズミズドケイ Koodo Meizu Mizudokei");
        setDescription("jp",
                "@U $T1：あなたの効果によって場にあるこのシグニが他のシグニゾーンに移動したとき、対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "Clepsydra, Code:Maze");
        setDescription("en",
                "@U $T1: When this SIGNI on your field moves to a different zone by your effect, your opponent discards a card."
        );
        
        setName("en_fan", "Code Maze Water Clock");
        setDescription("en_fan",
                "@U $T1: When this SIGNI on the field is moved to another SIGNI zone by your effect, your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "迷宫代号 水时计");
        setDescription("zh_simplified", 
                "@U $T1 :当因为你的效果把场上的这只精灵往其他的精灵区移动时，对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSourceCardIndex()) && getEvent().getSourceCost() == null &&
                   CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            discard(getOpponent(), 1);
        }
    }
}
