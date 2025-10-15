package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.FieldZone;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneBackground;

public final class ARTS_K_GraveEvening extends Card {

    public ARTS_K_GraveEvening()
    {
        setImageSets("WX24-P2-039");

        setOriginalName("グレイブ・イブニング");
        setAltNames("グレイブイブニング Gureibu Ibuningu");
        setDescription("jp",
                "対戦相手のシグニゾーン１つを指定する。このターン、そのシグニゾーンにあるシグニのパワーを－5000する。\n" +
                "あなたのデッキの上からカードを５枚見る。その中から＜迷宮＞のシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Grave Evening");
        setDescription("en",
                "Choose 1 of your opponent's SIGNI zones. This turn, the SIGNI in that SIGNI zone gets --5000 power.\n" +
                "Look at the top 5 cards of your deck. Reveal up to 2 <<Labyrinth>> SIGNI from among them, and add them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "墓地·薄暮");
        setDescription("zh_simplified", 
                "对战对手的精灵区1个指定。这个回合，那个精灵区的精灵的力量-5000。\n" +
                "从你的牌组上面看5张牌。从中把<<迷宮>>精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
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
            FieldZone fieldZone = playerTargetZone(new TargetFilter().OP().SIGNI()).get();
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().fromLocation(fieldZone.getZoneLocation()), new PowerModifier(-5000));
            
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
            GFX.attachToChronoRecord(record, new GFXZoneBackground(getOpponent(),fieldZone.getZoneLocation(), "full_moon", 1.2, new int[]{90,80,120}));
            
            attachPlayerAbility(getOwner(), attachedConst, record);
            
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.LABYRINTH).fromLooked());
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
