package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.events.EventDamage;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.AbilityCopyModifier;
import open.batoru.data.ability.modifiers.CardNameModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class LRIG_G4_HitoeFourthFormula extends Card {

    public LRIG_G4_HitoeFourthFormula()
    {
        setImageSets("WX24-P4-021", "WX24-P4-021U");

        setOriginalName("熾式　一衣");
        setAltNames("シシキヒトエ Shishiki Hitoe");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜ひとえ＞と同じカード名としても扱い、そのルリグの@C能力を得る。\n" +
                "@E @[エクシード４]@：【エナチャージ３】をする。その後、あなたのエナゾーンからカードを１枚まで対象とし、それを手札に加える。\n" +
                "@A $G1 @[@|本当の気持ち|@]@ %G0：&E４枚以上@0次の対戦相手のターン終了時まで、このルリグは@>@C：あなたがダメージを受ける場合、代わりに%G %Xを支払ってもよい。@@を得る。"
        );

        setName("en", "Hitoe, Fourth Formula");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Hitoe>> in your LRIG trash, and gains that LRIG's @C abilities.\n" +
                "@E @[Exceed 4]@: [[Ener Charge 3]]. Then, target up to 1 card from your ener zone, and add it to your hand.\n" +
                "@A $G1 @[@|True Feelings|@]@ %G0: &E4 or more@0 Until the end of your opponent's next turn, this LRIG gains:" +
                "@>@C: If you would be damaged, you may instead pay %G %X."
        );

		setName("zh_simplified", "炽式 一衣");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<ひとえ>>相同牌名，得到那张分身的@C能力。\n" +
                "@E @[超越 4]@:[[能量填充3]]。然后，从你的能量区把牌1张最多作为对象，将其加入手牌。\n" +
                "@A $G1 :真正心意%G0&E4张以上@0直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@C :你受到伤害的场合，作为替代，可以支付%G%X。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HITOE);
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

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.HITOE).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof ConstantAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("True Feelings");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            enerCharge(3);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromEner()).get();
            addToHand(target);
        }
        
        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                    new OverrideAction(GameEventId.DAMAGE, OverrideScope.GLOBAL, OverrideFlag.NON_MANDATORY, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler))
                );
                attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return ((EventDamage)event).getPlayer() == getOwner() &&
                   EnerCost.canPayCost(sourceAbilityRC, Cost.color(CardColor.GREEN, 1) + Cost.colorless(1));
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addEnerPayAction(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1));
        }
    }
}
