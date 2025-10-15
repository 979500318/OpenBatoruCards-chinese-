package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_B_SPINNINGHARMONY extends Card {
    
    public SPELL_B_SPINNINGHARMONY()
    {
        setImageSets("WXDi-P02-071");
        
        setOriginalName("SPINNING HARMONY");
        setAltNames("スピニングハーモニー Supiningu Haamonii");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、それを凍結する。カードを１枚引く。その後、対戦相手の場に凍結状態のシグニが３体以上ある場合、追加で対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );
        
        setName("en", "Spinning Harmony");
        setDescription("en",
                "Freeze target SIGNI on your opponent's field. Draw a card. Then, if there are three or more frozen SIGNI on your opponent's field, your opponent discards a card at random."
        );
        
        setName("en_fan", "SPINNING HARMONY");
        setDescription("en_fan",
                "Target 1 of your opponent's SIGNI, and freeze it. Draw 1 card. Then, if there are 3 or more frozen SIGNI on your opponent's field, additionally choose 1 card from your opponent's hand without looking, discard it."
        );
        
		setName("zh_simplified", "SPINNING HARMONY");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，将其冻结。抽1张牌。然后，对战对手的场上的冻结状态的精灵在3只以上的场合，追加不看对战对手的手牌选1张，舍弃。\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        
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
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()));
        }
        private void onSpellEff()
        {
            freeze(spell.getTarget());
            
            draw(1);
            
            if(new TargetFilter().OP().SIGNI().withState(CardStateFlag.FROZEN).getValidTargetsCount() >= 3)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }
    }
}
