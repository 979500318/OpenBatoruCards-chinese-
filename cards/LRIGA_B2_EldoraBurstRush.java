package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.cost.CrushCost;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class LRIGA_B2_EldoraBurstRush extends Card {

    public LRIGA_B2_EldoraBurstRush()
    {
        setImageSets("WXDi-P12-035");

        setOriginalName("エルドラ！バースト・ラッシュ！");
        setAltNames("エルドラバーストラッシュ Erudora Baasuto Rasshu Eldora Burst");
        setDescription("jp",
                "@E：このターン、次にあなたのライフバーストが発動する場合、代わりにそのライフバーストは二度発動する。\n" +
                "@E @[ライフクロス１枚をクラッシュする]@：あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。"
        );

        setName("en", "Eldora! Burst Rush!");
        setDescription("en",
                "@E: The next time your Life Burst activates this turn, instead that Life Burst activates twice.\n@E @[Crush one of your Life Cloth]@: Shuffle your deck and add the top card of your deck to your Life Cloth.\n"
        );
        
        setName("en_fan", "Eldora! Burst Rush!");
        setDescription("en_fan",
                "@E: The next time your Life Burst would activate this turn, it is activated twice instead.\n" +
                "@E @[Crush 1 of your life cloth]@: Shuffle your deck and add the top card of your deck to life cloth."
        );

		setName("zh_simplified", "艾尔德拉！迸发·突袭！");
        setDescription("zh_simplified", 
                "@E :这个回合，下一次你的生命迸发发动的场合，作为替代，那个生命迸发二次发动。\n" +
                "@E 生命护甲1张击溃:你的牌组洗切把最上面的牌加入生命护甲。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ELDORA);
        setColor(CardColor.BLUE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new CrushCost(1), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                    new OverrideAction(GameEventId.ABILITY, OverrideScope.GLOBAL,OverrideFlag.MANDATORY | OverrideFlag.DONT_BLOCK, this::onAttachedConstEffModOverrideCond,this::onAttachedConstEffModOverrideHandler)
            ));

            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }

        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return isOwnCard(event.getSourceAbility().getSourceCardIndex()) && event.getSourceAbility() instanceof LifeBurstAbility;
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            CardAbilities.removePlayerAbility(sourceAbilityRC);

            list.getSourceEvent().getSourceAbility().getFlags().addValue(AbilityFlag.RESOLVE_TWICE);
        }

        private void onEnterEff2()
        {
            shuffleDeck();
            addToLifeCloth(1);
        }
    }
}


