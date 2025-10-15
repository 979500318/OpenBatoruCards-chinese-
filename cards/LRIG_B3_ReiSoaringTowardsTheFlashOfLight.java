package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class LRIG_B3_ReiSoaringTowardsTheFlashOfLight extends Card {
    
    public LRIG_B3_ReiSoaringTowardsTheFlashOfLight()
    {
        setImageSets("WXDi-P06-007", "WXDi-P06-007U");
        
        setOriginalName("閃光へ飛翔　レイ");
        setAltNames("センコウヘヒショウレイ Senkou he Hishou Rei");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたが効果によってカードを２枚以上引いていた場合、青のシグニ１体を対象とし、手札を３枚捨ててもよい。そうした場合、ターン終了時まで、それは【アサシン】を得る。\n" +
                "@E：カードを１枚引き【エナチャージ１】をする。\n" +
                "@A $G1 %B0：ターン終了時まで、このルリグは@>@U $T2：対戦相手のライフクロス１枚がクラッシュされたとき、カードを１枚引くか、対戦相手は手札を１枚捨てる。@@を得る。"
        );
        
        setName("en", "Rei, On the Wings of Radiance");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have drawn two or more cards by effects this turn, you may discard three cards. If you do, target blue SIGNI gains [[Assassin]] until end of turn.\n" +
                "@E: Draw a card and [[Ener Charge 1]].\n" +
                "@A $G1 %B0: This LRIG gains@>@U $T2: Whenever one of your opponent's Life Cloth is crushed, draw a card or your opponent discards a card.@@until end of turn."
        );
        
        setName("en_fan", "Rei, Soaring Towards the Flash of Light");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if you drew 2 or more cards with your effects this turn, target 1 of your blue SIGNI, and you may discard 3 cards from your hand. If you do, until end of turn, it gains [[Assassin]].\n" +
                "@E: Draw 1 card and [[Ener Charge 1]].\n" +
                "@A $G1 %B0: Until end of turn, this LRIG gains:" +
                "@>@U $T2: Whenever 1 of your opponent's life cloth is crushed, draw 1 card or your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "向闪光飞翔 令");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这个回合你因为效果抽牌2张以上的场合，蓝色的精灵1只作为对象，可以把手牌3张舍弃。这样做的场合，直到回合结束时为止，其得到[[暗杀]]。\n" +
                "@E :抽1张牌并[[能量填充1]]。\n" +
                "@A $G1 %B0:直到回合结束时为止，这只分身得到\n" +
                "@>@U $T2 :当对战对手的生命护甲1张被击溃时，抽1张牌或，对战对手把手牌1张舍弃。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REI);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
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
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.DRAW && event.getSourceAbility() != null && isOwnCard(event.getSource())) >= 2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.BLUE)).get();
                
                if(target != null && discard(0,3, ChoiceLogic.BOOLEAN).size() == 3)
                {
                    attachAbility(target, new StockAbilityAssassin(), ChronoDuration.turnEnd());
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
            AutoAbility attachedAuto = new AutoAbility(GameEventId.CRUSH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.setUseLimit(UseLimit.TURN, 2);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
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
