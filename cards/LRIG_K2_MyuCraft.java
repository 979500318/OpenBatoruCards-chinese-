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

public final class LRIG_K2_MyuCraft extends Card {

    public LRIG_K2_MyuCraft()
    {
        setImageSets("WXDi-P11-025", "WX25-P2-029");
        setLinkedImageSets("WXDi-P11-TK05", "WXDi-P11-TK06");

        setOriginalName("ミュウ＝クラフト");
        setAltNames("ミュウクラフト Myu Kurafuto");
        setDescription("jp",
                "@E：《黒大幻蟲　アラクネ・パイダ》１枚と《黒大幻蟲　オウグソク【ＦＡ】》１枚を公開する。それらのどちらか１枚を対戦相手に見せずに裏向きでルリグデッキに加える。"
        );

        setName("en", "Myu=Craft");
        setDescription("en",
                "@E: Reveal an \"Arachne Pider, Giant Phantom Insect\" and a \"King Clad [FA], Giant Phantom Insect\". Add one of them to your LRIG Deck face down without showing it to your opponent. "
        );
        
        setName("en_fan", "Myu-Craft");
        setDescription("en_fan",
                "@E: Reveal 1 \"Arachne Pider, Black Great Phantom Insect\" and 1 \"Ougusoku [FA], Black Great Phantom Insect\". Add 1 of them into your LRIG deck face-down without showing it to your opponent."
        );

		setName("zh_simplified", "缪＝衍生");
        setDescription("zh_simplified", 
                "@E :《黒大幻蟲　アラクネ・パイダ》1张和《黒大幻蟲　オウグソク[[ＦＡ]]》1张公开。这些的其中1张对战对手不看，里向加入分身牌组。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MYU);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
