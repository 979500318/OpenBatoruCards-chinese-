package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.ModifiableAddedValueModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K2_IbukiTanga extends Card {

    public SIGNI_K2_IbukiTanga()
    {
        setImageSets("WX25-CP1-089");

        setOriginalName("丹花イブキ");
        setAltNames("タンガイブキ Tanga Ibuki");
        setDescription("jp",
                "@E @[手札から＜ブルアカ＞のカードを２枚捨てる]@：あなたの他の＜ブルアカ＞のシグニ１体を対象とし、このターン、それの効果によって対戦相手のシグニのパワーが－（マイナス）される場合、代わりに２倍－（マイナス）される。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#あなたのトラッシュから＜ブルアカ＞のシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Tanga Ibuki");

        setName("en_fan", "Ibuki Tanga");
        setDescription("en",
                "@E @[Discard 2 <<Blue Archive>> cards from your hand]@: Target 1 of your other <<Blue Archive>> SIGNI, and this turn, if the power of your opponent's SIGNI would be -- (minus) by its effect, it gets -- (minus) by twice as much instead." +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#Target 1 <<Blue Archive>> SIGNI from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "丹花伊吹");
        setDescription("zh_simplified", 
                "@E 从手牌把<<ブルアカ>>牌2张舍弃:你的其他的<<ブルアカ>>精灵1只作为对象，这个回合，因为其的效果把对战对手的精灵的力量-（减号）的场合，作为替代，2倍-（减号）。\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#从你的废弃区把<<ブルアカ>>精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(2, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff);

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE)).get();

            if(target != null)
            {
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(),
                    new ModifiableAddedValueModifier<>(cardIndex -> cardIndex.getIndexedInstance().getPower(), (mod, addedValue) ->
                        addedValue < 0 && mod.getSourceAbility().getSourceCardIndex() == target ? addedValue * 2 : addedValue
                    )
                );
                attachedConst.setForcedModifierUpdate(mod ->
                    mod instanceof PowerModifier && mod.getSourceAbility().getSourceCardIndex() == target
                );
                
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                attachPlayerAbility(getOwner(), attachedConst, record);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash()).get();
            
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
