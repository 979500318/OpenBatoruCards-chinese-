package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_B3_LostCodePiruluk extends Card {

    public LRIG_B3_LostCodePiruluk()
    {
        setImageSets("WX24-P1-013", "WX24-P1-013U");

        setOriginalName("ロストコード・ピルルク");
        setAltNames("ロストコードピルルク Rosuto Koodo Piruruku");
        setDescription("jp",
                "@U $TO $T1：あなたの＜電機＞のシグニ１体が場に出たとき、あなたのトラッシュからそのシグニと共通する色を持つスペル１枚を対象とし、それを手札に加える。\n" +
                "@A $G1 @[@|アプリ|@]@ %B0：カードを３枚引き、手札からスペルを好きな枚数捨てる。この方法で捨てたカード１枚につき対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Lost Code Piruluk");
        setDescription("en",
                "@U $TO $T1: When 1 of your <<Electric Machine>> SIGNI enters the field, target 1 spell that shares a common color with that SIGNI from your trash, and add it to your hand.\n" +
                "@A $G1 @[@|Appli|@]@ %B0: Draw 3 cards, and discard any number of spells from your hand. Your opponent discards 1 card from their hand for each spell discarded this way."
        );

		setName("zh_simplified", "失落代号·皮璐璐可");
        setDescription("zh_simplified", 
                "@U $TO $T1 :当你的<<電機>>精灵1只出场时，从你的废弃区把持有与那只精灵共通颜色的魔法1张作为对象，将其加入手牌。\n" +
                "@A $G1 凝望%B0:抽3张牌，从手牌把魔法任意张数舍弃。依据这个方法舍弃的牌的数量，每有1张对战对手就把手牌1张舍弃。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            auto.enableEventSourceSelection();

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Appli");
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.ELECTRIC_MACHINE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().spell().withColor(getEvent().getCaller().getColor()).fromTrash()).get();
            addToHand(target);
        }

        private void onActionEff()
        {
            draw(3);
            
            DataTable<CardIndex> dataDiscarded = discard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter().spell());
            if(dataDiscarded.get() != null) discard(getOpponent(), dataDiscarded.size());
        }
    }
}
