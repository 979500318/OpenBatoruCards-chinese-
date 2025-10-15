package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G2_MistletoeNaturalPlant extends Card {

    public SIGNI_G2_MistletoeNaturalPlant()
    {
        setImageSets("WX24-P2-085");

        setOriginalName("羅植　ヤドリギ");
        setAltNames("ラショクヤドリギ Rashoku Yadorigi");
        setDescription("jp",
                "@C：あなたのエナゾーンに＜植物＞のシグニがあるかぎり、このシグニのパワーは＋3000される。\n" +
                "@U：あなたのメインフェイズ開始時、あなたのエナゾーンからレベル３の＜植物＞のシグニを１枚まで対象とし、それと場にあるこのシグニの場所を入れ替える。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Mistletoe, Natural Plant");
        setDescription("en",
                "@C: As long as there is a <<Plant>> SIGNI in your ener zone, this SIGNI gets +3000 power.\n" +
                "@U: At the beginning of your main phase, target up to 1 level 3 <<Plant>> SIGNI from your ener zone, and exchange its position with this SIGNI." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and you may pay %X. If you do, banish it."
        );

		setName("zh_simplified", "罗植 槲寄生");
        setDescription("zh_simplified", 
                "@C :你的能量区有<<植物>>精灵时，这只精灵的力量+3000。\n" +
                "@U :你的主要阶段开始时，从你的能量区把等级3的<<植物>>精灵1张最多作为对象，将其与场上的这只精灵的场所交换。" +
                "~#对战对手的力量7000以上的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(2);
        setPower(7000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PLANT).fromEner().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(3).withClass(CardSIGNIClass.PLANT).fromEner().playableAs(getCardIndex())).get();
            
            if(target != null)
            {
                putInEner(getCardIndex());
                putOnField(target, getCardIndex().getPreTransientLocation());
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
