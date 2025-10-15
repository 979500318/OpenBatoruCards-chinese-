package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_B3_CodePirulukOmegaSquared extends Card {

    public LRIG_B3_CodePirulukOmegaSquared()
    {
        setImageSets("WX24-D3-04");

        setOriginalName("コード・ピルルク・Ω²");
        setAltNames("コードピルルクオメガスクエアド Koodo Piruruku Omega Sukueado Squared");
        setDescription("jp",
                "@A $T2 @[手札から青のシグニを１枚捨てる]@：対戦相手の手札を１枚見ないで選び、捨てさせる。\n" +
                "@A $G1 %B0：対戦相手のルリグ１体とシグニ１体を対象とし、それらを凍結する。"
        );

        setName("en", "Code Piruluk Ω²");
        setDescription("en",
                "@A $T2 @[Discard 1 blue SIGNI from your hand]@: Choose 1 card from your opponent's hand without looking, and discard it.\n" +
                "@A $G1 %B0: Target 1 of your opponent's LRIG and 1 of your opponent's SIGNI, and freeze them."
        );

		setName("zh_simplified", "代号·皮璐璐可·Ω2");
        setDescription("zh_simplified", 
                "@A $T2 从手牌把蓝色的精灵1张舍弃:不看对战对手的手牌选1张，舍弃。\n" +
                "@A $G1 %B0:对战对手的分身1只和精灵1只作为对象，将这些冻结。\n"
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

            ActionAbility act1 = registerActionAbility(new DiscardCost(new TargetFilter().SIGNI().withColor(CardColor.BLUE)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 2);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onActionEff1()
        {
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }

        private void onActionEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG());
            data.add(playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get());
            freeze(data);
        }
    }
}
