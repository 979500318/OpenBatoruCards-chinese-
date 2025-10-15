package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SPELL_G_IlluminatedEarth extends Card {
    
    public SPELL_G_IlluminatedEarth()
    {
        setImageSets("WXDi-P05-077");
        setLinkedImageSets("WXDi-P05-040");
        
        setOriginalName("照地");
        setAltNames("ショウチ Shouchi");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のパワー8000以上のシグニ１体を対象とし、あなたのエナゾーンから＜天使＞のシグニ１枚をトラッシュに置き手札から＜天使＞のシグニを１枚捨てる。そうした場合、それをバニッシュする。\n" +
                "$$2あなたのエナゾーンから《翠天姫　ガイア》１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のレベル２以上のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Edenify");
        setDescription("en",
                "Choose one of the following.\n" +
                "$$1 Put an <<Angel>> SIGNI from your Ener Zone into your trash and discard an <<Angel>> SIGNI. If you do, vanish target SIGNI on your opponent's field with power 8000 or more.\n" +
                "$$2 Add target \"Gaia, Jade Angel Queen\" from your Ener Zone to your hand." +
                "~#Vanish target level two or more SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Illuminated Earth");
        setDescription("en_fan",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 8000 or more, and put 1 <<Angel>> SIGNI from your ener zone into the trash and discard 1 <<Angel>> SIGNI from your hand. If you do, banish it.\n" +
                "$$2 Target 1 \"Gaia, Verdant Angel Princess\" from your ener zone, and add it to your hand." +
                "~#Target 1 of your opponent's level 2 or higher SIGNI, and banish it."
        );
        
		setName("zh_simplified", "照地");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的力量8000以上的精灵1只作为对象，从你的能量区把<<天使>>精灵1张放置到废弃区且从手牌把<<天使>>精灵1张舍弃。这样做的场合，将其破坏。\n" +
                "$$2 从你的能量区把《翠天姫　ガイア》1张作为对象，将其加入手牌。" +
                "~#对战对手的等级2以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        
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
            spell.setModeChoice(1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(spell.getChosenModes() == 1 ? new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(8000,0) : new TargetFilter(TargetHint.HAND).own().SIGNI().withName("翠天姫　ガイア").fromEner()));
        }
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1)
            {
                if(spell.getTarget() != null &&
                   payAll(new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.ANGEL).fromEner()), new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.ANGEL))))
                {
                    banish(spell.getTarget());
                }
            } else {
                addToHand(spell.getTarget());
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(2,0)).get();
            banish(target);
        }
    }
}
