package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_B_BLUEOPENER extends Card {
    
    public SPELL_B_BLUEOPENER()
    {
        setImageSets("WXDi-P00-066");
        
        setOriginalName("BLUE OPENER");
        setAltNames("ブルーオープナー Buruu Oopunaa");
        setDescription("jp",
                "対戦相手のレベル２以下のシグニ１体を対象とし、それをデッキの一番下に置く。" +
                "~#：対戦相手のレベル２以下のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );
        
        setName("en", "Blue Opener");
        setDescription("en",
                "Put target level two or less SIGNI on your opponent's field on the bottom of its owner's deck." +
                "~#Put target level two or less SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "BLUE OPENER");
        setDescription("en_fan",
                "Target 1 of your opponent's level 2 or lower SIGNI, and put it on the bottom of their deck." +
                "~#Target 1 of your opponent's level 2 or lower SIGNI, and put it on the bottom of their deck."
        );
        
		setName("zh_simplified", "BLUE OPENER");
        setDescription("zh_simplified", 
                "对战对手的等级2以下的精灵1只作为对象，将其放置到牌组最下面。" +
                "~#对战对手的等级2以下的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 3) + Cost.colorless(2));
        
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withLevel(0,2)));
        }
        private void onSpellEff()
        {
            returnToDeck(spell.getTarget(), DeckPosition.BOTTOM);
        }
        
        private void onLifeBurstEff()
        {
            onSpellEffPreTarget();
            onSpellEff();
        }
    }
}
