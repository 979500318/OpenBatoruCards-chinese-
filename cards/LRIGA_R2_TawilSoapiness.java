package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_R2_TawilSoapiness extends Card {
    
    public LRIGA_R2_TawilSoapiness()
    {
        setImageSets("WXDi-P00-011");
        
        setOriginalName("タウィル＝シャボネス");
        setAltNames("タウィルシャボネス Tauiru Shabonesu");
        setDescription("jp",
                "@E：対戦相手のパワー１２０００以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %X %X %X %X %X：対戦相手のパワー８０００以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Tawil =Bubbles=");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 12000 or less.\n" +
                "@E %X %X %X %X %X: Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Tawil-Soapiness");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 12000 or less, and banish it.\n" +
                "@E %X %X %X %X %X: Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );
        
		setName("zh_simplified", "塔维尔=泡影");
        setDescription("zh_simplified", 
                "@E :对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n" +
                "@E %X %X %X %X %X:对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAWIL);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.RED);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            registerEnterAbility(new EnerCost(Cost.colorless(5)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
