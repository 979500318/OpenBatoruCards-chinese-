package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockPlayerAbilityLimitUpper;

public final class ARTS_X_DrawEnhance extends Card {

    public ARTS_X_DrawEnhance()
    {
        setImageSets("WX24-D1-09", "PR-Di033");
        setLinkedImageSets(Token_LimitUpper.IMAGE_SET);

        setOriginalName("ドロー・エンハンス");
        setAltNames("ドローエンハンス Doroo Enhansu");
        setDescription("jp",
                "カードを２枚引く。あなたのルリグゾーンに【リミットアッパー】１つを置く。"
        );

        setName("en", "Draw Enhance");
        setDescription("en",
                "Draw 2 cards. Put 1 [[Limit Upper]] on your LRIG zone."
        );

		setName("zh_simplified", "抽卡·突破");
        setDescription("zh_simplified", 
                "抽2张牌。你的分身区放置[[界限高地]]1个。\n"
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
            draw(2);
            
            attachPlayerAbility(getOwner(), new StockPlayerAbilityLimitUpper(), ChronoDuration.permanent());
        }
    }
}

