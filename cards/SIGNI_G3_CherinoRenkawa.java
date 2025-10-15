package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G3_CherinoRenkawa extends Card {

    public SIGNI_G3_CherinoRenkawa()
    {
        setImageSets("WX25-CP1-045");

        setOriginalName("連河チェリノ");
        setAltNames("レンカワチェリノ Renkawa Cherino");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、それのレベル１につきあなたのエナゾーンから＜ブルアカ＞のカード１枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。\n" +
                "@U：あなたのターン終了時、【エナチャージ１】をする。" +
                "~{{U：あなたのアタックフェイズ開始時、あなたの場に＜ブルアカ＞のシグニが３種類以上ある場合、【エナチャージ１】をする。"
        );

        setName("en", "Renkawa Cherino");

        setName("en_fan", "Cherino Renkawa");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may put 1 <<Blue Archive>> card for each of its levels from your ener zone into the trash. If you do, banish it.\n" +
                "@U: At the end of your turn, [[Ener Charge 1]]." +
                "~{{U: At the beginning of your attack phase, if you have 3 or more different <<Blue Archive>> SIGNI on your field, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "连河切里诺");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以依据其的等级的数量，每有1级就从你的能量区把<<ブルアカ>>牌1张放置到废弃区。这样做的场合，将其破坏。\n" +
                "@U :你的回合结束时，[[能量填充1]]。\n" +
                "~{{U:你的攻击阶段开始时，你的场上的<<ブルアカ>>精灵在3种类以上的场合，[[能量填充1]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            AutoAbility auto3 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff3);
            auto3.setCondition(this::onAutoEff1Cond);
            auto3.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0,target.getIndexedInstance().getLevel().getValue(), ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner());
                
                if(trash(data) > 0)
                {
                    banish(target);
                }
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            enerCharge(1);
        }
        
        private void onAutoEff3(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).getExportedData().stream().
                map(c -> ((CardIndex)c).getCardReference().getOriginalName()).distinct().count() >= 3)
            {
                enerCharge(1);
            }
        }
    }
}
