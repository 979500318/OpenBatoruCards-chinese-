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

public final class LRIG_G2_AiyaiCraft extends Card {

    public LRIG_G2_AiyaiCraft()
    {
        setImageSets("WXDi-P11-019", "WX25-P2-025");
        setLinkedImageSets("WXDi-P11-TK03", "WXDi-P11-TK04");

        setOriginalName("アイヤイ★クラフト");
        setAltNames("アイヤイクラフト Aiyai Kurafuto");
        setDescription("jp",
                "@E：《緑参ノ遊姫　メリゴラン》１枚と《緑参ノ遊姫　アスレ【ＨＡＲＤ】》１枚を公開する。それらのどちらか１枚を対戦相手に見せずに裏向きでルリグデッキに加える。"
        );

        setName("en", "Aiyai ★ Craft");
        setDescription("en",
                "@E: Reveal a \"Merigoran, Green Extreme Play\" and an \"Asure [Hard], Green Extreme Play\". Add one of them to your LRIG Deck face down without showing it to your opponent. "
        );
        
        setName("en_fan", "Aiyai★Craft");
        setDescription("en_fan",
                "@E: Reveal 1 \"Merrygoron, Green Third Play Princess\" and 1 \"Athle [HARD], Green Third Play Princess\". Add 1 of them into your LRIG deck face-down without showing it to your opponent."
        );

		setName("zh_simplified", "艾娅伊★衍生");
        setDescription("zh_simplified", 
                "@E :《緑参ノ遊姫　メリゴラン》1张和《緑参ノ遊姫　アスレ[[ＨＡＲＤ]]》1张公开。这些的其中1张对战对手不看，里向加入分身牌组。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AIYAI);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
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
