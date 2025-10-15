package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_W1_CodeArtFLashli extends Card {
    
    public SIGNI_W1_CodeArtFLashli()
    {
        setImageSets("WXDi-D04-012", "SPDi01-22", "SPDi38-05");
        
        setOriginalName("コードアート　Ｋイチュデ");
        setAltNames("コードアートケーイチュデ Koodo Aato Kee Ichude");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの上からカードを３枚見る。その中からスペル１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "F - Lite, Code: Art");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, look at the top three cards of your deck. Reveal a spell from among them and add it to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Code Art F Lashli");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, look at the top 3 cards of your deck. Reveal 1 spell from among them, and add it to your hand, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "必杀代号 强光电筒");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从你的牌组上面看3张牌。从中把魔法1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(1000);
        
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
        }
        
        private void onAutoEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().spell().fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
