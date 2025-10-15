package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class ARTS_B_BigCat extends Card {

    public ARTS_B_BigCat()
    {
        setImageSets("WX25-P1-006", "WX25-P1-006U");

        setOriginalName("ビッグ・キャット");
        setAltNames("ビッグキャット Biggu Kyatto");
        setDescription("jp",
                "@[ブースト]@ -- %B %X\n\n" +
                "対戦相手のパワー10000以下のシグニ１体を対象とし、それをダウンする。あなたがブーストしていた場合、カードを４枚引き、手札を２枚捨てる。"
        );

        setName("en", "Big Cat");
        setDescription("en",
                "@[Boost]@ -- %B %X\n\n" +
                "Target 1 of your opponent's SIGNI with power 10000 or less, and down it. If you used Boost, draw 4 cards, and discard 2 cards from your hand."
        );

		setName("zh_simplified", "巨化·喵咪");
        setDescription("zh_simplified", 
                "赋能—%B%X（这张必杀使用时，可以作为使用费用追加把%B%X:支付）\n" +
                "对战对手的力量10000以下的精灵1只作为对象，将其#D。你赋能的场合，抽4张牌，手牌2张舍弃。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setAdditionalCost(new EnerCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1)));
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI().withPower(0,10000)).get();
            down(target);
            
            if(arts.hasPaidAdditionalCost())
            {
                draw(4);
                discard(2);
            }
        }
    }
}

