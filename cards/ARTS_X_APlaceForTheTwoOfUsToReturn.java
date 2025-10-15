package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.ability.stock.StockPlayerAbilityLimitUpper;

public final class ARTS_X_APlaceForTheTwoOfUsToReturn extends Card {

    public ARTS_X_APlaceForTheTwoOfUsToReturn()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-034");
        setLinkedImageSets(Token_LimitUpper.IMAGE_SET);

        setOriginalName("ふたりの還る場所");
        setAltNames("フタリノカエルバショ Futari no Kaeru Basho");
        setDescription("jp",
                "好きな生徒１人との絆を獲得する。あなたのルリグゾーンに【リミットアッパー】１つを置く。"
        );

        setName("en", "A Place For the Two of Us to Return");
        setDescription("en",
                "Gain a bond with a student of your choice. Put 1 [[Limit Upper]] on your LRIG zone."
        );

		setName("zh_simplified", "二人回去的地方");
        setDescription("zh_simplified", 
                "获得与任意学生1人的羁绊。你的分身区放置[[界限高地]]1个。\n"
        );

        setType(CardType.ARTS);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            playerChoiceBond();

            attachPlayerAbility(getOwner(), new StockPlayerAbilityLimitUpper(), ChronoDuration.permanent());
        }
    }
}
