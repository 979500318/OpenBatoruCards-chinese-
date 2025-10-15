package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_R2_LalaruCraft extends Card {

    public LRIG_R2_LalaruCraft()
    {
        setImageSets("WX25-P2-017");
        setLinkedImageSets("WX25-P2-TK03", "WX25-P2-TK04");

        setOriginalName("ララ・ルー\"Craft\"");
        setAltNames("ララルークラフト Rararuu Kurafuto");
        setDescription("jp",
                "@E：《コードヒート　ウイクロソジャービークル》１枚と《コードヒート　ウイクロソジャーロボ》１枚を公開する。それらのどちらか１枚を対戦相手に見せずに裏向きでルリグデッキに加える。"
        );

        setName("en", "Lalaru \"Craft\"");
        setDescription("en",
                "@E: Reveal 1 \"Code Heat Wixonger Vehicle\" and 1 \"Code Heat Wixonger Robo\". Add 1 of them into your LRIG deck face-down without showing it to your opponent."
        );

		setName("zh_simplified", "啦啦·噜“Craft”");
        setDescription("zh_simplified", 
                "@E :《コードヒート　ウィクロンジャービークル》1张和《コードヒート　ウィクロンジャーロボ》1张公开。这些的其中1张对战对手不看，里向加入分身牌组。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LALARU);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);

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
            look(craft(getLinkedImageSets().get(0)));
            look(craft(getLinkedImageSets().get(1)));

            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().Resona().fromLooked()).get();
            returnToDeck(cardIndex, DeckPosition.TOP);

            exclude(getCardsInLooked(getOwner()));
        }
    }
}
