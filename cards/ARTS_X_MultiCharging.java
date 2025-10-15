package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityMultiEner;

public final class ARTS_X_MultiCharging extends Card {

    public ARTS_X_MultiCharging()
    {
        setImageSets("WX24-P3-042");

        setOriginalName("マルチ・チャージング");
        setAltNames("マルチチャージング Maruchi Chaajingu");
        setDescription("jp",
                "【エナチャージ３】をする。このターン、あなたのエナゾーンにあるカードは【マルチエナ】を得る。"
        );

        setName("en", "Multi Charging");
        setDescription("en",
                "[[Ener Charge 3]]. This turn, cards in your ener zone gain [[Multi Ener]]."
        );

		setName("zh_simplified", "万花·充能");
        setDescription("zh_simplified", 
                "[[能量填充3]]。这个回合，你的能量区的牌得到[[万花色]]。\n"
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
            enerCharge(3);

            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().fromEner(), new AbilityGainModifier(this::onAttachedConstEffSharedGetSample));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
        private Ability onAttachedConstEffSharedGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityMultiEner());
        }
    }
}

