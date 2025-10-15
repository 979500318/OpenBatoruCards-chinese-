package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class LRIG_G4_MidorikoBondGirlTypeFour extends Card {

    public LRIG_G4_MidorikoBondGirlTypeFour()
    {
        setImageSets("WDK03-001");
        setLinkedImageSets("WDK03-006");

        setOriginalName("四型絆娘　翠子");
        setAltNames("シガタハンキミドリコ Shigata Hanki Midoriko");
        setDescription("jp",
                "@A #C：あなたのルリグデッキから《異体同心　華代》１枚を場に出す。\n" +
                "@A $T1 #C：【エナチャージ２】"
        );

        setName("en", "Midoriko, Bond Girl Type Four");
        setDescription("en",
                "@A #C: Put 1 \"Hanayo, Acting in Perfect Harmony\" from your LRIG deck onto the field.\n" +
                "@A $T1 #C: [[Ener Charge 2]]"
        );

		setName("zh_simplified", "四型绊娘 翠子");
        setDescription("zh_simplified", 
                "@A #C:从你的分身牌组把《異体同心　華代》1张出场。\n" +
                "@A $T1 #C:[[能量填充2]]\n"
        );

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setLevel(4);
        setLimit(11);
        setCoins(+3);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act1 = registerActionAbility(new CoinCost(1), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);

            ActionAbility act2 = registerActionAbility(new CoinCost(1), this::onActionEff2);
            act2.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onActionEff1Cond()
        {
            return new TargetFilter().own().key().withName("異体同心　華代").fromLocation(CardLocation.DECK_LRIG).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff1()
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().key().withName("異体同心　華代").fromLocation(CardLocation.DECK_LRIG)).get();
            if(cardIndex != null) putKeyOnField(cardIndex, new CostModifier(() -> new CoinCost(0), ModifierMode.SET));
        }
        
        private void onActionEff2()
        {
            enerCharge(2);
        }
    }
}
