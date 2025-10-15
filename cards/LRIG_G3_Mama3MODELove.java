package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

import java.util.List;

public final class LRIG_G3_Mama3MODELove extends Card {

    public LRIG_G3_Mama3MODELove()
    {
        setImageSets("WXDi-P09-008", "WXDi-P09-008U");

        setOriginalName("ママ♥３ ＭＯＤＥ慈愛");
        setAltNames("ママスリーモードジアイ Mama Surii Moodo Jiai");
        setDescription("jp",
                "@A $T1 @[エナゾーンからそれぞれレベルの異なるシグニ３枚をトラッシュに置く]@：対戦相手のレベル３以上のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A $G1 %G0：次の対戦相手のターンの間、あなたが最初にダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Mama ♥ 3 MODE Love");
        setDescription("en",
                "@A $T1 @[Put three SIGNI with different levels from your Ener Zone into your trash]@: Vanish target level three or more SIGNI on your opponent's field.\n" +
                "@A $G1 %G0: During your opponent's next turn, if you would be the first to take damage, instead you do not take that damage."
        );
        
        setName("en_fan", "Mama♥3 MODE Love");
        setDescription("en_fan",
                "@A $T1 @[Put 3 SIGNI with different levels from your ener zone into the trash]@: Target 1 of your opponent's level 3 or higher SIGNI, and banish it.\n" +
                "@A $G1 %G0: During your opponent's next turn, the first time you would be damaged, instead you aren't damaged."
        );
        
		setName("zh_simplified", "妈妈♥3 MODE慈爱");
        setDescription("zh_simplified", 
                "@A $T1 从能量区把等级不同的精灵3张放置到废弃区:对战对手的等级3以上的精灵1只作为对象，将其破坏。\n" +
                "@A $G1 %G0:下一个对战对手的回合期间，你最初受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MAMA);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);
        setCoins(+1);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act1 = registerActionAbility(new TrashCost(3, new TargetFilter().SIGNI().fromEner(), this::onActionEff1CostTargetCond), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private ConditionState onActionEff1Cond()
        {
            return new TargetFilter().own().SIGNI().fromEner().getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).distinct().count() >= 3 ?
                    ConditionState.OK : ConditionState.BAD;
        }
        private boolean onActionEff1CostTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 3 && listPickedCards.stream().mapToInt(c -> c.getIndexedInstance().getLevel().getValue()).distinct().count() == 3;
        }
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(3,0)).get();
            banish(target);
        }
        
        private void onActionEff2()
        {
            blockNextDamage(ChronoDuration.nextTurnEnd(getOpponent()), cardIndexSnapshot -> !isOwnTurn());
        }
    }
}
