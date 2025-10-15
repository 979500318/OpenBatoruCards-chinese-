package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_G_PerformanceFlaw extends Card {
    
    public SPELL_G_PerformanceFlaw()
    {
        setImageSets("WXDi-P00-072");
        
        setOriginalName("奏創");
        setAltNames("ソウソウ Sousou");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、それをエナゾーンに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、それをエナゾーンに置く。"
        );
        
        setName("en", "Creation Tone");
        setDescription("en",
                "Put target SIGNI on your opponent's field into its owner's Ener Zone." +
                "~#Put target SIGNI on your opponent's field into its owner's Ener Zone."
        );
        
        setName("en_fan", "Performance Flaw");
        setDescription("en_fan",
                "Target 1 of your opponent's SIGNI, and put it into the ener zone." +
                "~#Target 1 of your opponent's SIGNI, and put it into the ener zone."
        );
        
		setName("zh_simplified", "奏创");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，将其放置到能量区。" +
                "~#对战对手的精灵1只作为对象，将其放置到能量区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2) + Cost.colorless(3));
        
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
            
            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()));
        }
        private void onSpellEff()
        {
            putInEner(spell.getTarget());
        }
        
        private void onLifeBurstEff()
        {
            onSpellEffPreTarget();
            onSpellEff();
        }
    }
}
