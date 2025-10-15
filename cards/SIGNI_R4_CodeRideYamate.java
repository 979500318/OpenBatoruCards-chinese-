package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R4_CodeRideYamate extends Card {

    public SIGNI_R4_CodeRideYamate()
    {
        setImageSets("WDK01-011");

        setOriginalName("コードライド　ヤマテ");
        setAltNames("コードライドヤマテ Koodo Raido Yamate");
        setDescription("jp",
                "\\C：このシグニのパワーは＋3000され、このシグニは@>@U：このシグニがアタックしたとき、自身のパワー以下の対戦相手のシグニ１体を対象とし、それをバニッシュする。@@を得る。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Code Ride Yamate");
        setDescription("en",
                "\\C: This SIGNI gets +3000 power, and it gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power equal to or less than this SIGNI's, and banish it.@@" +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and banish it."
        );

		setName("zh_simplified", "骑乘代号 山手线");
        setDescription("zh_simplified", 
                "[驾驶]@C :这只精灵的力量+3000，这只精灵得到\n" +
                "@>@U :当这只精灵攻击时，自己的力量以下的对战对手的精灵1只作为对象，将其破坏。@@" +
                "~#对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(4);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000), new AbilityGainModifier(this::onConstEffModGetSample));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return isState(CardStateFlag.IN_DRIVE) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,getAbility().getSourceCardIndex().getIndexedInstance().getPower().getValue())).get();
            getAbility().getSourceCardIndex().getIndexedInstance().banish(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
    }
}
