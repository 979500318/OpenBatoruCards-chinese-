package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class SIGNI_B2_CodeArtWAterCutter extends Card {

    public SIGNI_B2_CodeArtWAterCutter()
    {
        setImageSets("WX24-P4-072");

        setOriginalName("コードアート　Wオーターカッター");
        setAltNames("コードアートダブリューオーターカッター Koodo Aato Daburyuu Oota Kattaa Water Cutter");
        setDescription("jp",
                "@A $T1 @[手札を３枚まで捨てる]@：あなたの手札からコストの合計が「この方法で捨てたカードの枚数」以下のスペル１枚をコストを支払わずに使用する。"
        );

        setName("en", "Code Art W Ater Cutter");
        setDescription("en",
                "@A $T1 @[Discard up to 3 cards from your hand]@: Use 1 spell from your hand with a total cost equal to or less than \"the number of cards discarded this way\" without paying its cost."
        );

		setName("zh_simplified", "必杀代号 水刀切割机");
        setDescription("zh_simplified", 
                "@A $T1 手牌3张最多舍弃:从你的手牌把费用的合计在\n" +
                "@>:这个方法舍弃的牌的张数@@\n" +
                "以下的魔法1张不把费用支付，使用。\n" +
                "（费用的合计是，牌的左上的能量费用的数字的合计。例费用是%W%X %X:的场合，费用的合计是3）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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

            ActionAbility act = registerActionAbility(new DiscardCost(0,3), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private void onActionEff()
        {
            int count = !getAbility().getCostPaidData().isEmpty() && getAbility().getCostPaidData().get() != null ? getAbility().getCostPaidData().size() : 0;
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ACTIVATE).own().spell().withCost(0,count).fromHand()).get();
            if(cardIndex != null) use(cardIndex, new CostModifier(() -> new EnerCost(Cost.colorless(0)), ModifierMode.SET));
        }
    }
}
