package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B3_SaprolegniaNaturalPaleBacteria extends Card {
    
    public SIGNI_B3_SaprolegniaNaturalPaleBacteria()
    {
        setImageSets("WXDi-P08-067");
        
        setOriginalName("羅淡菌　ミズカビ");
        setAltNames("ラタンキンミズカビ Ratankin Mizukabi");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、それをダウンし凍結する。\n" +
                "$$2対戦相手の手札を１枚見ないで選び、捨てさせる。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );
        
        setName("en", "Saprolegnia, Natural Pale Bacteria");
        setDescription("en",
                "@U: When this SIGNI is vanished, choose one of the following.\n" +
                "$$1 Down target SIGNI on your opponent's field and freeze it.\n" +
                "$$2 Your opponent discards a card at random." +
                "~#Down target SIGNI on your opponent's field and freeze it. Your opponent discards a card at random."
        );
        
        setName("en_fan", "Saprolegnia, Natural Pale Bacteria");
        setDescription("en_fan",
                "@U: When this SIGNI is banished, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and down and freeze it.\n" +
                "$$2 Choose 1 card from your opponent's hand without looking, and discard it." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Choose 1 card from your opponent's hand without looking, and discard it."
        );
        
		setName("zh_simplified", "罗淡菌 水霉菌");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，从以下的2种选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，将其#D并冻结。\n" +
                "$$2 不看对战对手的手牌选1张，舍弃。" +
                "~#对战对手的精灵1只作为对象，将其#D并冻结。不看对战对手的手牌选1张，舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
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
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
                down(target);
                freeze(target);
            } else {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
    }
}
