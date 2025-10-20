package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.AttackModifierFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class PIECE_WRG_EndlessPunchline extends Card {
    
    public PIECE_WRG_EndlessPunchline()
    {
        setImageSets("WXDi-D04-011", "PR-Di005");
        
        setOriginalName("ENDLESS-PUNCHLINE");
        setAltNames("エンドレスパンチライン Endoresu Panchirain");
        setDescription("jp",
                "=U =T ＜Card Jockey＞＆全員レベル１以上\n\n" +
                "あなたのレベル３のルリグ１体を対象とし、ターン終了時まで、それは以下の能力を得る。" +
                "@>@C：このルリグはダウン状態でもアタックでき、１ターンにこのルリグがアタックできる上限は３になる。\n" +
                "@U $T1：このルリグがアタックしたとき、あなたのデッキをシャッフルし一番上を公開する。そのカードがレベル１のシグニの場合、このターン、このルリグがアタックできる上限を２減らす。そのカードがレベル２のシグニの場合、このターン、このルリグがアタックできる上限を１減らす。"
        );
        
        setName("en", "Endless Punchline");
        setDescription("en",
                "=U You have =T <<Card Jockey>> on your field with all members level one or more.\n\n" +
                "Target level three LRIG on your field gains the following abilities until end of turn." +
                "@>@C: This LRIG can attack even while downed and can attack up to three times in one turn.\n" +
                "@U $T1: When this LRIG attacks, shuffle your deck and reveal the top card of your deck. If that card is a level one SIGNI, the amount of times this LRIG can attack this turn is reduced by two. If that card is a level two SIGNI, the amount of times this LRIG can attack this turn is reduced by one."
        );
        
        setName("en_fan", "ENDLESS - PUNCHLINE");
        setDescription("en_fan",
                "=U =T <<Card Jockey>> and all of them are level 1 or higher\n\n" +
                "Target 1 of your level 3 LRIGs, and until end of turn, it gains:" +
                "@>@C: This LRIG can attack while downed, and the maximum number of times it can attack in 1 turn becomes 3.\n" +
                "@U $T1: When this LRIG attacks, shuffle your deck and reveal the top card of it. If that card is a level 1 SIGNI, this turn, decrease the maximum number of times this LRIG can attack by 2. If that card is a level 2 SIGNI, this turn, decrease the maximum number of times this LRIG can attack by 1."
        );
        
		setName("zh_simplified", "ENDLESS-PUNCHLINE");
        setDescription("zh_simplified", 
                "=U=T<<Card:Jockey>>＆全员等级1以上\n" +
                "你的等级3的分身1只作为对象，直到回合结束时为止，其得到以下的能力。\n" +
                "@>@C :这只分身在横置状态也能攻击，1回合中这只分身的攻击上限变为3次。\n" +
                "@U $T1 :当这只分身攻击时，你的牌组洗切把最上面公开。\n" +
                "那张牌是等级1的精灵的场合，这个回合，这只分身的攻击上限减2次。\n" +
                "那张牌是等级2的精灵的场合，这个回合，这只分身的攻击上限减1次。@@\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE, CardColor.RED, CardColor.GREEN);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            piece = registerPieceAbility(this::onPieceEffPreTarget, this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            if(new TargetFilter().own().anyLRIG().withLevel(1,0).getValidTargetsCount() != 3) return ConditionState.BAD;
            
            return new TargetFilter().own().LRIG().withLevel(3).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        int numMaxAvailableAttacks;
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().LRIG().withLevel(3)));
        }
        private void onPieceEff()
        {
            if(piece.getTarget() != null)
            {
                numMaxAvailableAttacks = 3;
                
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_ATTACK, this::onAttachedConstEffModRuleCheck));
                attachAbility(piece.getTarget(), attachedConst, ChronoDuration.turnEnd());
                
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                attachedAuto.setNestedDescriptionOffset(1);
                
                attachAbility(piece.getTarget(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(CardRuleCheckData data)
        {
            return data.getCardIndex().getIndexedInstance().hasAttackModifier(AttackModifierFlag.CANT_ATTACK) ||
                   GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ATTACK && event.getCallerCardIndex() == data.getCardIndex()) >= numMaxAvailableAttacks ?
                        RuleCheckState.BLOCK : RuleCheckState.OK;
        }
        private void onAttachedAutoEff()
        {
            shuffleDeck();
            
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                int level = cardIndex.getIndexedInstance().getLevelByRef();
                returnToDeck(cardIndex, DeckPosition.TOP);
                
                if(level == 1) numMaxAvailableAttacks -= 2;
                else if(level == 2) numMaxAvailableAttacks -= 1;
            }
        }
    }
}
