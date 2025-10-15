package open.batoru.data.cards;

import open.batoru.catalog.description.DescriptionParser;
import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityCopyModifier;
import open.batoru.data.ability.modifiers.CardNameModifier;
import open.batoru.data.ability.stock.StockAbilitySLancer;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class LRIG_G4_MidorikoTypeKindledHeart extends Card {

    public LRIG_G4_MidorikoTypeKindledHeart()
    {
        setImageSets("WX24-P4-020", "WX24-P4-020U");

        setOriginalName("熾型心　緑姫");
        setAltNames("シガタシンミドリコ Shigatashin Midoriko");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜緑子＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：あなたのエナゾーンからシグニを１枚まで対象とし、それを場に出す。その後、あなたのシグニ１体を対象とし、ターン終了時まで、それは【Ｓランサー】を得る。\n" +
                "@A $G1 @[@|ワナ|@]@ %G0：&E４枚以上@0次の対戦相手のターン終了時まで、あなたのすべてのシグニのパワーを＋5000し、それらは[[シャドウ（{{パワーがこのシグニのパワーの半分以下のシグニ$パワー%1以下のシグニ}}）]]を得る。"
        );

        setName("en", "Midoriko Type Kindled Hearts");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Midoriko>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: Target up to 1 SIGNI from your ener zone, and put it onto the field. Then, target 1 of your SIGNI, and until end of turn, it gains [[S Lancer]].\n" +
                "@A $G1 @[@|Wanna|@]@ %G0: &E4 or more@0 Until the end of your opponent's next turn, all of your SIGNI get +5000 power, and [[Shadow ({{SIGNI with power equal to or less than half this SIGNI's$SIGNI with power %1 or less}})]]."
        );

		setName("zh_simplified", "炽型心 绿姬");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<緑子>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@:从你的能量区把精灵1张最多作为对象，将其出场。然后，你的精灵1只作为对象，直到回合结束时为止，其得到[[S枪兵]]。\n" +
                "@A $G1 :祈望%G0&E4张以上@0直到下一个对战对手的回合结束时为止，你的全部的精灵的力量+5000，这些得到[[暗影（力量在这只精灵的力量的一半以下的精灵）]]。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIDORIKO);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(4);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }


    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.MIDORIKO).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Wanna");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromEner().playable()).get();
            putOnField(target);
            
            target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilitySLancer(), ChronoDuration.turnEnd());
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                forEachSIGNIOnField(getOwner(), cardIndex -> {
                    gainPower(cardIndex, 5000, ChronoDuration.nextTurnEnd(getOpponent()));
                    attachAbility(cardIndex, new StockAbilityShadow(this::onAttachedStockEffAddCond, () -> DescriptionParser.formatNumber(cardIndex.getIndexedInstance().getPower().getValue().intValue()/2)), ChronoDuration.nextTurnEnd(getOpponent()));
                });
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getPower().getValue() <= getPower().getValue()/2 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
