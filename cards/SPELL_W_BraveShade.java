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
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.stock.StockAbilityCantAttack;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SPELL_W_BraveShade extends Card {
    
    public SPELL_W_BraveShade()
    {
        setImageSets("WXDi-P02-055");
        
        setOriginalName("ブレイブ・シェード");
        setAltNames("ブレイブシェード Bureibu Sheedo");
        setDescription("jp",
                "あなたのシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋5000し、それは能力を失い、[[シャドウ]]と@>@C：アタックできない。@@を得る。"
        );
        
        setName("en", "A Shade of Bravery");
        setDescription("en",
                "Until the end of your opponent's next end phase, target SIGNI on your field gets +5000 power, loses its abilities, and gains [[Shadow]] and@>@C: This SIGNI cannot attack."
        );
        
        setName("en_fan", "Brave Shade");
        setDescription("en_fan",
                "Target 1 of your SIGNI, and until the end of your opponent's next turn, it gets +5000 power, it loses its abilities, and it gains [[Shadow]] and:" +
                "@>@C: Can't attack."
        );
        
		setName("zh_simplified", "迎难·而上");
        setDescription("zh_simplified", 
                "你的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+5000，其的能力失去，得到[[暗影]]和\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                gainPower(spell.getTarget(), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
                
                disableAllAbilities(spell.getTarget(), AbilityGain.ALLOW, ChronoDuration.nextTurnEnd(getOpponent()));
                
                attachAbility(spell.getTarget(), new StockAbilityShadow(), ChronoDuration.nextTurnEnd(getOpponent()));
                
                attachAbility(spell.getTarget(), new StockAbilityCantAttack(), ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
    }
}
