package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.AbilityCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityCostModifier;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class LRIGA_W2_RememberRestraint extends Card {

    public LRIGA_W2_RememberRestraint()
    {
        setImageSets("WXDi-P15-033");

        setOriginalName("リメンバ・リストレイント");
        setAltNames("リメンバリストレイント Rimenba Risutoreinto");
        setDescription("jp",
                "@E：対戦相手のルリグ１体を対象とし、それを凍結する。\n" +
                "@E：次の対戦相手のターン終了時まで、このルリグは@>@C：対戦相手のカードの@A能力の使用コストは%X増える。@@を得る。"
        );

        setName("en", "Remember Restraint");
        setDescription("en",
                "@E: Freeze target LRIG on your opponent's field.\n@E: This LRIG gains@>@C: Use costs of the @A abilities of your opponent's cards are increased by %X.@@until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Remember Restraint");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's LRIG, and freeze it.\n" +
                "@E: Until the end of your opponent's next turn, this LRIG gains:" +
                "@>@C: The use costs of the @A abilities of your opponent's cards are increased by %X."
        );

		setName("zh_simplified", "忆·束缚");
        setDescription("zh_simplified", 
                "@E :对战对手的分身1只作为对象，将其冻结。\n" +
                "@E :直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@C 对战对手的牌的@A能力的使用费用增%X。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
            freeze(target);
        }
        
        private void onEnterEff2()
        {
            ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().OP().anyLocation(),
                new AbilityCostModifier(this::onAttachedConstEffModGetSample1, new CostModifier(this::onAttachedConstEffModGetSample2, ModifierMode.INCREASE))
            );
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private boolean onAttachedConstEffModGetSample1(Ability ability)
        {
            return ability instanceof ActionAbility;
        }
        private AbilityCost onAttachedConstEffModGetSample2()
        {
            return new EnerCost(Cost.colorless(1));
        }
    }
}
