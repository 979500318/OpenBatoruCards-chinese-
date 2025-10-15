package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class LRIG_W3_AkinoAdvancingTowardsTomorrow extends Card {
    
    public LRIG_W3_AkinoAdvancingTowardsTomorrow()
    {
        setImageSets("WXDi-P04-007");
        
        setOriginalName("明日へ前進　アキノ");
        setAltNames("アスヘゼンシンアキノ Asu he Zenshin Akino");
        setDescription("jp",
                "@U：あなたのメインフェイズ開始時、対戦相手のシグニ１体を対象とし、あなたの白のシグニ１体を場からトラッシュに置き%Wを支払ってもよい。そうした場合、それを手札に戻す。\n" +
                "@E：カードを１枚引き【エナチャージ１】をする。\n" +
                "@A $G1 %W0：次の対戦相手のターンの、メインフェイズとアタックフェイズの間、あなたのシグニは【シャドウ】を得る。"
        );
        
        setName("en", "Akino, Bound for Tomorrow");
        setDescription("en",
                "@U: At the beginning of your main phase, you may put a white SIGNI on your field into their owner's trash and pay %W. If you do, return target SIGNI on your opponent's field to its owner's hand.\n" +
                "@E: Draw a card and [[Ener Charge 1]].\n" +
                "@A $G1 %W0: During the main phase and attack phase of your opponent's next turn, SIGNI on your field gain [[Shadow]]. "
        );
        
        setName("en_fan", "Akino, Advancing Towards Tomorrow");
        setDescription("en_fan",
                "@U: At the beginning of your main phase, target 1 of your opponent's SIGNI, and you may put 1 white SIGNI from your field into the trash, and pay %W. If you do, return it to their hand.\n" +
                "@E: Draw 1 card and [[Ener Charge 1]].\n" +
                "@A $G1 %W0: During the main phase and attack phase of your opponent's next turn, your SIGNI gain [[Shadow]]."
        );
        
		setName("zh_simplified", "向明日前进 昭乃");
        setDescription("zh_simplified", 
                "@U :你的主要阶段开始时，对战对手的精灵1只作为对象，可以把你的白色的精灵1只从场上放置到废弃区并支付%W。这样做的场合，将其返回手牌。\n" +
                "@E :抽1张牌并[[能量填充1]]。\n" +
                "@A $G1 %W0:下一个对战对手的回合的，主要阶段和攻击阶段期间，你的精灵得到[[暗影]]。（这个能力的使用后出场的精灵也给予影响）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AKINO);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);
        
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
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withColor(CardColor.WHITE)).get();
                
                if(cardIndex != null && payEner(Cost.color(CardColor.WHITE, 1)) && trash(cardIndex))
                {
                    addToHand(target);
                }
            }
        }
        
        private void onEnterEff()
        {
            draw(1);
            enerCharge(1);
        }
        
        private void onActionEff()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().SIGNI(), new AbilityGainModifier(this::onAttachedConstEffSharedModGetSample));
            attachedConst.setCondition(this::onAttachedConstEffSharedCond);
            
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.nextTurn(getOwner()));
        }
        private ConditionState onAttachedConstEffSharedCond()
        {
            return !isOwnTurn() && (getCurrentPhase() == GamePhase.MAIN || GamePhase.isAttackPhase(getCurrentPhase())) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffSharedModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow());
        }
    }
}
