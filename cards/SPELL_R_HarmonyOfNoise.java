package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class SPELL_R_HarmonyOfNoise extends Card {
    
    public SPELL_R_HarmonyOfNoise()
    {
        setImageSets("WXDi-P02-063");
        
        setOriginalName("騒音の調和");
        setAltNames("ソウオンノチョウワ Souon no Chouwa");
        setDescription("jp",
                "あなたのレベル３の赤のシグニ１体を対象とし、ターン終了時まで、それは[[ダブルクラッシュ]]を得る。"
        );
        
        setName("en", "In Perfect Harmony");
        setDescription("en",
                "Target red level three SIGNI on your field gains [[Double Crush]] until end of turn."
        );
        
        setName("en_fan", "Harmony of Noise");
        setDescription("en_fan",
                "Target 1 of your level 3 red SIGNI, and until end of turn, it gains [[Double Crush]]."
        );
        
		setName("zh_simplified", "骚音的调和");
        setDescription("zh_simplified", 
                "你的等级3的红色的精灵1只作为对象，直到回合结束时为止，其得到[[双重击溃]]。\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withLevel(3).withColor(CardColor.RED)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null) attachAbility(spell.getTarget(), new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
        }
    }
}
