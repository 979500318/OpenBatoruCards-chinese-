package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_R1_HanayoSakura extends Card {
    
    public LRIGA_R1_HanayoSakura()
    {
        setImageSets("WXDi-P06-025");
        
        setOriginalName("花代・桜");
        setAltNames("ハナヨサクラ");
        setDescription("jp",
                "@E：対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E：【エナチャージ１】"
        );
        
        setName("en", "Hanayo, Sakura");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 5000 or less.\n" +
                "@E: [[Ener Charge 1]]"
        );
        
        setName("en_fan", "Hanayo Sakura");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 5000 or less, and banish it.\n" +
                "@E: [[Ener Charge 1]]"
        );
        
		setName("zh_simplified", "花代·樱");
        setDescription("zh_simplified", 
                "@E :对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n" +
                "@E :[[能量填充1]]\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.HANAYO);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            banish(target);
        }
        private void onEnterEff2()
        {
            enerCharge(1);
        }
    }
}
