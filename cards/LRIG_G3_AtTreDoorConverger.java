package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.CardIndexSnapshot;
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
import open.batoru.data.ability.cost.TrashCost;

public final class LRIG_G3_AtTreDoorConverger extends Card {

    public LRIG_G3_AtTreDoorConverger()
    {
        setImageSets("WXDi-P16-012", "WXDi-P16-012U");

        setOriginalName("収斂せし扉　アト＝トレ");
        setAltNames("シュウレンセシトビラアトトレ Shuurenseshi Tobira Ato Tore");
        setDescription("jp",
                "@E：あなたの手札からカードを３枚までエナゾーンに置く。\n" +
                "@A $T1 %G0:以下の２つから１つを選ぶ。\n" +
                "$$1あなたの手札が０枚の場合、カードを２枚引く。\n" +
                "$$2あなたのエナゾーンにカードがない場合、【エナチャージ２】をする。\n" +
                "@A $G1 @[エナゾーンにあるすべてのカードをトラッシュに置き、手札をすべて捨てる]@：この方法でカードを合計５枚以上トラッシュに置いた場合、あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。"
        );

        setName("en", "At =Tre=, the Converged Gate");
        setDescription("en",
                "@E: Put up to three cards from your hand into your Ener Zone.\n@A $T1 %G0: Choose one of the following.\n$$1If you have no cards in your hand, draw two cards.\n$$2If you have no cards in your Ener Zone, [[Ener Charge 2]].\n@A $G1 @[Put all cards in your Ener Zone into your trash and discard your hand]@: If a total of five or more cards were put into your trash this way, shuffle your deck and add the top card of your deck to your Life Cloth."
        );
        
        setName("en_fan", "At-Tre, Door Converger");
        setDescription("en_fan",
                "@E: Put up to 3 cards from your hand into the ener zone.\n" +
                "@A $T1 %G0: @[@|Choose 1 of the following:|@]@\n" +
                "$$1 If there are 0 cards in your hand, draw 2 cards.\n" +
                "$$2 If there are no cards in your ener zone, [[Ener Charge 2]].\n" +
                "@A $G1 @[Put all cards from your ener zone into the trash, and discard all cards from your hand]@: If 5 or more cards were put into the trash this way, shuffle your deck, and add the top card of your deck to life cloth."
        );

		setName("zh_simplified", "收敛扉 亚特=TRE");
        setDescription("zh_simplified", 
                "@E :从你的手牌把牌3张最多放置到能量区。\n" +
                "@A $T1 %G0:从以下的2种选1种。\n" +
                "$$1 你的手牌在0张的场合，抽2张牌。\n" +
                "$$2 你的能量区没有牌的场合，[[能量填充2]]。\n" +
                "@A $G1 能量区的全部的牌放置到废弃区，手牌全部舍弃:这个方法把牌合计5张以上放置到废弃区的场合，你的牌组洗切把最上面的牌加入生命护甲。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AT);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act2 = registerActionAbility(new AbilityCostList(
                new TrashCost(() -> getEnerCount(getOwner()), new TargetFilter().own().fromEner()),
                new DiscardCost(() -> getHandCount(getOwner()))
            ), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.ENER).own().fromHand());
            putInEner(data);
        }

        private void onActionEff1()
        {
            if(playerChoiceMode() == 1)
            {
                if(getHandCount(getOwner()) == 0)
                {
                    draw(2);
                }
            } else if(getEnerCount(getOwner()) == 0)
            {
                enerCharge(2);
            }
        }
        
        private void onActionEff2()
        {
            if((getAbility().getCostPaidData(0,0) != null && getAbility().getCostPaidData(0,0).get() instanceof CardIndexSnapshot ? getAbility().getCostPaidData(0,0).size() : 0) +
               (getAbility().getCostPaidData(0,1) != null && getAbility().getCostPaidData(0,1).get() instanceof CardIndexSnapshot ? getAbility().getCostPaidData(0,1).size() : 0) >= 5)
            {
                shuffleDeck();
                addToLifeCloth(1);
            }
        }
    }
}
