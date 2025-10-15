package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.PowerCost;

public final class SIGNI_G3_DreiPatra extends Card {
    
    public SIGNI_G3_DreiPatra()
    {
        setImageSets("WXDi-P07-046");
        
        setOriginalName("ドライ＝パトラ");
        setAltNames("ドライパトラ Dorai Patora");
        setDescription("jp",
                "@E %G：あなたのエナゾーンからシグニ１枚を対象とし、それを手札に加える。\n" +
                "@E %K：あなたのトラッシュから緑のシグニ１枚を対象とし、それを場に出す。\n" +
                "@A $T1 %X @[ターン終了時まで、このシグニのパワーを－10000する]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。この能力はこのシグニのパワーが20000以上の場合にしか使用できない。"
        );
        
        setName("en", "Patra Type: Drei");
        setDescription("en",
                "@E %G: Add target SIGNI from your Ener Zone to your hand.\n" +
                "@E %K: Put target green SIGNI from your trash onto your field.\n" +
                "@A $T1 %X @[This SIGNI gets --10000 power until end of turn]@: Target SIGNI on your opponent's field gets --10000 power until end of turn. This ability can only be used if this SIGNI's power is 20000 or more."
        );
        
        setName("en_fan", "Drei-Patra");
        setDescription("en_fan",
                "@E %G: Target 1 SIGNI from your ener zone, and add it to your hand.\n" +
                "@E %K: Target 1 green SIGNI from your trash, and put it onto the field.\n" +
                "@A $T1 %X @[This SIGNI gets --10000 power until end of turn]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power. This ability can only be used if this SIGNI's power is 20000 or more."
        );
        
		setName("zh_simplified", "DREI＝帕特拉");
        setDescription("zh_simplified", 
                "@E %G:从你的能量区把精灵1张作为对象，将其加入手牌。\n" +
                "@E %K:从你的废弃区把绿色的精灵1张作为对象，将其出场。\n" +
                "@A $T1 %X直到回合结束时为止，这只精灵的力量-10000:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。这个能力只有在这只精灵的力量在20000以上的场合才能使用。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
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
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1)), this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff2);
            
            ActionAbility act = registerActionAbility(new AbilityCostList(new EnerCost(Cost.colorless(1)), new PowerCost(10000)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.GREEN).fromTrash().playable()).get();
            putOnField(target);
        }
        
        private ConditionState onActionEffCond()
        {
            return getPower().getValue() >= 20000 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
        }
    }
}
