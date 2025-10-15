package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B2_CodeMazePrimora extends Card {
    
    public SIGNI_B2_CodeMazePrimora()
    {
        setImageSets("WXDi-P03-068");
        
        setOriginalName("コードメイズ　プリモーラ");
        setAltNames("コードメイズプリモーラ Koodo Meizu Purimoora");
        setDescription("jp",
                "@U：対戦相手のターン終了時、対戦相手のシグニ１体を対象とし、それを凍結する。\n" +
                "@U：このシグニがバニッシュされたとき、対戦相手のシグニ１体を対象とし、それをダウンし凍結する。"
        );
        
        setName("en", "Primora, Code: Maze");
        setDescription("en",
                "@U: At the end of your opponent's turn, freeze target SIGNI on your opponent's field.\n" +
                "@U: When this SIGNI is vanished, down target SIGNI on your opponent's field and freeze it."
        );
        
        setName("en_fan", "Code Maze Primora");
        setDescription("en_fan",
                "@U: At the end of your opponent's turn, target 1 of your opponent's SIGNI, and freeze it.\n" +
                "@U: When this SIGNI is banished, target 1 of your opponent's SIGNI, and down and freeze it."
        );
        
		setName("zh_simplified", "迷宫代号 沿海州水族馆秀");
        setDescription("zh_simplified", 
                "@U :对战对手的回合结束时，对战对手的精灵1只作为对象，将其冻结。\n" +
                "@U 当这只精灵被破坏时，对战对手的精灵1只作为对象，将其#D并冻结。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            freeze(target);
        }
        
        private void onAutoEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
        }
    }
}
