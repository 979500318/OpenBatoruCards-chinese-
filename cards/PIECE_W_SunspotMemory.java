package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.stock.StockPlayerAbilityLRIGBarrier;
import open.batoru.data.ability.stock.StockPlayerAbilitySIGNIBarrier;

public final class PIECE_W_SunspotMemory extends Card {

    public PIECE_W_SunspotMemory()
    {
        setImageSets("WXDi-P12-001");
        setLinkedImageSets(Token_SIGNIBarrier.IMAGE_SET, Token_LRIGBarrier.IMAGE_SET);

        setOriginalName("黒点の記憶");
        setAltNames("コクテンノキオク Kokuten no Kioku");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "あなたのデッキの上からカードを３枚見る。その中から#Sのカード１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。\n" +
                "【シグニバリア】１つと【ルリグバリア】１つを得る。"
        );

        setName("en", "Sunspot Memory");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\nLook at the top three cards of your deck. Reveal a #S card from among them and add it to your hand. Put the rest on the bottom of your deck in any order.\nGain a [[SIGNI Barrier]] and a [[LRIG Barrier]].\n"
        );
        
        setName("en_fan", "Sunspot Memory");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "Look at the top 3 cards of your deck. Reveal 1 #S @[Dissona]@ card from among them, and add it to your hand, and put the rest on the bottom of your deck in any order.\n" +
                "You gain 1 [[SIGNI Barrier]] and 1 [[LRIG Barrier]]."
        );

		setName("zh_simplified", "黑点的记忆");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "从你的牌组上面看3张牌。从中把#S的牌1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "得到[[精灵屏障]]1个和[[分身屏障]]1个。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }

        private ConditionState onPieceEffCond()
        {
            return CardAbilities.getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            look(3);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().dissona().fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            attachPlayerAbility(getOwner(), new StockPlayerAbilitySIGNIBarrier(), ChronoDuration.permanent());
            attachPlayerAbility(getOwner(), new StockPlayerAbilityLRIGBarrier(), ChronoDuration.permanent());
        }
    }
}
