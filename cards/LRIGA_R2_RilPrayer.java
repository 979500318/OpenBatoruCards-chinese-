package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.AttackModifierFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AttackModifier;

public final class LRIGA_R2_RilPrayer extends Card {
    
    public LRIGA_R2_RilPrayer()
    {
        setImageSets("WXDi-P08-030");
        
        setOriginalName("リル・祈祷");
        setAltNames("リルキトウ Riru Kitou");
        setDescription("jp",
                "@E：対戦相手は自分のシグニを好きな数選ぶ。このターン、対戦相手は選んだシグニで可能ならばアタックしなければならず、それら以外のシグニでアタックできない。\n" +
                "@E %X %X %X：このターン、対戦相手のシグニ１体がアタックしたとき、そのアタックがこのターンで二度目以降の対戦相手によるアタックの場合、そのアタックしたシグニをバニッシュする。"
        );
        
        setName("en", "Ril, in Prayer");
        setDescription("en",
                "@E: Your opponent chooses any number of SIGNI on their field. Any SIGNI not chosen cannot attack this turn, and all SIGNI chosen must attack if able.\n" +
                "@E %X %X %X: Whenever a SIGNI on your opponent's field attacks this turn, if your opponent has already attacked once before, vanish it."
        );
        
        setName("en_fan", "Ril Prayer");
        setDescription("en_fan",
                "@E: Your opponent chooses any number of their SIGNI. This turn, your opponent must attack with the chosen SIGNI if able, and cannot attack with other SIGNI.\n" +
                "@E %X %X %X: This turn, whenever 1 of your opponent's SIGNI attacks this turn, if it was your opponent's second or later attack this turn, banish the attacking SIGNI."
        );
        
		setName("zh_simplified", "莉露·祈祷");
        setDescription("zh_simplified", 
                "@E :对战对手任意数量选自己的精灵。这个回合，对战对手选的精灵如果能攻击，则必须攻击，这些以外的精灵不能攻击。\n" +
                "@E %X %X %X:这个回合，当对战对手的精灵1只攻击时，那次攻击是这个回合第二次以后的对战对手的攻击的场合，那只精灵破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.RIL);
        setColor(CardColor.RED);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(3)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(getOpponent(), 0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.ABILITY).own().SIGNI());
            
            ConstantAbilityShared attachedConstShared1 = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().match(data), new AttackModifier(AttackModifierFlag.FORCE_ATTACK));
            ConstantAbilityShared attachedConstShared2 = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().except(data), new AttackModifier(AttackModifierFlag.CANT_ATTACK));
            
            attachPlayerAbility(getOwner(), attachedConstShared1, ChronoDuration.turnEnd());
            attachPlayerAbility(getOwner(), attachedConstShared2, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) &&
                   GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ATTACK && 
                       !isOwnCard(event.getCaller()) && CardType.isSIGNI(event.getCaller().getCardReference().getType())
                   ) >= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            banish(caller);
        }
    }
}
