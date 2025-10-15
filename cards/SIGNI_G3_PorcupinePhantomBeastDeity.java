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
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityLancer;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class SIGNI_G3_PorcupinePhantomBeastDeity extends Card {

    public SIGNI_G3_PorcupinePhantomBeastDeity()
    {
        setImageSets("WX24-P2-055");

        setOriginalName("幻獣神　ヤマアラシ");
        setAltNames("ゲンジュウシンヤマアラシ Genjuushin Yamaarashi");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのシグニ１体を対象とし、ターン終了時まで、それは@>@U $T1：このシグニが【ランサー】か【Ｓランサー】によって対戦相手のライフクロス１枚をクラッシュしたとき、%G %G %Xを支払ってもよい。そうした場合、対戦相手のライフクロス１枚をクラッシュする。」を得る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Porcupine, Phantom Beast Deity");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your SIGNI, and until end of turn, it gains:" +
                "@>@U $T1: When this SIGNI crushes 1 of your opponent's life cloth by the effect of [[Lancer]] or [[S Lancer]], you may pay %G %G %X. If you do, crush 1 of your opponent's life cloth.@@" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "幻兽神 豪猪");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U $T1 :当这只精灵因为[[枪兵]]或[[S枪兵]]把对战对手的生命护甲1张击溃时，可以支付%G %G%X。这样做的场合，对战对手的生命护甲1张击溃。@@" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(3);
        setPower(12000);

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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.CRUSH, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getAbility().getSourceCardIndex() &&
                    getEvent().getSourceAbility() != null &&
                    (getEvent().getSourceAbility().getSourceStockAbility() instanceof StockAbilityLancer ||
                     getEvent().getSourceAbility().getSourceStockAbility() instanceof StockAbilitySLancer) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex source = getAbility().getSourceCardIndex();
            if(source.getIndexedInstance().payEner(Cost.color(CardColor.GREEN, 2) + Cost.colorless(1)))
            {
                source.getIndexedInstance().crush(getOpponent());
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
