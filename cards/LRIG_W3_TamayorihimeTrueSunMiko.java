package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
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

public final class LRIG_W3_TamayorihimeTrueSunMiko extends Card {

    public LRIG_W3_TamayorihimeTrueSunMiko()
    {
        setImageSets("WX24-D1-04");

        setOriginalName("真太陽の巫女　タマヨリヒメ");
        setAltNames("シンタイヨウノミコタマヨリヒメ Shintaiyou no Miko Tamayorihime");
        setDescription("jp",
                "@A $T1 %W @[手札から白のシグニを１枚捨てる]@：対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@A $G1 %W0：あなたのデッキの上からカードを５枚見る。その中からカードを２枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Tamayorihime, True Sun Miko");
        setDescription("en",
                "@A $T1 %W @[Discard 1 white SIGNI from your hand]@: Target 1 of your opponent's SIGNI, and return it to their hand.\n" +
                "@A $G1 %W0: Look at the top 5 cards of your deck. Add up to 2 cards from among them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "真太阳的巫女 玉依姬");
        setDescription("zh_simplified", 
                "@A $T1 %W从手牌把白色的精灵1张舍弃:对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "@A $G1 %W0:从你的牌组上面看5张牌。从中把牌2张最多加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
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
                new EnerCost(Cost.color(CardColor.WHITE, 1)),
                new DiscardCost(new TargetFilter().SIGNI().withColor(CardColor.WHITE))
            ), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(target);
        }

        private void onActionEff2()
        {
            look(5);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromLooked());
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
