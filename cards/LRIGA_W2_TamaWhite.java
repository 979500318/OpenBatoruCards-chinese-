package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.Enter;

public final class LRIGA_W2_TamaWhite extends Card {

    public LRIGA_W2_TamaWhite()
    {
        setImageSets("WX24-P1-036");

        setOriginalName("タマ・ほわいと");
        setAltNames("タマホワイト Tama Howaito");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中からレベル２以下のシグニを２枚まで場に出し、残りを好きな順番でデッキの一番下に置く。それらのシグニの@E能力は発動しない。"
        );

        setName("en", "Tama, White");
        setDescription("en",
                "@E: Look at the top 5 cards of your deck. Put up to 2 level 2 or lower SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. The @E abilities of SIGNI put onto the field this way don't activate."
        );

		setName("zh_simplified", "小玉·圣芒");
        setDescription("zh_simplified", 
                "@E 从你的牌组上面看5张牌。从中把等级2以下的精灵2张最多出场，剩下的任意顺序放置到牌组最下面。这些精灵的@E能力不能发动。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAMA);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(0,2).fromLooked().playable());
            putOnField(data, Enter.DONT_ACTIVATE);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
