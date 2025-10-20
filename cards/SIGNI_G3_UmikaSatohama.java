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
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G3_UmikaSatohama extends Card {

    public SIGNI_G3_UmikaSatohama()
    {
        setImageSets("WX25-CP1-082");

        setOriginalName("里浜ウミカ");
        setAltNames("サトハマウミカ Satohama Umika");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、パワーがこのシグニのパワーの半分以下の対戦相手のシグニ１体を対象とし、あなたの他のアップ状態の＜ブルアカ＞のシグニ１体をダウンしてもよい。そうした場合、それをバニッシュする。\n" +
                "@A %X：ターン終了時まで、このシグニのパワーを＋5000する。" +
                "~{{C：アタックフェイズの間、このシグニのパワーは＋5000される。@@" +
                "~#：カードを１枚引き【エナチャージ２】をする。"
        );

        setName("en", "Satohama Umika");

        setName("en_fan", "Umika Satohama");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power equal to or less than half this SIGNI's power, and you may down 1 of your other upped <<Blue Archive>> SIGNI. If you do, banish it.\n" +
                "@A %X: Until end of turn, this SIGNI gets +5000 power." +
                "~{{C: During the attack phase, this SIGNI gets +5000 power.@@" +
                "~#Draw 1 card, and [[Ener Charge 2]]."
        );

		setName("zh_simplified", "里滨海夏");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，力量在这只精灵的力量的一半以下的对战对手的精灵1只作为对象，可以把你的其他的竖直状态的<<ブルアカ>>精灵1只横置。这样做的场合，将其破坏。\n" +
                "@A %X:直到回合结束时为止，这只精灵的力量+5000。\n" +
                "~{{C:攻击阶段期间，这只精灵的力量+5000。@@" +
                "~#抽1张牌并[[能量填充2]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff);

            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,getPower().getValue()/2)).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.DOWN).own().SIGNI().upped().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex())).get();
                
                if(down(cardIndex))
                {
                    banish(target);
                }
            }
        }
        
        private void onActionEff()
        {
            gainPower(getCardIndex(), 5000, ChronoDuration.turnEnd());
        }
        
        private ConditionState onConstEffCond()
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) ? ConditionState.OK : ConditionState.BAD;
        }

        private void onLifeBurstEff()
        {
            draw(1);
            enerCharge(2);
        }
    }
}
