package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_R1_TawilHowling extends Card {
    
    public LRIGA_R1_TawilHowling()
    {
        setImageSets("WXDi-D01-006");
        
        setOriginalName("タウィル＝ハウリング");
        setAltNames("タウィルハウリング Tauiru Hauringu");
        setDescription("jp",
                "@E：対戦相手のパワー８０００以下のシグニ１体を対象とし、それをバニッシュする。カードを１枚引き、手札を１枚捨てる。"
        );
        
        setName("en", "Tawil =Screech=");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 8000 or less. Draw a card, and discard a card."
        );
        
        setName("en_fan", "Tawil-Howling");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 8000 or less, and banish it. Draw 1 card, and discard 1 card from your hand."
        );
        
		setName("zh_simplified", "塔维尔=呼啸");
        setDescription("zh_simplified", 
                "@E :对战对手的力量8000以下的精灵1只作为对象，将其破坏。抽1张牌，手牌1张舍弃。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAWIL);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.RED);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
            
            draw(1);
            discard(1);
        }
    }
}
