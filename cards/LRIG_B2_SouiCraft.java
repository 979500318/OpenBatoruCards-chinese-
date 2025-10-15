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

public final class LRIG_B2_SouiCraft extends Card {

    public LRIG_B2_SouiCraft()
    {
        setImageSets("WX25-P2-021");
        setLinkedImageSets("WX25-P2-TK05", "WX25-P2-TK06");

        setOriginalName("ソウイ＝クラフト");
        setAltNames("ソウイクラフト Soui Kurafuto");
        setDescription("jp",
                "@E：《蒼穹将姫　ニブルヘイム》１枚と《蒼穹将姫　ユミル》１枚を公開する。それらのどちらか１枚を対戦相手に見せずに裏向きでルリグデッキに加える。"
        );

        setName("en", "Soui-Craft");
        setDescription("en",
                "@E: Reveal 1 \"Niflheim, Azure Sky General Princess\" and 1 \"Ymir, Azure Sky General Princess\". Add 1 of them into your LRIG deck face-down without showing it to your opponent."
        );

		setName("zh_simplified", "索薇=衍生");
        setDescription("zh_simplified", 
                "@E :《蒼穹将姫　ニヴルヘイム》1张和《蒼穹将姫　ユミル》1张公开。这些的其中1张对战对手不看，里向加入分身牌组。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SOUI);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
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
