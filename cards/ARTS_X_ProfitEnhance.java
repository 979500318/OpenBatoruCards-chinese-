package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockPlayerAbilityLimitUpper;

public final class ARTS_X_ProfitEnhance extends Card {

    public ARTS_X_ProfitEnhance()
    {
        setImageSets("WX24-P1-031", "SPDi37-12");
        setLinkedImageSets(Token_LimitUpper.IMAGE_SET);

        setOriginalName("プロフィット・エンハンス");
        setAltNames("プロフィットエンハンス Purofitto Enhansu");
        setDescription("jp",
                "カードを１枚引き【エナチャージ１】をする。あなたのルリグゾーンに【リミットアッパー】１つを置く。"
        );

        setName("en", "Profit Enhance");
        setDescription("en",
                "Draw 1 card, and [[Ener Charge 1]]. Put 1 [[Limit Upper]] on your LRIG zone."
        );

		setName("zh_simplified", "获益·突破");
        setDescription("zh_simplified", 
                "抽1张牌并[[能量填充1]]。你的分身区放置[[界限高地]]1个。\n"
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
            draw(1);
            enerCharge(1);
            
            attachPlayerAbility(getOwner(), new StockPlayerAbilityLimitUpper(), ChronoDuration.permanent());
        }
    }
}

