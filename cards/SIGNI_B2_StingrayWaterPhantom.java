package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_StingrayWaterPhantom extends Card {
    
    public SIGNI_B2_StingrayWaterPhantom()
    {
        setImageSets("WXDi-P03-069");
        
        setOriginalName("幻水　トビエイ");
        setAltNames("ゲンスイトビエイ Gensui Tobiei");
        setDescription("jp",
                "~#：対戦相手は自分のシグニ１体を選びデッキの一番下に置く。"
        );
        
        setName("en", "Tobijei, Phantom Aquatic Beast");
        setDescription("en",
                "~#Your opponent chooses a SIGNI on their field and puts it on the bottom of their deck."
        );
        
        setName("en_fan", "Stingray, Water Phantom");
        setDescription("en_fan",
                "~#Your opponent chooses 1 of their SIGNI, and returns it on the bottom of their deck."
        );
        
		setName("zh_simplified", "幻水 斑点鹞鲼");
        setDescription("zh_simplified", 
                "~#对战对手选自己的精灵1只放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BOTTOM).own().SIGNI()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
