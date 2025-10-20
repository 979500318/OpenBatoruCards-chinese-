package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class KEY_B_CodePirulukKEY extends Card {

    public KEY_B_CodePirulukKEY()
    {
        setImageSets("WDK02-009", "SPK02-01");

        setOriginalName("コード・ピルルク　ＫＥＹ");
        setAltNames("コードピルルクキー Koodo Piruruku Kii");
        setDescription("jp",
                "@C：あなたのレベル２以上のセンタールリグは以下の能力を得る。\n" +
                "@>@A $T1 @[エクシード１]@：カードを１枚引く。対戦相手は手札を１枚捨てる。\n" +
                "@A $T1 -A @[エクシード２]@：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。"
        );

        setName("en", "Code Piruluk KEY");
        setDescription("en",
                "@C: Your level 2 or higher center LRIG gains:" +
                "@>@A $T1 @[Exceed 1]@: Draw 1 card. Your opponent discards 1 card from their hand.\n" +
                "@A $T1 -A @[Exceed 2]@: Target 1 of your opponent's SIGNI, and down and freeze it."
        );

		setName("zh_simplified", "代号·皮璐璐可 KEY");
        setDescription("zh_simplified", 
                "@C :你的等级2以上的核心分身得到以下的能力。\n" +
                "@>@A $T1 @[超越 1]@（从你的分身的下面把1张牌放置到分身废弃区）:抽1张牌。对战对手把手牌1张舍弃。@@\n" +
                "@A $T1 :攻击阶段@[超越 2]@（从你的分身的下面把牌合计2张放置到分身废弃区）\n" +
                "对战对手的精灵1只作为对象，将其横置并冻结。\n"
        );

        setType(CardType.KEY);
        setColor(CardColor.BLUE);
        setCost(Cost.coin(1));

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new TargetFilter().own().LRIG().withLevel(2,0), new AbilityGainModifier(this::onConstEffMod1GetSample),new AbilityGainModifier(this::onConstEffMod2GetSample));
        }
        
        private Ability onConstEffMod1GetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct1 = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(1), this::onAttachedActionEff1);
            attachedAct1.setUseLimit(UseLimit.TURN, 1);
            return attachedAct1;
        }
        private void onAttachedActionEff1()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().draw(1);
            getAbility().getSourceCardIndex().getIndexedInstance().discard(getOpponent(), 1);
        }

        private Ability onConstEffMod2GetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct2 = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(2), this::onAttachedActionEff2);
            attachedAct2.setUseLimit(UseLimit.TURN, 1);
            attachedAct2.setActiveUseTiming(UseTiming.ATTACK);
            attachedAct2.setNestedDescriptionOffset(1);
            return attachedAct2;
        }
        private void onAttachedActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            getAbility().getSourceCardIndex().getIndexedInstance().down(target);
            getAbility().getSourceCardIndex().getIndexedInstance().freeze(target);
        }
    }
}
