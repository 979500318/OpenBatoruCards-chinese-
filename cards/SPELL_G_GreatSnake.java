package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_G_GreatSnake extends Card {
    
    public SPELL_G_GreatSnake()
    {
        setImageSets("WXDi-P01-077");
        
        setOriginalName("大蛇");
        setAltNames("ダイジャ Daija");
        setDescription("jp",
                "対戦相手のパワー15000以上のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のパワー7000以上のシグニを１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Great Snake");
        setDescription("en",
                "Vanish target SIGNI on your opponent's field with power 15000 or more." +
                "~#Vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "Great Snake");
        setDescription("en_fan",
                "Target 1 of your opponent's SIGNI with power 15000 or more, and banish it." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );
        
		setName("zh_simplified", "大蛇");
        setDescription("zh_simplified", 
                "对战对手的力量15000以上的精灵1只作为对象，将其破坏。" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1));
        
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(15000,0)));
        }
        private void onSpellEff()
        {
            banish(spell.getTarget());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
