package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class ARTS_X_MiracleCharging extends Card {

    public ARTS_X_MiracleCharging()
    {
        setImageSets("WX24-D1-10");

        setOriginalName("ミラクル・チャージング");
        setAltNames("ミラクルチャージング Mirakuru Chaajingu");
        setDescription("jp",
                "カードを３枚引くか【エナチャージ３】をする。"
        );

        setName("en", "Miracle Charging");
        setDescription("en",
                "Draw 3 cards or [[Ener Charge 3]]."
        );

		setName("zh_simplified", "奇迹·充能");
        setDescription("zh_simplified", 
                "抽3张牌或[[能量填充3]]。\n"
        );

        setType(CardType.ARTS);
        setUseTiming(UseTiming.ATTACK);

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
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                draw(3);
            } else {
                enerCharge(3);
            }
        }
    }
}

