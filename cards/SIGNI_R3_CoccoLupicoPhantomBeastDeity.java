package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.AbilityCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class SIGNI_R3_CoccoLupicoPhantomBeastDeity extends Card {
    
    public SIGNI_R3_CoccoLupicoPhantomBeastDeity()
    {
        setImageSets("WXDi-P03-039");
        
        setOriginalName("幻獣神　コッコ・ルピコ");
        setAltNames("ゲンジュウシンコッコルピコ Genjuushin Kokko Rupiko");
        setDescription("jp",
                "^U：このシグニが中央のシグニゾーンにあるかぎり、あなたのルリグにグロウするためのコストは%X減る。\n" +
                "@U $T1：あなたのターンの間、あなたのルリグがグロウしたとき、対戦相手のパワー8000以下のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Cocco Lupico, Phantom Beast Deity");
        setDescription("en",
                "@C: As long as this SIGNI is in your center SIGNI Zone, the cost to grow LRIG on your field is reduced by %X.\n" +
                "@U $T1: During your turn, when a LRIG on your field grows, you may pay %X. If you do, vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Cocco Lupico, Phantom Beast Deity");
        setDescription("en_fan",
                "@C: As long as this SIGNI is in your center SIGNI zone, the cost to grow your LRIGs is reduced by %X.\n" +
                "@U $T1: During your turn, whenever 1 of your LRIGs grows, target 1 of your opponent's SIGNI with power 8000 or less, and you may pay %X. If you do, banish it."
        );
        
		setName("zh_simplified", "幻兽神 咕咕·路比亚");
        setDescription("zh_simplified", 
                "@C :这只精灵在中央的精灵区时，你的分身的成长的费用减%X。\n" +
                "@U $T1 :你的回合期间，当你的分身成长时，对战对手的力量8000以下的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SKY_BEAST);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().anyLRIG().fromLocation(CardLocation.DECK_LRIG),
                new CostModifier(this::onConstEffModGetSample, ModifierMode.REDUCE)
            );
            
            AutoAbility auto = registerAutoAbility(GameEventId.GROW, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onConstEffCond()
        {
            return getCardIndex().getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
        private AbilityCost onConstEffModGetSample(CardIndex cardIndex)
        {
            return new EnerCost(Cost.colorless(1));
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
