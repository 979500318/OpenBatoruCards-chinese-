package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_G2_IshikumaDoujiVerdantDevil extends Card {

    public SIGNI_G2_IshikumaDoujiVerdantDevil()
    {
        setImageSets("WX24-P2-086");

        setOriginalName("翠魔　イシクマドウジ");
        setAltNames("スイマイシクマドウジ Suima Ishikuma Douji");
        setDescription("jp",
                "@U：あなたのメインフェイズ開始時、このシグニを場からトラッシュに置き%Xを支払ってもよい。そうした場合、対戦相手は自分のシグニ１体を選びエナゾーンに置く。\n" +
                "@A #D：次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Ishikuma-Douji, Verdant Devil");
        setDescription("en",
                "@U: At the beginning of your main phase, you may put this SIGNI from your field into the trash and pay %X. If you do, your opponent chooses 1 of their SIGNI, and puts it into the ener zone.\n" +
                "@A #D: Until the end of your opponent's next turn, this SIGNI gets +4000 power." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "翠魔 石熊童子");
        setDescription("zh_simplified", 
                "@U :你的主要阶段开始时，可以把这只精灵从场上放置到废弃区并支付%X。这样做的场合，对战对手选自己的精灵1只放置到能量区。\n" +
                "@A 横置:直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。" +
                "~#[[能量填充1]]。这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto1.setCondition(this::onAutoEffCond);

            registerActionAbility(new DownCost(), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(payAll(new TrashCost(), new EnerCost(Cost.colorless(1))))
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.ENER).own().SIGNI()).get();
                putInEner(cardIndex);
            }
        }
        
        private void onActionEff()
        {
            gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
