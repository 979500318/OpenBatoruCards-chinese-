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
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.MillCost;

public final class SIGNI_K3_MakotoHanuma extends Card {

    public SIGNI_K3_MakotoHanuma()
    {
        setImageSets("WX25-CP1-049");

        setOriginalName("羽沼マコト");
        setAltNames("ハヌママコト Hanuma Makoto");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのエナゾーンから＜ブルアカ＞のカード２枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、すべてのシグニのパワーを場にあるシグニ１体につき－1000する。\n" +
                "@A $T1 @[デッキの上からカードを３枚トラッシュに置く]@：ターン終了時まで、あなたの他のすべての＜ブルアカ＞のシグニのパワーを＋3000する。" +
                "~{{E %K：あなたのトラッシュからレベル２以下の黒のシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Hanuma Makoto");

        setName("en_fan", "Makoto Hanuma");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may put 2 <<Blue Archive>> cards from your ener zone into the trash. If you do, until end of turn, all SIGNI on the field get --1000 power for each SIGNI on the field.\n" +
                "@A $T1 @[Put the top 3 cards of your deck into the trash]@: Until end of turn, all of your other <<Blue Archive>> SIGNI get +3000 power." +
                "~{{E %K: Target 1 level 2 or lower black SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "羽沼真琴");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以从你的能量区把<<ブルアカ>>牌2张放置到废弃区。这样做的场合，直到回合结束时为止，全部的精灵的力量依据场上的精灵的数量，每有1只就-1000。（互相的精灵）\n" +
                "@A $T1 从牌组上面把3张牌放置到废弃区:直到回合结束时为止，你的其他的全部的<<ブルアカ>>精灵的力量+3000。\n" +
                "~{{E%K:从你的废弃区把等级2以下的黑色的精灵1张作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
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

            ActionAbility act = registerActionAbility(new MillCost(3), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);

            EnterAbility enter = registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
            enter.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner());
            
            if(trash(data) > 0)
            {
                gainPower(new TargetFilter().SIGNI().getExportedData(), -1000 * (getSIGNICount(getOwner()) + getSIGNICount(getOpponent())), ChronoDuration.turnEnd());
            }
        }
        
        private void onActionEff()
        {
            gainPower(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getExportedData(), 3000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).withLevel(0,2).fromTrash()).get();
            addToHand(target);
        }
    }
}
