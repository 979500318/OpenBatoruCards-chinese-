package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_R_FessoneMagicConflagation extends Card {

    public SPELL_R_FessoneMagicConflagation()
    {
        setImageSets("WXDi-P14-TK02");

        setOriginalName("フェゾーネマジック・烈火");
        setAltNames("フェゾーネマジックレッカ Fezoone Majikku Rekka");
        setDescription("jp",
                "((クラフトであるスペルは、使用後にゲームから除外される))\n\n" +
                "対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Fesonne Magic: Blazing Red");
        setDescription("en",
                "((This spell can be used from your LRIG Deck during your main phase.))\n" +
                "((Spells that are Craft are removed from the game after they are used.))\n\n" +
                "Vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Fessone Magic - Conflagation");
        setDescription("en_fan",
                "((This craft is excluded from the game after use))\n\n" +
                "Target 1 of your opponent's SIGNI with power 12000 or less, and banish it."
        );

		setName("zh_simplified", "音乐节魔术·烈火");
        setDescription("zh_simplified", 
                "对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)));
        }
        private void onSpellEff()
        {
            banish(spell.getTarget());
        }
    }
}
