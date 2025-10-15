package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_R3_RilMemoryOfWarDancing extends Card {
    
    public LRIG_R3_RilMemoryOfWarDancing()
    {
        setImageSets("WXDi-P07-008", "WXDi-P07-008U");
        
        setOriginalName("武踊の記憶　リル");
        setAltNames("ブヨウノキオクリル Buyou no Kioku Riru");
        setDescription("jp",
                "@U $T1：&Rを持つあなたのシグニ１体が場に出たとき、手札を１枚捨ててもよい。そうした場合、カードを１枚引く。\n" +
                "@A $T1 %R0：あなたの場にいるアシストルリグのレベルの合計が１以上の場合、#Cを得る。４以上の場合、追加で#Cを得る。\n" +
                "@A $G1 %R0：対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Ril, Memory of Martial Dancing");
        setDescription("en",
                "@U $T1: When a SIGNI with a &R enters your field, you may discard a card. If you do, draw a card.\n" +
                "@A $T1 %R0: If the total level of Assist LRIG on your field is one or more, gain #C. If the total level of Assist LRIG on your field is four or more, gain an additional #C.\n" +
                "@A $G1 %R0: Vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Ril, Memory of War Dancing");
        setDescription("en_fan",
                "@U $T1: Whenever a &R SIGNI enters your field, you may discard 1 card from your hand. If you do, draw 1 card.\n" +
                "@A $T1 %R0: If the total level of all assist LRIGs on your field is 1 or more, gain #C. If it is 4 or more, additionally gain #C.\n" +
                "@A $G1 %R0: Target 1 of your opponent's SIGNI, and banish it."
        );
        
		setName("zh_simplified", "武踊的记忆 莉露");
        setDescription("zh_simplified", 
                "@U $T1 :当持有[升阶]的你的精灵1只出场时，可以把手牌1张舍弃。这样做的场合，抽1张牌。\n" +
                "@A $T1 %R0:你的场上的支援分身的等级的合计在1以上的场合，得到#C。4以上的场合，追加得到#C。\n" +
                "@A $G1 %R0:对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.RIL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);
        setCoins(+4);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().getUseCondition() == UseCondition.RISE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                draw(1);
            }
        }
        
        private void onActionEff1()
        {
            int sum = 0;
            if(getLRIGAssistLeft(getOwner()) != null) sum += getLRIGAssistLeft(getOwner()).getIndexedInstance().getLevel().getValue();
            if(getLRIGAssistRight(getOwner()) != null) sum += getLRIGAssistRight(getOwner()).getIndexedInstance().getLevel().getValue();
            
            if(sum >= 1) gainCoins(1);
            if(sum >= 4) gainCoins(1);
        }
        
        private void onActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
    }
}
