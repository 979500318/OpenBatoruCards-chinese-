package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.ModifiableAddedValueModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G3_AminoAcidNaturalBacteriaPrincess extends Card {
    
    public SIGNI_G3_AminoAcidNaturalBacteriaPrincess()
    {
        setImageSets("WXDi-P00-039");
        
        setOriginalName("羅菌姫　アミノサン");
        setAltNames("ラキンヒメアミノサン Rakinhime Aminosan");
        setDescription("jp",
                "@C：このシグニのパワーが対戦相手の効果によって－（マイナス）される場合、代わりに＋（プラス）される。\n" +
                "@C：あなたのターンの間、あなたの他のシグニのパワーを＋5000する。" +
                "~#：[[エナチャージ１]]をする。このターン、次にあなたがシグニからダメージを受ける場合、代わりにダメージを受けない。"
        );
        
        setName("en", "Amino Acid, Natural Bacteria Queen");
        setDescription("en",
                "@C: If this SIGNI would get -- (minus) power by an opponent's effect, it gets + (plus) that amount instead.\n" +
                "@C: During your turn, other SIGNI on your field get +5000 power." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Amino Acid, Natural Bacteria Princess");
        setDescription("en_fan",
                "@C: If the power of this SIGNI would be -- (minus) by your opponent's effect, it gets + (plus) instead.\n" +
                "@C: During your turn, all of your other SIGNI get +5000 power." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );
        
		setName("zh_simplified", "罗菌姬 氨基酸");
        setDescription("zh_simplified", 
                "@C :这只精灵的力量因为对战对手的效果-（减号）的场合，作为替代，+（加号）。\n" +
                "@C $TO :你的其他的精灵的力量+5000。" +
                "~#[[能量填充1]]。这个回合，下一次你从精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new ModifiableAddedValueModifier<>(this::onConstEffModGetSample, this::onConstEffModAddedValue));
            
            registerConstantAbility(this::onConstEff2SharedCond, new TargetFilter().own().SIGNI(), new PowerModifier(5000));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ModifiableDouble onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getPower();
        }
        private double onConstEffModAddedValue(ModifiableDouble mod, double addedValue)
        {
            return addedValue < 0 && !isOwnCard(mod.getSourceAbility().getSourceCardIndex()) ? -addedValue : addedValue;
        }
        
        private ConditionState onConstEff2SharedCond(CardIndex cardIndex)
        {
            return isOwnTurn() && cardIndex != getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
