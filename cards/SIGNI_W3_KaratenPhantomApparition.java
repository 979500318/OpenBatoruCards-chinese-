package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W3_KaratenPhantomApparition extends Card {
    
    public SIGNI_W3_KaratenPhantomApparition()
    {
        setImageSets("WXDi-P07-058");
        
        setOriginalName("幻怪　カラテン");
        setAltNames("ゲンカイカラテン Genkai Karaten");
        setDescription("jp",
                "@U $T1：@E能力を持つあなたのシグニ１体が場に出たとき、【エナチャージ１】をする。\n" +
                "@A $T1 %W %X #C #C：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Karaten, Phantom Spirit");
        setDescription("en",
                "@U $T1: When a SIGNI with an @E ability enters your field, [[Ener Charge 1]]. \n" +
                "@A $T1 %W %X #C #C: Add target SIGNI with a #G from your trash to your hand."
        );
        
        setName("en_fan", "Karaten, Phantom Apparition");
        setDescription("en_fan",
                "@U $T1: When a SIGNI with @E ability enters the field, [[Ener Charge 1]].\n" +
                "@A $T1 %W %X #C #C: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "幻怪 鸦天狗");
        setDescription("zh_simplified", 
                "@U $T1 当持有@E能力的你的精灵1只出场时，[[能量填充1]]。（能在那个@E能力前把这个能力发动）\n" +
                "@A $T1 %W%X#C #C从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act = registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)),
                new CoinCost(2)
            ), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) &&
                   caller.getIndexedInstance().getAbilityList().stream().anyMatch((ability -> !ability.isDisabled() && ability instanceof EnterAbility)) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }
    }
}
