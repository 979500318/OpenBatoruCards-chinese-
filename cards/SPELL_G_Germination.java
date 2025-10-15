package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_G_Germination extends Card {

    public SPELL_G_Germination()
    {
        setImageSets("WXK01-100");

        setOriginalName("萌芽");
        setAltNames("ホウガ Houga");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1あなたのエナゾーンからシグニ１枚を対象とし、それを手札に加える。\n" +
                "$$2【エナチャージ２】"
        );

        setName("en", "Germination");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 SIGNI from your ener zone, and add it to your hand.\n" +
                "$$2 [[Ener Charge 2]]"
        );

		setName("zh_simplified", "萌芽");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 从你的能量区把精灵1张作为对象，将其加入手牌。\n" +
                "$$2 [[能量填充2]]\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            spell.setModeChoice(1);
        }
        private void onSpellEffPreTarget()
        {
            if(spell.getChosenModes() == 1)
            {
                spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()));
            }
        }
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1)
            {
                addToHand(spell.getTarget());
            } else {
                enerCharge(2);
            }
        }
    }
}
