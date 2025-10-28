package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class ARTS_K_AncientBlade extends Card {

    public ARTS_K_AncientBlade()
    {
        setImageSets("WX25-P1-043");

        setOriginalName("アンシエント・ブレード");
        setAltNames("アンシエントブレード Anshiento Bureedo");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。あなたのデッキの上からカードを５枚見る。その中からカードを１枚までトラッシュに置き、＜古代兵器＞のシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Ancient Blade");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power. Look at the top 5 cards of your deck. Put up to 1 card from among them into the trash, reveal up to 2 <<Ancient Weapon>> SIGNI from among them, add them to your hand, and put the rest on the bottom of your deck in any order."
        );

        setName("zh_simplified", "远古·刀刃");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。从你的牌组上面看5张牌。从中把牌1张最多放置到废弃区，<<古代兵器>>精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
            
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().fromLooked()).get();
            trash(cardIndex);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ANCIENT_WEAPON).fromLooked());
            reveal(data);
            addToHand(data);
            
            returnToDeckOrdered(CardLocation.LOOKED, DeckPosition.BOTTOM);
        }
    }
}

