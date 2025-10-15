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

public final class SPELL_W_FurenSlash extends Card {
    
    public SPELL_W_FurenSlash()
    {
        setImageSets("WXDi-P00-048");
        
        setOriginalName("フレン・スラッシュ");
        setAltNames("フレンスラッシュ Furen Surasshu");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、それを手札に戻す。" +
                "~#：対戦相手のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Furen Slash");
        setDescription("en",
                "Return target SIGNI on your opponent's field to its owner's hand." +
                "~#Return target SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Furen Slash");
        setDescription("en_fan",
                "Target 1 of your opponent's SIGNI, and return it to their hand." +
                "~#Target 1 of your opponent's SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "赋敛·斩击");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，将其返回手牌。" +
                "~#对战对手的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2) + Cost.colorless(3));
        
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()));
        }
        private void onSpellEff()
        {
            addToHand(spell.getTarget());
        }
        
        private void onLifeBurstEff()
        {
            onSpellEffPreTarget();
            onSpellEff();
        }
    }
}
