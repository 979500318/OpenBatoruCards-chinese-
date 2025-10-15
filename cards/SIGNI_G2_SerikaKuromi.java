package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G2_SerikaKuromi extends Card {

    public SIGNI_G2_SerikaKuromi()
    {
        setImageSets("WXDi-CP02-089");

        setOriginalName("黒見セリカ");
        setAltNames("クロミセリカ Kuromi Serika");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのエナゾーンから＜ブルアカ＞のカード１枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、このシグニは@>@C：このシグニは正面のシグニのパワーが5000以下であるかぎり、【ランサー】を得る。@@を得る。" +
                "~{{U $T1：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、【エナチャージ１】をする。"
        );

        setName("en", "Kuromi Serika");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may put a <<Blue Archive>> card from your Ener Zone into your trash. If you do, this SIGNI gains@>@C: As long as the SIGNI in front of this SIGNI has power 5000 or less, this SIGNI gains [[Lancer]].@@until end of turn.~{{U $T1: When this SIGNI crushes one of your opponent's Life Cloth, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Serika Kuromi");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, you may put 1 <<Blue Archive>> card from your ener zone into the trash. If you do, until end of turn, this SIGNI gains:" +
                "@>@C: As long as the SIGNI in front of this SIGNI has power 5000 or less, this SIGNI gains [[Lancer]].@@" +
                "~{{U $T1: When this SIGNI crushes 1 of your opponent's life cloth, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "黑见芹香");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以从你的能量区把<<ブルアカ>>牌1张放置到废弃区。这样做的场合，直到回合结束时为止，这只精灵得到\n" +
                "@>@C :这只精灵的正面的精灵的力量在5000以下时，得到[[枪兵]]。@@\n" +
                "~{{U$T1 :当这只精灵把对战对手的生命护甲1张击溃时，[[能量填充1]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.CRUSH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()).get();

            if(trash(cardIndex))
            {
                ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
                attachedConst.setCondition(this::onAttachedConstEffCond);
                
                attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedConstEffCond()
        {
            return getOppositeSIGNI() != null && getOppositeSIGNI().getIndexedInstance().getPower().getValue() <= 5000 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityLancer());
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}
