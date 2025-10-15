package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_B2_UtahaShiraishi extends Card {

    public SIGNI_B2_UtahaShiraishi()
    {
        setImageSets("WX25-CP1-066");
        setLinkedImageSets("WX25-CP1-TK1A");

        setOriginalName("白石ウタハ");
        setAltNames("シライシウタハ Shiraishi Utaha");
        setDescription("jp",
                "@A @[手札から＜ブルアカ＞のカードを１枚捨てる]@：あなたの場に《雷ちゃん》がない場合、クラフトの《雷ちゃん》１つを場に出す。\n" +
                "@A @[このシグニを場からトラッシュに置く]@：あなたの《雷ちゃん》１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋4000する。" +
                "~{{U：あなたのアタックフェイズ開始時、アップ状態のこのシグニをダウンしてもよい。そうした場合、カードを１枚引く。@@" +
                "~#対戦相手のルリグ１体を対象とし、それをダウンする。"
        );

        setName("en", "Shiraishi Utaha");

        setName("en_fan", "Utaha Shiraishi");
        setDescription("en",
                "@A @[Discard 1 <<Blue Archive>> card from your hand]@: If there is no \"Rai-chan\" on your field, put 1 \"Rai-chan\" craft onto the field.\n" +
                "@A @[Put this SIGNI from the field into the trash]@: Target 1 of your \"Rai-chan\", and until the end of your opponent's next turn, it gets +4000 power." +
                "~{{U: At the beginning of your attack phase, you may down this upped SIGNI. If you do, draw 1 card.@@" +
                "~#Target 1 of your opponent's LRIG, and down it."
        );

		setName("zh_simplified", "白石咏叶");
        setDescription("zh_simplified", 
                "@A 从手牌把<<ブルアカ>>牌1张舍弃:你的场上没有《雷ちゃん》的场合，衍生的《雷ちゃん》1只出场。\n" +
                "@A 这只精灵从场上放置到废弃区:你的《雷ちゃん》1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+4000。\n" +
                "~{{U你的攻击阶段开始时，可以把竖直状态的这只精灵#D。这样做的场合，抽1张牌。@@" +
                "~#对战对手的分身1只作为对象，将其#D。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            ActionAbility act1 = registerActionAbility(new DiscardCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);

            ActionAbility act2 = registerActionAbility(new TrashCost(), this::onActionEff2);
            act2.setCondition(this::onActionEff2Cond);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onActionEff1Cond()
        {
            return new TargetFilter().own().SIGNI().withName("雷ちゃん").getValidTargetsCount() == 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff1()
        {
            if(new TargetFilter().own().SIGNI().withName("雷ちゃん").getValidTargetsCount() == 0)
            {
                CardIndex cardIndex = craft(getLinkedImageSets().get(0));
                
                if(!putOnField(cardIndex))
                {
                    exclude(cardIndex);
                }
            }
        }
        
        private ConditionState onActionEff2Cond()
        {
            return new TargetFilter().own().SIGNI().withName("雷ちゃん").getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withName("雷ちゃん")).get();
            gainPower(target, 4000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down())
            {
                draw(1);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().anyLRIG()).get();
            down(target);
        }
    }
}
