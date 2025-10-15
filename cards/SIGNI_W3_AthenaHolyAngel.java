package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W3_AthenaHolyAngel extends Card {
    
    public SIGNI_W3_AthenaHolyAngel()
    {
        setImageSets("WXDi-D04-015");
        
        setOriginalName("聖天　アテナ");
        setAltNames("セイテンアテナ Seiten Atena");
        setDescription("jp",
                "@E %W %X %X：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Athena, Blessed Angel");
        setDescription("en",
                "@E %W %X %X: Add target SIGNI with a #G from your trash to your hand."
        );
        
        setName("en_fan", "Athena, Holy Angel");
        setDescription("en_fan",
                "@E %W %X %X: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "圣天 雅典娜");
        setDescription("zh_simplified", 
                "@E %W%X %X从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(2)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }
    }
}
