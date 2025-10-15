package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataSIGNIClass;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.SpellAbility;

import java.util.List;

public final class SPELL_G_Orchestra extends Card {

    public SPELL_G_Orchestra()
    {
        setImageSets("WX25-P1-097");

        setOriginalName("楽団");
        setAltNames("ガクダン Gakudan");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1あなたのエナゾーンから緑のシグニ１枚を対象とし、それを場に出す。\n" +
                "$$2あなたのエナゾーンから共通するクラスを持たない緑のシグニ２枚を対象とし、それらを場に出す。" +
                "~#：【エナチャージ１】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Orchestra");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 green SIGNI from your ener zone, and put it onto the field.\n" +
                "$$2 Target 2 green SIGNI that do not share a common class from your ener zone, and put them onto the field." +
                "~#[[Ener Charge 1]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "乐团");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 从你的能量区把绿色的精灵1张作为对象，将其出场。\n" +
                "$$2 从你的能量区把不持有共通类别的绿色的精灵2张作为对象，将这些出场。" +
                "~#[[能量填充1]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            if(spell.getChosenModes() == 1)
            {
                spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.GREEN).fromEner().playable()));
            } else {
                TargetFilter filter = new TargetFilter(TargetHint.TRASH).own().SIGNI().withColor(CardColor.GREEN).fromEner();
                
                if(CardDataSIGNIClass.containsTwoWithoutCommonClass(filter.getExportedData()))
                {
                    spell.setTargets(playerTargetCard(2, filter, this::onSpellEffPreTargetCond));
                }
            }
        }
        private boolean onSpellEffPreTargetCond(List<CardIndex> pickedCards)
        {
            return pickedCards.isEmpty() || (pickedCards.size() == 2 && !pickedCards.getFirst().getIndexedInstance().getSIGNIClass().matches(pickedCards.getLast().getIndexedInstance().getSIGNIClass()));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                if(spell.getChosenModes() == 1)
                {
                    putOnField(spell.getTarget());
                } else {
                    putOnField(spell.getTargets());
                }
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

