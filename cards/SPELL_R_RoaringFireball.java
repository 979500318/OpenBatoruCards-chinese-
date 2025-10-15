package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_R_RoaringFireball extends Card {
    
    public SPELL_R_RoaringFireball()
    {
        setImageSets("WXDi-D03-021");
        
        setOriginalName("轟音の炎球");
        setAltNames("ゴウオンノエンキュウ Gouon no Enkyuu");
        setDescription("jp",
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。あなたの場に＜Ｎｏ　Ｌｉｍｉｔ＞のルリグが３体いる場合、代わりに対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Deafening Inferno");
        setDescription("en",
                "Vanish target SIGNI on your opponent's field with power 8000 or less. If you have three <<No Limit>> LRIG on your field, instead vanish target SIGNI on your opponent's field with power 10000 or less." +
                "~#Vanish target upped SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Roaring Fireball");
        setDescription("en_fan",
                "Target 1 of your opponent's SIGNI with power 8000 or less, and banish it. If there are 3 <<No Limit>> LRIGs on your field, instead target 1 of your opponent's SIGNI with power 10000 or less, and banish it." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );
        
		setName("zh_simplified", "轰音的炎球");
        setDescription("zh_simplified", 
                "对战对手的力量8000以下的精灵1只作为对象，将其破坏。你的场上的<<NoLimit>>分身在3只的场合，作为替代，对战对手的力量10000以下的精灵1只作为对象，将其破坏。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, isLRIGTeam(CardLRIGTeam.NO_LIMIT) ? 10000 : 8000)));
        }
        private void onSpellEff()
        {
            banish(spell.getTarget());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
