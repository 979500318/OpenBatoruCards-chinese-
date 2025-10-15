package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R2_LancelotCrimsonGeneral extends Card {
    
    public SIGNI_R2_LancelotCrimsonGeneral()
    {
        setImageSets("WXDi-D03-013", "WXDi-D07-015", "SPDi10-02");
        
        setOriginalName("紅将　ランスロット");
        setAltNames("コウショウランスロット Koushou Ransurotto");
        setDescription("jp",
                "@E %X：対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Lancelot, Crimson General");
        setDescription("en",
                "@E %X: Vanish target SIGNI on your opponent's field with power 5000 or less."
        );
        
        setName("en_fan", "Lancelot, Crimson General");
        setDescription("en_fan",
                "@E %X: Target 1 of your opponent's SIGNI with power 5000 or less, and banish it."
        );
        
		setName("zh_simplified", "红将 兰斯洛特");
        setDescription("zh_simplified", 
                "@E %X:对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            banish(target);
        }
    }
}
