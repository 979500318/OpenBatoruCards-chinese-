package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_RosaryNaturalWarPlant extends Card {

    public SIGNI_G1_RosaryNaturalWarPlant()
    {
        setImageSets("WX24-P2-082");

        setOriginalName("羅闘植　ローザリ");
        setAltNames("ラトウショクローザリ Ratoushoku Roozari");
        setDescription("jp",
                "@C：あなたのエナゾーンに＜植物＞のシグニがあるかぎり、このシグニのパワーは＋3000される。\n" +
                "@U：あなたのメインフェイズ開始時、あなたのエナゾーンからレベル２の＜植物＞のシグニを１枚まで対象とし、それと場にあるこのシグニの場所を入れ替える。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Rosary, Natural War Plant");
        setDescription("en",
                "@C: As long as there is a <<Plant>> SIGNI in your ener zone, this SIGNI gets +3000 power.\n" +
                "@U: At the beginning of your main phase, target up to 1 level 2 <<Plant>> SIGNI from your ener zone, and exchange its position with this SIGNI." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "罗斗植 玫瑰");
        setDescription("zh_simplified", 
                "@C :你的能量区有<<植物>>精灵时，这只精灵的力量+3000。\n" +
                "@U :你的主要阶段开始时，从你的能量区把等级2的<<植物>>精灵1张最多作为对象，将其与场上的这只精灵的场所交换。" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(1);
        setPower(2000);

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
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(2).withClass(CardSIGNIClass.PLANT).fromEner().playableAs(getCardIndex())).get();
            
            if(target != null)
            {
                putInEner(getCardIndex());
                putOnField(target, getCardIndex().getPreTransientLocation());
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
