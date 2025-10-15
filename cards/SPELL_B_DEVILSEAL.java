package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_B_DEVILSEAL extends Card {

    public SPELL_B_DEVILSEAL()
    {
        setImageSets("WDK02-020");

        setOriginalName("DEVIL SEAL");
        setAltNames("デビルシール Debiru Shiiru");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1あなたの＜悪魔＞のシグニ１体を対象とし、それを場からトラッシュに置く。そうした場合、対戦相手は手札を１枚捨てる。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "DEVIL SEAL");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Put 1 of your <<Devil>> SIGNI from the field into the trash. If you do, your opponent discards 1 card from their hand.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "DEVIL SEAL");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 你的<<悪魔>>精灵1只作为对象，将其从场上放置到废弃区。这样做的场合，对战对手把手牌1张舍弃。\n" +
                "$$2 [[能量填充1]]（你的牌组最上面的牌放置到能量区）\n"
        );

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.SPELL);
        setColor(CardColor.BLUE);

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
                spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.DEVIL)));
            }
        }
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1)
            {
                if(trash(spell.getTarget()))
                {
                    discard(getOpponent(), 1);
                }
            } else {
                enerCharge(1);
            }
        }
    }
}
