package open.batoru.data.cards;

import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_W3_CodeMazeHimejiCastle extends Card {
    
    public SIGNI_W3_CodeMazeHimejiCastle()
    {
        setImageSets("WXDi-P03-053");
        
        setOriginalName("コードメイズ　ヒメジジョ");
        setAltNames("コードメイズヒメジジョ Koodo Meizu Himejijo");
        setDescription("jp",
                "@C：対戦相手のターンの間、対戦相手は、シグニの能力かシグニの効果で対象を選ぶ際、可能ならばこのシグニを対象とする。"
        );
        
        setName("en", "Himejijo, Code: Maze");
        setDescription("en",
                "@C: During your opponent's turn, as your opponent chooses targets for a SIGNI's ability or effect, they must target this SIGNI if possible."
        );
        
        setName("en_fan", "Code Maze Himeji Castle");
        setDescription("en_fan",
                "@C: During your opponent's turn, if your opponent would choose a target for a SIGNI's ability or a SIGNI's effect, they must target this SIGNI if able."
        );
        
		setName("zh_simplified", "迷宫代号 姫路城");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，对战对手的，精灵的能力或精灵的效果选对象时，如果能把这只精灵作为对象，则必须把这只精灵作为对象。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new RuleCheckModifier<>(CardRuleCheckType.MUST_BE_TARGETED, this::onConstEffModRuleCheck));
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getGenericData(0) != getOwner() && data.getSourceAbility() != null &&
                   CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) ? RuleCheckState.OK : RuleCheckState.IGNORE;
        }
    }
}
