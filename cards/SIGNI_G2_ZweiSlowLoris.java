package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G2_ZweiSlowLoris extends Card {
    
    public SIGNI_G2_ZweiSlowLoris()
    {
        setImageSets("WXDi-D01-014");
        
        setOriginalName("ツヴァイ＝スローロリス");
        setAltNames("ツヴァイスローロリス Tsuvai Suroo Rorisu");
        setDescription("jp",
                "@C：あなたのエナゾーンにあるシグニが持つクラスが合計３種類以上あるかぎり、このシグニのパワーは＋５０００される。\n" +
                "@A $T1 %G %G %X：あなたの他のパワー１５０００以上のシグニ１体を対象とし、ターン終了時まで、それは[[ランサー]]を得る。"
        );
        
        setName("en", "Zwei =Slow Loris=");
        setDescription("en",
                "@C: As long as there are three or more different classes among SIGNI in your Ener Zone, this SIGNI gets +5000 power.\n" +
                "@A $T1 %G %G %X: Another target SIGNI on your field with power 15000 or more gains [[Lancer]] until end of turn."
        );
        
        setName("en_fan", "Zwei-Slow Loris");
        setDescription("en_fan",
                "@C: As long as there are 3 or more different classes among SIGNI in your ener zone, this SIGNI gets +5000 power.\n" +
                "@A $T1 %G %G %X: Target 1 of your other SIGNI with power 15000 or more, and until end of turn, that SIGNI gains [[Lancer]]."
        );
        
		setName("zh_simplified", "ZWEI=间蜂猴");
        setDescription("zh_simplified", 
                "@C :你的能量区的精灵持有的类别合计3种类以上时，这只精灵的力量+5000。\n" +
                "@A $T1 %G %G%X:你的其他的力量15000以上的精灵1只作为对象，直到回合结束时为止，其得到[[枪兵]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(2);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 2) + Cost.colorless(1)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onConstEffCond()
        {
            return CardAbilities.getSIGNIClasses(getCardsInEner(getOwner())).size() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onActionEffCond()
        {
            return new TargetFilter().own().SIGNI().withPower(15000,0).except(getCardIndex()).
                    not(new TargetFilter().withStockAbility(StockAbilityLancer.class)).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withPower(15000,0).except(getCardIndex())).get();
            if(target != null) attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
        }
    }
}
