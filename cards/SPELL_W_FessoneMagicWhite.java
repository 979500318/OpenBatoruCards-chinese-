package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardStateFlag;
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

public final class SPELL_W_FessoneMagicWhite extends Card {

    public SPELL_W_FessoneMagicWhite()
    {
        setImageSets("WXDi-P14-TK01");

        setOriginalName("フェゾーネマジック・ホワイト");
        setAltNames("フェゾーネマジックホワイト Fezoone Majikku Howaito");
        setDescription("jp",
                "((クラフトであるスペルは、使用後にゲームから除外される))\n\n" +
                "あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Fesonne Magic: White");
        setDescription("en",
                "((This spell can be used from your LRIG Deck during your main phase.))\n" +
                "((Spells that are Craft are removed from the game after they are used.))\n\n" +
                "Add target SIGNI with a #G from your trash to your hand."
        );

        setName("en_fan", "Fessone Magic - White");
        setDescription("en_fan",
                "((This craft is excluded from the game after use))\n\n" +
                "Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "音乐节魔术·纯白");
        setDescription("zh_simplified", 
                "从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()));
        }
        private void onSpellEff()
        {
            addToHand(spell.getTarget());
        }
    }
}
