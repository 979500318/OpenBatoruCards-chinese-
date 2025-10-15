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

public final class SPELL_W_RebirthReturn extends Card {
    
    public SPELL_W_RebirthReturn()
    {
        setImageSets("WXDi-P04-052");
        
        setOriginalName("リバース・リターン");
        setAltNames("リバースリターン Ribaasu Ritaan");
        setDescription("jp",
                "対戦相手のレベル２以下のシグニ１体を対象とし、それを手札に戻す。対戦相手の手札を１枚見ないで選び、捨てさせる。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );
        
        setName("en", "Rebirth Return");
        setDescription("en",
                "Return target level two or less SIGNI on your opponent's field to its owner's hand. Your opponent discards a card at random." +
                "~#Choose one -- \n$$1 Return target upped SIGNI on your opponent's field to its owner's hand. \n$$2 Your opponent discards a card at random."
        );
        
        setName("en_fan", "Rebirth Return");
        setDescription("en_fan",
                "Target 1 of your opponent's level 2 or lower SIGNI, and return it to their hand. Choose 1 card from your opponent's hand without looking, and discard it." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and return it to their hand.\n" +
                "$$2 Choose 1 card from your opponent's hand without looking, and discard it."
        );
        
		setName("zh_simplified", "返航·宣告");
        setDescription("zh_simplified", 
                "对战对手的等级2以下的精灵1只作为对象，将其返回手牌。不看对战对手的手牌选1张，舍弃。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 不看对战对手的手牌选1张，舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1) + Cost.color(CardColor.BLUE, 1) + Cost.colorless(1));
        
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(0,2)));
        }
        private void onSpellEff()
        {
            addToHand(spell.getTarget());
            
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
                addToHand(target);
            } else {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }
    }
}
