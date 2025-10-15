package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B3_CodeArtDRess extends Card {

    public SIGNI_B3_CodeArtDRess()
    {
        setImageSets("WX24-D3-19");

        setOriginalName("コードアート　Dレス");
        setAltNames("コードアートディーレス Koodo Aato Dii Resu");
        setDescription("jp",
                "@A %B %B #D：対戦相手のパワー8000以下のシグニ１体を対象とし、それをデッキの一番下に置く。対戦相手の手札が２枚以下の場合、代わりに対戦相手のパワー10000以下のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Code Art D Ress");
        setDescription("en",
                "@A %B %B #D: Target 1 of your opponent's SIGNI with power 8000 or less, and put it on the bottom of their deck. If there are 2 or less cards in your opponent's hand, instead, target 1 of your opponent's SIGN with power 10000 or less, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "必杀代号 多罗丝赛露");
        setDescription("zh_simplified", 
                "@A %B %B#D:对战对手的力量8000以下的精灵1只作为对象，将其放置到牌组最下面。对战对手的手牌在2张以下的场合，作为替代，对战对手的力量10000以下的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.BLUE, 2)), new DownCost()), this::onActionEff);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withPower(0, getHandCount(getOpponent()) > 2 ? 8000 : 10000)).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
