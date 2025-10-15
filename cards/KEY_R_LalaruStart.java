package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class KEY_R_LalaruStart extends Card {

    public KEY_R_LalaruStart()
    {
        setImageSets("WXK01-014");

        setOriginalName("ララ・ルー”スタート”");
        setAltNames("ララルースタート Rara Ruu Sutaato");
        setDescription("jp",
                "@A $T1 %R %X：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A @[このキーを場からルリグトラッシュに置く]@：対戦相手のシグニ２体を対象とし、それらをバニッシュする。"
        );

        setName("en", "Lalaru \"Start\"");
        setDescription("en",
                "@A $T1 %R %X: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@A @[Put this key from the field into the LRIG trash]@: Target 2 of your opponent's SIGNI, and banish them."
        );

		setName("zh_simplified", "啦啦·噜“开始”");
        setDescription("zh_simplified", 
                "@A $T1 %R%X:对战对手的精灵1只作为对象，将其破坏。\n" +
                "@A 这张钥匙从场上放置到分身废弃区:对战对手的精灵2只作为对象，将这些破坏。（只有1只精灵时不能使用）\n"
        );

        setType(CardType.KEY);
        setColor(CardColor.RED);
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

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act2 = registerActionAbility(new TrashCost(), this::onActionEff2);
            act2.setCondition(this::onActionEff2Cond);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        
        private ConditionState onActionEff2Cond()
        {
            return getSIGNICount(getOpponent()) < 2 ? ConditionState.WARN : ConditionState.OK;
        }
        private void onActionEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(2, new TargetFilter(TargetHint.BANISH).OP().SIGNI());
            banish(data);
        }
    }
}
