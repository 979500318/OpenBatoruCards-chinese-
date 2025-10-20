package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_GaghielAzureAngel extends Card {
    
    public SIGNI_B2_GaghielAzureAngel()
    {
        setImageSets("WXDi-P02-067");
        
        setOriginalName("蒼天　ガギエル");
        setAltNames("ソウテンガギエル Souten Gagieru");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、カードを１枚引き、手札を１枚捨てる。\n" +
                "@E：カードを１枚引き、手札を１枚捨てる。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );
        
        setName("en", "Gaghiel, Azure Angel");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, draw a card and discard a card.\n" +
                "@E: Draw a card and discard a card." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Gaghiel, Azure Angel");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, draw 1 card, and discard 1 card from your hand.\n" +
                "@E: Draw 1 card, and discard 1 card from your hand." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );
        
		setName("zh_simplified", "苍天 迦基尔");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，抽1张牌，手牌1张舍弃。\n" +
                "@E :抽1张牌，手牌1张舍弃。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            registerEnterAbility(this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            draw(1);
            discard(1);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
