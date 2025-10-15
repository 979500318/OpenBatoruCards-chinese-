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

public final class SPELL_R_TwelveHoursOfTheWorldsBeginning extends Card {
    
    public SPELL_R_TwelveHoursOfTheWorldsBeginning()
    {
        setImageSets("WXDi-P00-057");
        
        setOriginalName("開闢の打刻");
        setAltNames("カイビャクノダコク Kaibyaku no Dakoku");
        setDescription("jp",
                "対戦相手のパワー１３０００以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のパワー１３０００以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Battle Beat");
        setDescription("en",
                "Vanish target SIGNI on your opponent's field with power 13000 or less." +
                "~#Vanish target SIGNI on your opponent's field with power 13000 or less."
        );
        
        setName("en_fan", "Twelve Hours of the World's Beginning");
        setDescription("en_fan",
                "Target 1 of your opponent's SIGNI with power 13000 or less, and banish it." +
                "~#Target 1 of your opponent's SIGNI with power 13000 or less, and banish it."
        );
        
		setName("zh_simplified", "开辟的打刻");
        setDescription("zh_simplified", 
                "对战对手的力量13000以下的精灵1只作为对象，将其破坏。" +
                "~#对战对手的力量13000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2) + Cost.colorless(2));
        
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,13000)));
        }
        private void onSpellEff()
        {
            banish(spell.getTarget());
        }
        
        private void onLifeBurstEff()
        {
            onSpellEffPreTarget();
            onSpellEff();
        }
    }
}
