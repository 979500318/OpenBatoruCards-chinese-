package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SPELL_B_RANDOMBAD extends Card {
    
    public SPELL_B_RANDOMBAD()
    {
        setImageSets("WXDi-D05-021", "SPDi01-47");
        
        setOriginalName("RANDOM BAD");
        setAltNames("ランダムバッド Randamu Baddo");
        setDescription("jp",
                "カードを１枚引き、対戦相手は手札を１枚捨てる。あなたの場に＜うちゅうのはじまり＞のルリグが３体いる場合、代わりにカードを１枚引き、対戦相手の手札を１枚見ないで選び、捨てさせる。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "RANDOM DRAIN");
        setDescription("en",
                "Draw a card and your opponent discards a card. If you have three <<UCHU NO HAJIMARI>> LRIG on your field, instead draw a card and your opponent discards a card at random." +
                "~#Down target SIGNI on your opponent's field and freeze it. Your opponent discards a card."
        );
        
        setName("en_fan", "RANDOM BAD");
        setDescription("en_fan",
                "Draw 1 card, and your opponent discards 1 card from their hand. If there are 3 <<Universe's Beginning>> LRIGs on your field, instead draw 1 card, and choose 1 card from your opponent's hand without looking, and discard it." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "RANDOM BAD");
        setDescription("zh_simplified", 
                "抽1张牌，对战对手把手牌1张舍弃。你的场上的<<うちゅうのはじまり>>分身在3只的场合，作为替代，抽1张牌，不看对战对手的手牌选1张，舍弃。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerSpellAbility(this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            draw(1);
            
            if(isLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING))
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            } else {
                discard(getOpponent(), 1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            discard(getOpponent(), 1);
        }
    }
}
