package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_K_LikeFriends extends Card {

    public PIECE_K_LikeFriends()
    {
        setImageSets("WXDi-P10-003");

        setOriginalName("ライク・ア・フレンズ");
        setAltNames("ライクアフレンズ Raiku A Furenzu Like A Friends");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "対戦相手は手札を１枚捨て、自分のシグニ１体を選びトラッシュに置き、自分のエナゾーンからカード１枚を選びトラッシュに置き、自分のデッキの上からカードを２枚トラッシュに置く。"
        );

        setName("en", "Like Friends");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "Your opponent discards a card, chooses a SIGNI on their field and puts it into the trash, chooses a card from their Ener Zone and puts it into the trash, and puts the top two cards of their deck into their trash."
        );
        
        setName("en_fan", "Like Friends");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "Your opponent discards 1 card from their hand, chooses 1 of their SIGNI and puts it into the trash, chooses 1 card from their ener zone and puts it into the trash, and puts the top 2 cards of their deck into the trash."
        );

		setName("zh_simplified", "一·见·如故");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "对战对手把手牌1张舍弃，选自己的精灵1只放置到废弃区，从自己的能量区选1张牌放置到废弃区，从自己的牌组上面把2张牌放置到废弃区。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(2));
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
            discard(getOpponent(), 1);
            
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.TRASH).own().SIGNI()).get();
            trash(cardIndex);
            
            cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
            trash(cardIndex);
            
            millDeck(getOpponent(), 2);
        }
    }
}
