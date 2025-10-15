package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_W_OverPursuit extends Card {
    
    public SPELL_W_OverPursuit()
    {
        setImageSets("WXDi-P08-055", "SPDi37-01");
        
        setOriginalName("オーバー・パシュート");
        setAltNames("オーバーパシュート Oobaa Pashuuto");
        setDescription("jp",
                "対戦相手のレベル１のシグニ１体を対象とし、それを手札に戻す。あなたの場に白のシグニが３体以上ある場合、【エナチャージ１】をする。"
        );
        
        setName("en", "Over Pursuit");
        setDescription("en",
                "Return target level one SIGNI on your opponent's field to its owner's hand. If there are three or more white SIGNI on your field, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Over Pursuit");
        setDescription("en_fan",
                "Target 1 of your opponent's level 1 SIGNI, and return it to their hand. If there are 3 or more white SIGNI on your field, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "过度·追击");
        setDescription("zh_simplified", 
                "对战对手的等级1的精灵1只作为对象，将其返回手牌。你的场上的白色的精灵在3只以上的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        
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
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(1)));
        }
        private void onSpellEff()
        {
            addToHand(spell.getTarget());
            
            if(new TargetFilter().own().SIGNI().withColor(CardColor.WHITE).getValidTargetsCount() >= 3)
            {
                enerCharge(1);
            }
        }
    }
}
