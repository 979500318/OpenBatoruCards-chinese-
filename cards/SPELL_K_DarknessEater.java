package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_DarknessEater extends Card {
    
    public SPELL_K_DarknessEater()
    {
        setImageSets("WXDi-P00-080");
        
        setOriginalName("ダークネス・イーター");
        setAltNames("ダークネスイーター Daakunesu Iitaa");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－12000する。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－12000する。"
        );
        
        setName("en", "Darkness Eater");
        setDescription("en",
                "Target SIGNI on your opponent's field gets --12000 power until end of turn." +
                "~#Target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Darkness Eater");
        setDescription("en_fan",
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --12000 power." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --12000 power."
        );
        
		setName("zh_simplified", "乌黑·噬神");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-12000。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2) + Cost.colorless(2));
        
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()));
        }
        private void onSpellEff()
        {
            gainPower(spell.getTarget(), -12000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            onSpellEffPreTarget();
            onSpellEff();
        }
    }
}
