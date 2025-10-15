package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_R_DestructiveFlameOfInsaneThoughts extends Card {

    public SPELL_R_DestructiveFlameOfInsaneThoughts()
    {
        setImageSets("WXK01-080");

        setOriginalName("狂想の滅炎");
        setAltNames("キョウソウノメツエン Kyousou no Metsuen");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2ターン終了時まで、あなたのすべてのシグニのパワーを＋3000する。"
        );

        setName("en", "Destructive Flame of Insane Thoughts");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 10000 or less, and banish it.\n" +
                "$$2 Until end of turn, all of your SIGNI get +3000 power."
        );

		setName("zh_simplified", "狂想的灭炎");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 直到回合结束时为止，你的全部的精灵的力量+3000。\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));

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
                spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)));
            }
        }
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1)
            {
                banish(spell.getTarget());
            } else {
                gainPower(getSIGNIOnField(getOwner()), 3000, ChronoDuration.turnEnd());
            }
        }
    }
}
