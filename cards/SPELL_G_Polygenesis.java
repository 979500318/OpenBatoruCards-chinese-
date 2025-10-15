package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_G_Polygenesis extends Card {
    
    public SPELL_G_Polygenesis()
    {
        setImageSets("WXDi-D01-021");
        
        setOriginalName("複成");
        setAltNames("フクセイ Fukusei");
        setDescription("jp",
                "あなたのエナゾーンから緑のシグニ１枚を対象とし、それを手札に加える。あなたの場に＜アンシエント・サプライズ＞のルリグが３体いる場合、代わりにあなたのエナゾーンからシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Polygenesis");
        setDescription("en",
                "Add target green SIGNI from your Ener Zone to your hand. If there are three <<Ancient Surprise>> LRIG on your field, instead add target SIGNI from your Ener Zone to your hand." +
                "~#Vanish target upped SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Polygenesis");
        setDescription("en_fan",
                "Target 1 green SIGNI from your ener zone, and add it to your hand. If you have 3 <<Ancient Surprise>> LRIGs on your field, instead target 1 SIGNI from your ener zone, and add it to your hand." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );
        
		setName("zh_simplified", "复成");
        setDescription("zh_simplified", 
                "从你的能量区把绿色的精灵1张作为对象，将其加入手牌。你的场上的<<アンシエント・サプライズ>>分身在3只的场合，作为替代，从你的能量区把精灵1张作为对象，将其加入手牌。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        
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
            TargetFilter filter = new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner();
            if(!isLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE)) filter = filter.withColor(CardColor.GREEN);
            
            spell.setTargets(playerTargetCard(filter));
        }
        private void onSpellEff()
        {
            addToHand(spell.getTarget());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
