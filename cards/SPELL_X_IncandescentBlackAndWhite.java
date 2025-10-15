package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ModifiableVariable.ModifiableValueReference;

public final class SPELL_X_IncandescentBlackAndWhite extends Card {

    public SPELL_X_IncandescentBlackAndWhite()
    {
        setImageSets("PR-Di017A");
        setDoubleFacedCardPairImageSetHint("PR-Di017B");

        setOriginalName("白熱する黒白");
        setAltNames("ハクネツスルコクビャク Hakunetsu suru Rukokubyaku");
        setDescription("jp",
                "カードを2枚引く。その後、あなたのライフクロスが1枚以下の場合、チェックゾーンにあるこのカードを裏返して≪REV:アンコーリング≫を場に出す。"
        );

        setName("en", "Incandescent Black and White");
        setDescription("en",
                "Draw 2 cards. Then, if you have 1 or less life cloth, flip this card in the check zone and put it onto the field as \"REV: Encoring\"."
        );

		setName("zh_simplified", "白热的黑白");
        setDescription("zh_simplified", 
                "抽2张牌。然后，你的生命护甲在1张以下的场合，检查区的这张牌翻面把≪REV:アンコーリング≫出场。（不能出场的场合则放置到废弃区）\n"
        );

        setType(CardType.SPELL);
        setCost(Cost.colorless(2));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerSpellAbility(this::onSpellEff);
        }

        private void onSpellEff()
        {
            draw(2);

            if(getLifeClothCount(getOwner()) <= 1 && getCardIndex().getLocation() == CardLocation.CHECK_ZONE)
            {
                Card.IndexedInstance indexedInstance = transform(getCardIndex(), "PR-Di017B", ChronoDuration.permanent());
                if(indexedInstance != null)
                {
                    ModifiableValueReference<Integer> value = indexedInstance.getCardStateFlags().addValue(CardStateFlag.DONT_RESET_STATS);
                    putOnField(getCardIndex());
                    indexedInstance.getCardStateFlags().removeValue(value);
                }
            }
        }
    }
}

