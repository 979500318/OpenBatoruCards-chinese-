package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B2_CodePalaceRyuujou extends Card {
    
    public SIGNI_B2_CodePalaceRyuujou()
    {
        setImageSets("WXDi-P07-075");
        
        setOriginalName("コードパレス　リュウジョウ");
        setAltNames("コードパレスリュウジョウ Koodo Paresu Ryuujou");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにこのシグニが手札以外の領域から場に出ていた場合、カードを１枚引くか、対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "Ryuujou, Code: Palace");
        setDescription("en",
                "@U: At the beginning of your attack phase, if this SIGNI entered the field from a Zone other than a player's hand this turn, draw a card or your opponent discards a card."
        );
        
        setName("en_fan", "Code Palace Ryuujou");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if this SIGNI entered the field from other than your hand this turn, draw 1 card or your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "迷殿代号 龙城");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这个回合这只精灵从手牌以外的领域出场的场合，抽1张牌或，对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event ->
                event.getId() == GameEventId.ENTER && event.getCallerCardIndex() == getCardIndex() && event.getCaller().getOldLocation() != CardLocation.HAND) > 0)
            {
                if(playerChoiceAction(ActionHint.DRAW, ActionHint.DISCARD) == 1)
                {
                    draw(1);
                } else {
                    discard(getOpponent(), 1);
                }
            }
        }
    }
}
