package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class PIECE_W_SeleneGirlsAcademyAfterSchool extends Card {

    public PIECE_W_SeleneGirlsAcademyAfterSchool()
    {
        setImageSets("WXDi-CP01-001");

        setOriginalName("世怜音女学院 After School");
        setAltNames("セレイネジョガクインアフタースクール Sereine Jogakuin Afutaa Sukuuru");
        setDescription("jp",
                "このピースを使用する際、使用コストとして追加でエクシード４を支払ってもよい。\n\n" +
                "あなたのデッキの上からカードを５枚見る。その中から＜バーチャル＞のシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。追加でエクシード４を支払っていた場合、【エナチャージ１】をする。"
        );

        setName("en", "SELENE Girls' Academy, After School");
        setDescription("en",
                "As you use this PIECE, you may pay Exceed 4 as an additional use cost. \n\nLook at the top five cards of your deck. Reveal up to two <<Virtual>> SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in any order. If you paid the Exceed 4, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Selene Girls' Academy After School");
        setDescription("en_fan",
                "While using this piece, you may pay an additional @[Exceed 4]@ for its use cost.\n\n" +
                "Look at the top 5 cards of your deck. Reveal up to 2 <<Virtual>> SIGNI from among them, and add them to your hand, and put the rest on the bottom of your deck in any order. If you additionally paid @[Exceed 4]@, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "世怜音女学院 After School　");
        setDescription("zh_simplified", 
                "这张和音使用时，可以作为使用费用追加把@[超越 4]@支付。（从你的分身的下面把牌合计4张放置到分身废弃区）\n" +
                "从你的牌组上面看5张牌。从中把<<バーチャル>>精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。追加把@[超越 4]@支付过的场合，[[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.RELAY);

        setType(CardType.PIECE);
        setColor(CardColor.WHITE);
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
            piece.setAdditionalCost(new ExceedCost(4));
        }
        
        private void onPieceEff()
        {
            look(5);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromLooked());
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            if(piece.hasPaidAdditionalCost())
            {
                enerCharge(1);
            }
        }
    }
}
