package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_G_GrowWings extends Card {

    public SPELL_G_GrowWings()
    {
        setImageSets("WXDi-P12-082");

        setOriginalName("羽化");
        setAltNames("ウカ Uka");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1あなたの場に#Sのシグニがある場合、【エナチャージ１】をする。\n" +
                "$$2あなたのエナゾーンから#Sのシグニ１枚を対象とし、それを手札に加える。" +
                "~#：【エナチャージ１】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Emergence");
        setDescription("en",
                "Choose one of the following.\n$$1 If there is a #S SIGNI on your field, [[Ener Charge 1]].\n$$2 Add target #S SIGNI from your Ener Zone to your hand." +
                "~#[[Ener Charge 1]]. Then, add up to one target SIGNI from your Ener Zone to your hand or put it onto your field."
        );
        
        setName("en_fan", "Grow Wings");
        setDescription("en_fan",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 If there is a #S @[Dissona]@ SIGNI on your field, [[Ener Charge 1]].\n" +
                "$$2 Target 1 #S @[Dissona]@ SIGNI from your ener zone, and add it to your hand." +
                "~#[[Ener Charge 1]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "羽化");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 你的场上有#S的精灵的场合，[[能量填充1]]。\n" +
                "$$2 从你的能量区把#S的精灵1张作为对象，将其加入手牌。" +
                "~#[[能量填充1]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEff);
            spell.setModeChoice(1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1)
            {
                if(new TargetFilter().own().SIGNI().dissona().getValidTargetsCount() > 0)
                {
                    enerCharge(1);
                }
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().dissona().fromEner()).get();
                addToHand(target);
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromEner()).get();
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
