package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class LRIG_R3_HiranaOneStepTowardsTheTop extends Card {
    
    public LRIG_R3_HiranaOneStepTowardsTheTop()
    {
        setImageSets("WXDi-D03-004","WXDi-D09-H04", "SPDi07-01","SPDi08-01","SPDi09-01");
        
        setOriginalName("頂点へ一歩　ヒラナ");
        setAltNames("チョウテンヘイッポヒラナ Chouten he Ippo Hirana");
        setDescription("jp",
                "=T ＜Ｎｏ　Ｌｉｍｉｔ＞\n" +
                "^C：アタックしているあなたのシグニのパワーを＋2000する。\n" +
                "@E：カードを１枚引き、[[エナチャージ１]]をする。\n" +
                "@A $G1 %R0：ターン終了時まで、このルリグは@>@U：このルリグがアタックしたとき、あなたのアップ状態のレベル２のルリグ２体をダウンしてもよい。そうした場合、対戦相手が#Gを持つカードを１枚捨てないかぎり、対戦相手にダメージを与える。@@を得る。"
        );
        
        setName("en", "Hirana, a Step Towards the Top");
        setDescription("en",
                "=T <<No Limit>>\n" +
                "^C: Attacking SIGNI on your field get +2000 power.\n" +
                "@E: Draw a card and [[Ener Charge 1]].\n" +
                "@A $G1 %R0: This LRIG gains@>@U: Whenever this LRIG attacks, you may down two upped level two LRIG on your field. If you do, this LRIG deals damage to your opponent unless your opponent discards a card with a #G.@@until end of turn."
        );
        
        setName("en_fan", "Hirana, One Step Towards the Top");
        setDescription("en_fan",
                "=T <<No Limit>>\n" +
                "^C: Your attacking SIGNI get +2000 power.\n" +
                "@E: Draw 1 card, and [[Ener Charge 1]].\n" +
                "@A $G1 %R0: Until end of turn, this LRIG gains:" +
                "@>@U: Whenever this LRIG attacks, you may down 2 of your upped level 2 LRIGs. If you do, damage your opponent unless they discard 1 card with #G @[Guard]@."
        );
        
		setName("zh_simplified", "向顶点一步 平和");
        setDescription("zh_simplified", 
                "=T<<No:Limit>>\n" +
                "^C:攻击中的你的精灵的力量+2000。\n" +
                "@E :抽1张牌，[[能量填充1]]。\n" +
                "@A $G1 %R0:直到回合结束时为止，这只分身得到\n" +
                "@>@U 当这只分身攻击时，可以把你的竖直状态的等级2的分身2只#D。这样做的场合，如果对战对手不把持有#G的牌1张舍弃，那么给予对战对手伤害。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HIRANA);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
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
            
            registerConstantAbility(this::onConstEffCond, new TargetFilter().SIGNI().own(), new PowerModifier(2000));
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onConstEffCond(CardIndex cardIndex)
        {
            return isLRIGTeam(CardLRIGTeam.NO_LIMIT) && cardIndex.getIndexedInstance().isState(CardStateFlag.ATTACKING) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            draw(1);
            enerCharge(1);
        }
        
        private ConditionState onActionEffCond()
        {
            return new TargetFilter().own().own().anyLRIG().withLevel(2).upped().getValidTargetsCount() >= 2 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            AutoAbility attachedAbility = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachAbility(getCardIndex(), attachedAbility, ChronoDuration.turnEnd());
        }
        private void onAttachedAutoEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.DOWN).own().anyLRIG().withLevel(2).upped());
            if(data.size() == 2 && down(data) == 2)
            {
                CardIndex cardIndex = discard(getOpponent(), 0,1, new TargetFilter(TargetHint.DISCARD).OP().withState(CardStateFlag.CAN_GUARD)).get();
                if(cardIndex == null)
                {
                    damage(getOpponent());
                }
            }
        }
    }
}
