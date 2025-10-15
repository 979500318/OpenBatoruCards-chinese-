package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_B_PRAYING extends Card {

    public SPELL_B_PRAYING()
    {
        setImageSets("WXK01-090");

        setOriginalName("PRAYING");
        setAltNames("プレイング Pureingu");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1カードを２枚引き、手札を１枚捨てる。\n" +
                "$$2対戦相手は手札を１枚捨てる。"
        );

        setName("en", "PRAYING");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Draw 2 cards, and discard 1 card from your hand.\n" +
                "$$2 Your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "PRAYING");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 抽2张牌，手牌1张舍弃。\n" +
                "$$2 对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));

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

            spell = registerSpellAbility(this::onSpellEff);
            spell.setModeChoice(1);
        }
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1)
            {
                draw(2);
                discard(1);
            } else {
                discard(getOpponent(), 1);
            }
        }
    }
}
