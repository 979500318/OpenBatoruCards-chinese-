package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_B2_CodeEatWaterJelly extends Card {

    public SIGNI_B2_CodeEatWaterJelly()
    {
        setImageSets(Mask.IGNORE+"SPDi01-127");

        setOriginalName("コードイート　ミズゼリー");
        setAltNames("コードイートミズゼリー Koodo Iito Mizu Jerii");
        setDescription("jp",
                "@E：あなたのデッキの一番上を公開する。あなたの場にそのカードと共通する色を持つルリグがいる場合、そのカードをこのシグニの下に置く。\n" +
                "@A $T1：このシグニの下からカード１枚を手札に加える。"
        );

        setName("en", "Code Eat Water Jelly");
        setDescription("en",
                "@E: Reveal the top card of your deck. If it shares a common color with a LRIG on your field, put it under this SIGNI.\n" +
                "@A #D: Add 1 card from under this SIGNI to your hand."
        );

		setName("zh_simplified", "食用代号 水果冻");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面公开。你的场上有持有与那张牌共通颜色的分身的场合，那张牌放置到这只精灵的下面。\n" +
                "@A 横置:从这只精灵的下面把1张牌加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(2);
        setPower(8000);

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

            registerActionAbility(new DownCost(), this::onActionEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                if(new TargetFilter().own().anyLRIG().withColor(cardIndex.getIndexedInstance().getColor()).getValidTargetsCount() == 0 ||
                   !attach(getCardIndex(), cardIndex, CardUnderType.UNDER_GENERIC))
                {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }

        private void onActionEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).own().under(getCardIndex())).get();
            addToHand(cardIndex);
        }
    }
}
