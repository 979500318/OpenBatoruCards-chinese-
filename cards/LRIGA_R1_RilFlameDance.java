package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_R1_RilFlameDance extends Card {
    
    public LRIGA_R1_RilFlameDance()
    {
        setImageSets("WXDi-P08-028");
        
        setOriginalName("リル・炎舞");
        setAltNames("リルエンブ Riru Enbu");
        setDescription("jp",
                "@E：対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E：#C #Cを得る。"
        );
        
        setName("en", "Ril, Flame Dance");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 5000 or less.\n" +
                "@E: Gain #C #C."
        );
        
        setName("en_fan", "Ril Flame Dance");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 5000 or less, and banish it.\n" +
                "@E: Gain #C #C."
        );
        
		setName("zh_simplified", "莉露·炎舞");
        setDescription("zh_simplified", 
                "@E :对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n" +
                "@E :得到#C #C。\n" +
                "（玩家保留币的上限是5个）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.RIL);
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
            gainCoins(2);
        }
    }
}
