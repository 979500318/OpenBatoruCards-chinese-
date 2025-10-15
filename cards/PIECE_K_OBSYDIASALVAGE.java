package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class PIECE_K_OBSYDIASALVAGE extends Card {

    public PIECE_K_OBSYDIASALVAGE()
    {
        setImageSets("WXDi-CP01-003");

        setOriginalName("OBSYDIA SALVAGE");
        setAltNames("オブシディアサルベージ Obushidia Sarubeeji");
        setDescription("jp",
                "このピースを使用する際、使用コストとして追加でエクシード４を支払ってもよい。\n\n" +
                "あなたのデッキの上からカードを２枚トラッシュに置く。その後、あなたのトラッシュから＜バーチャル＞のシグニを２枚まで対象とし、それらを手札に加える。追加でエクシード４を支払っていた場合、追加であなたのトラッシュから＜バーチャル＞のシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "OBSYDIA SALVAGE");
        setDescription("en",
                "As you use this PIECE, you may pay Exceed 4 as an additional use cost. \n\nPut the top two cards of your deck into your trash. Then, add up to two target <<Virtual>> SIGNI from your trash to your hand. If you paid the Exceed 4, in addition, add target <<Virtual>> SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "OBSYDIA SALVAGE");
        setDescription("en_fan",
                "While using this piece, you may pay an additional @[Exceed 4]@ for its use cost.\n\n" +
                "Put the top 2 cards of your deck into the trash. Then, target up to 2 <<Virtual>> SIGNI from your trash, and add them to your hand. If you additionally paid @[Exceed 4]@, additionally target 1 <<Virtual>> SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "OBSYDIA SALVAGE");
        setDescription("zh_simplified", 
                "这张和音使用时，可以作为使用费用追加把@[超越 4]@支付。（从你的分身的下面把牌合计4张放置到分身废弃区）\n" +
                "从你的牌组上面把2张牌放置到废弃区。然后，从你的废弃区把<<バーチャル>>精灵2张最多作为对象，将这些加入手牌。追加把@[超越 4]@支付过的场合，追加从你的废弃区把<<バーチャル>>精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.RELAY);

        setType(CardType.PIECE);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(1));
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
            millDeck(2);
            
            TargetFilter filter = new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash();
            
            DataTable<CardIndex> data = playerTargetCard(0,2, filter);
            addToHand(data);
            
            if(piece.hasPaidAdditionalCost())
            {
                CardIndex target = playerTargetCard(filter).get();
                addToHand(target);
            }
        }
    }
}
