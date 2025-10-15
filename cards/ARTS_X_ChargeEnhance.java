package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockPlayerAbilityLimitUpper;

public final class ARTS_X_ChargeEnhance extends Card {

    public ARTS_X_ChargeEnhance()
    {
        setImageSets("WX24-P2-041");
        setLinkedImageSets(Token_LimitUpper.IMAGE_SET);

        setOriginalName("チャージ・エンハンス");
        setAltNames("チャージエンハンス Chaaji Enhansu");
        setDescription("jp",
                "【エナチャージ２】をする。あなたのルリグゾーンに【リミットアッパー】１つを置く。"
        );

        setName("en", "Charge Enhance");
        setDescription("en",
                "[[Ener Charge 2]]. Put 1 [[Limit Upper]] on your LRIG zone."
        );

		setName("zh_simplified", "填充·突破");
        setDescription("zh_simplified", 
                "[[能量填充2]]。你的分身区放置[[界限高地]]1个。\n"
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
            enerCharge(2);
            
            attachPlayerAbility(getOwner(), new StockPlayerAbilityLimitUpper(), ChronoDuration.permanent());
        }
    }
}

