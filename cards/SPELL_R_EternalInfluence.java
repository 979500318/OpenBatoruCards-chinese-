package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class SPELL_R_EternalInfluence extends Card {
    
    public SPELL_R_EternalInfluence()
    {
        setImageSets("WXDi-P03-063");
        
        setOriginalName("永劫の影響");
        setAltNames("エイゴウノエイキョウ Eigou no Eikyuu");
        setDescription("jp",
                "このスペルを使用する際、使用コストとして追加でエクシード４を支払ってもよい。\n\n" +
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。追加でエクシード４を支払っていた場合、代わりに対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Eternal Influence");
        setDescription("en",
                "As you use this spell, you may pay Exceed 4 as an additional use cost. \n\n" +
                "Vanish target SIGNI on your opponent's field with power 8000 or less. If you paid the Exceed 4, instead vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Eternal Influence");
        setDescription("en_fan",
                "While using this spell, you may pay an additional @[Exceed 4]@ for its use cost.\n\n" +
                "Target 1 of your opponent's SIGNI with power 8000 or less, and banish it. If you additionally paid @[Exceed 4]@, instead target 1 of your opponent's SIGNI, and banish it."
        );
        
		setName("zh_simplified", "永劫的影响");
        setDescription("zh_simplified", 
                "这张魔法使用时，可以作为使用费用追加把@[超越 4]@支付。（从你的分身的下面把牌合计4张放置到分身废弃区）\n" +
                "对战对手的力量8000以下的精灵1只作为对象，将其破坏。追加把@[超越 4]@支付过的场合，作为替代，对战对手的精灵1只作为对象，将其破坏。\n"
        );

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
            spell.setAdditionalCost(new ExceedCost(4));
        }
        
        private void onSpellEffPreTarget()
        {
            TargetFilter filter = new TargetFilter(TargetHint.BANISH).OP().SIGNI();
            if(!spell.hasPaidAdditionalCost()) filter = filter.withPower(0,8000);
            
            spell.setTargets(playerTargetCard(filter));
        }
        private void onSpellEff()
        {
            banish(spell.getTarget());
        }
    }
}
