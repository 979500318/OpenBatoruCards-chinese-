package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class LRIG_G3_MidorikoWarEmpressTypeThree extends Card {

    public LRIG_G3_MidorikoWarEmpressTypeThree()
    {
        setImageSets("WX24-D4-04");

        setOriginalName("三式戦帝女　緑姫");
        setAltNames("サンシキセンテイジョミドリコ Sanshiki Senteijo Midoriko");
        setDescription("jp",
                "@A $T1 %G @[手札から緑のシグニを１枚捨てる]@：あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋3000し、それは【ランサー】を得る。\n" +
                "@A $G1 %G0：【エナチャージ２】をする。その後、あなたのエナゾーンからカードを２枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "Midoriko, War Empress Type Three");
        setDescription("en",
                "@A $T1 %G @[Discard 1 green SIGNI from your hand]@: Target 1 of your SIGNI, and until end of turn, it gets +3000 power and [[Lancer]].\n" +
                "@A $G1 %G0: [[Ener Charge 2]]. Then, target up to 2 cards from your ener zone, and add them to your hand."
        );

		setName("zh_simplified", "三式战帝女 绿姬");
        setDescription("zh_simplified", 
                "@A $T1 %G从手牌把绿色的精灵1张舍弃:你的精灵1只作为对象，直到回合结束时为止，其的力量+3000，其得到[[枪兵]]。\n" +
                "@A $G1 %G0:[[能量填充2]]。然后，从你的能量区把牌2张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIDORIKO);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
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

            ActionAbility act1 = registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.GREEN, 1)),
                new DiscardCost(new TargetFilter().SIGNI().withColor(CardColor.GREEN))
            ), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null)
            {
                gainPower(target, 3000, ChronoDuration.turnEnd());
                attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
            }
        }

        private void onActionEff2()
        {
            enerCharge(2);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromEner());
            addToHand(data);
        }
    }
}
