package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_G1_EinGreenLucbor extends Card {
    
    public SIGNI_G1_EinGreenLucbor()
    {
        setImageSets("WXDi-P07-079");
        
        setOriginalName("アイン＝グリーンルクボル");
        setAltNames("アイングリーンルクボル Ain Guriin Rukuboru");
        setDescription("jp",
                "@A @[このシグニを場からトラッシュに置く]@：あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000する。それが＜毒牙＞のシグニの場合、代わりにターン終了時まで、それのパワーを＋10000する。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );
        
        setName("en", "Green Lucbor Type: Ein");
        setDescription("en",
                "@A @[Put this SIGNI on your field into its owner's trash]@: Target SIGNI on your field gets +5000 power until end of turn. If it is a <<Venom>> SIGNI, it gets +10000 power until end of turn instead." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Ein-Green Lucbor");
        setDescription("en_fan",
                "@A @[Put this SIGNI from the field into the trash]@: Target 1 of your SIGNI, and until end of turn, it gets +5000 power. If it is a <<Venom Fang>> SIGNI, instead, until end of turn, it gets +10000 power." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );
        
		setName("zh_simplified", "EINS=翠绿琉克博尔");
        setDescription("zh_simplified", 
                "@A 这只精灵从场上放置到废弃区:你的精灵1只作为对象，直到回合结束时为止，其的力量+5000。其是<<毒牙>>精灵的场合，作为替代，直到回合结束时为止，其的力量+10000。" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act = registerActionAbility(new TrashCost(), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onActionEffCond()
        {
            return getSIGNICount(getOwner()) > 1 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            if(target != null) gainPower(target, !target.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.VENOM_FANG) ? 5000 : 10000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
