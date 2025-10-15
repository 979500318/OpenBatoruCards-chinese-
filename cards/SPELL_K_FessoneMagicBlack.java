package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_FessoneMagicBlack extends Card {

    public SPELL_K_FessoneMagicBlack()
    {
        setImageSets("WXDi-P14-TK05");

        setOriginalName("フェゾーネマジック・ブラック");
        setAltNames("フェゾーネマジックブラック Fezoone Majikku Burakku");
        setDescription("jp",
                "((クラフトであるスペルは、使用後にゲームから除外される))\n\n" +
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Fesonne Magic: Black");
        setDescription("en",
                "((This spell can be used from your LRIG Deck during your main phase.))\n" +
                "((Spells that are Craft are removed from the game after they are used.))\n\n" +
                "Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Fessone Magic - Black");
        setDescription("en_fan",
                "((This craft is excluded from the game after use))\n\n" +
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "音乐节魔术·漆黑");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()));
        }
        private void onSpellEff()
        {
            gainPower(spell.getTarget(), -8000, ChronoDuration.turnEnd());
        }
    }
}
