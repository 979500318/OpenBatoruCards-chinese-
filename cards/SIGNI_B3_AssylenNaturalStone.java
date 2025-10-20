package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B3_AssylenNaturalStone extends Card {
    
    public SIGNI_B3_AssylenNaturalStone()
    {
        setImageSets("WXDi-D01-016");
        
        setOriginalName("羅石　アシレン");
        setAltNames("ラセキアシレン 	Raseki Ashiren");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニのパワーが１５０００以上の場合、対戦相手は手札を１枚捨てる。２００００以上の場合、代わりに対戦相手の手札を１枚見ないで選び、捨てさせる。\n" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );
        
        setName("en", "Assylen, Natural Crystal");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if its power is 15000 or more, your opponent discards a card. If its power is 20000 or more, your opponent instead discards a card at random." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Assylen, Natural Stone");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if this SIGNI's power is 15000 or more, your opponent discards 1 card from their hand. If it is 20000 or more, instead choose 1 card from your opponent's without looking, and discard it." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );
        
		setName("zh_simplified", "罗石 亚述水晶透镜");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这只精灵的力量在15000以上的场合，对战对手把手牌1张舍弃。20000以上的场合，作为替代，不看对战对手的手牌选1张，舍弃。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            double power = getCardIndex().getIndexedInstance().getPower().getValue();
            
            if(power >= 20000)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            } else if(power >= 15000)
            {
                discard(getOpponent(), 1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(cardIndex);
            freeze(cardIndex);
            
            draw(1);
        }
    }
}
