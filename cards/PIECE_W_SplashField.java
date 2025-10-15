package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.stock.StockPlayerAbilityLRIGBarrier;
import open.batoru.data.ability.stock.StockPlayerAbilitySIGNIBarrier;

public final class PIECE_W_SplashField extends Card {

    public PIECE_W_SplashField()
    {
        setImageSets("WXDi-P14-001");
        setLinkedImageSets(Token_SIGNIBarrier.IMAGE_SET, Token_LRIGBarrier.IMAGE_SET);

        setOriginalName("スプラッシュフィールド");
        setAltNames("Supurasshu Fiirudo");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "以下の２つから１つを選ぶ。\n" +
                "$$1あなたのデッキの上からカードを５枚見る。その中からカードを２枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。【シグニバリア】１つを得る。\n" +
                "$$2対戦相手のシグニ１体を対象とし、それをトラッシュに置く。【ルリグバリア】１つを得る。"
        );

        setName("en", "Splash Field");
        setDescription("en",
                "\n=U =E You have the three LRIG on your field with three or more different colors among all members.\n\nChoose one of the following.\n$$1Look at the top five cards of your deck. Add up to two cards from among them to your hand and put the rest on the bottom of your deck in any order. Gain a [[SIGNI Barrier]].\n$$2Put target SIGNI on your opponent's field into its owner's trash. Gain a [[LRIG Barrier]]."
        );
        
        setName("en_fan", "Splash Field");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Look at the top 5 cards of your deck. Add up to 2 cards from among them to your hand, and put the rest on the bottom of your deck in any order. Gain 1 [[SIGNI Barrier]].\n" +
                "$$2 Target 1 of your opponent's SIGNI, and put it into the trash. Gain 1 [[LRIG Barrier]]."
        );

		setName("zh_simplified", "水花飞溅");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "从以下的2种选1种。\n" +
                "$$1 从你的牌组上面看5张牌。从中把牌2张最多加入手牌，剩下的任意顺序放置到牌组最下面。得到[[精灵屏障]]1个。\n" +
                "$$2 对战对手的精灵1只作为对象，将其放置到废弃区。得到[[分身屏障]]1个。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
            piece.setModeChoice(1);
        }

        private ConditionState onPieceEffCond()
        {
            return CardAbilities.getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            if(piece.getChosenModes() == 1)
            {
                look(5);
                
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromLooked());
                addToHand(data);
                
                while(getLookedCount() > 0)
                {
                    CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                }
                
                attachPlayerAbility(getOwner(), new StockPlayerAbilitySIGNIBarrier(), ChronoDuration.permanent());
            } else {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
                trash(cardIndex);

                attachPlayerAbility(getOwner(), new StockPlayerAbilityLRIGBarrier(), ChronoDuration.permanent());
            }
        }
    }
}
