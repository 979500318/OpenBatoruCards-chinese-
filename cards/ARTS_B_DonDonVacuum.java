package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;

public final class ARTS_B_DonDonVacuum extends Card {

    public ARTS_B_DonDonVacuum()
    {
        setImageSets("WX24-P1-023");

        setOriginalName("ドンドン・バキューム");
        setAltNames("ドンドンバキューム Dondon Bakyuumu");
        setDescription("jp",
                "対戦相手のパワー10000以下のシグニ１体を対象とし、対戦相手が手札を２枚捨てないかぎり、それをバニッシュする。あなたのデッキの上からカードを５枚見る。その中からスペルか＜電機＞のシグニを合計２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "DonDon Vacuum");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 10000 or less, and banish it unless your opponent discards 2 cards from their hand. Look at the top 5 cards of your deck. Reveal up to 2 spell and/or <<Electric Machine>> SIGNI from among them, and add them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "咚咚·真空");
        setDescription("zh_simplified", 
                "对战对手的力量10000以下的精灵1只作为对象，如果对战对手不把手牌2张舍弃，那么将其破坏。从你的牌组上面看5张牌。从中把魔法或<<電機>>精灵合计2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            if(target != null && discard(getOpponent(), 0,2, ChoiceLogic.BOOLEAN).size() != 2)
            {
                banish(target);
            }

            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().or(new TargetFilter().spell(), new TargetFilter().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE).fromLooked()).fromLooked());
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}

