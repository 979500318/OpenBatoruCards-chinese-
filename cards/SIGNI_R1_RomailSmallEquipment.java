package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R1_RomailSmallEquipment extends Card {
    
    public SIGNI_R1_RomailSmallEquipment()
    {
        setImageSets("WXDi-D03-012", "SPDi01-28");
        
        setOriginalName("小装　ローメイル");
        setAltNames("ショウソウローメイル Shousou Roomeiru");
        setDescription("jp",
                "@E %X：対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Romail, Lightly Armed");
        setDescription("en",
                "@E %X: Vanish target SIGNI on your opponent's field with power 3000 or less."
        );
        
        setName("en_fan", "Romail, Small Equipment");
        setDescription("en_fan",
                "@E %X: Target 1 of your opponent's SIGNI with power 3000 or less, and banish it."
        );
        
		setName("zh_simplified", "小装 皇家铠");
        setDescription("zh_simplified", 
                "@E %X:对战对手的力量3000以下的精灵1只作为对象，将其破坏。\n" +
                "（@E能力的:的左侧是费用。你能选择不把费用支付，而不发动）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(1);
        setPower(2000);
        
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
            banish(target);
        }
    }
}
