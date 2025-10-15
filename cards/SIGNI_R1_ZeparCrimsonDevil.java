package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_R1_ZeparCrimsonDevil extends Card {
    
    public SIGNI_R1_ZeparCrimsonDevil()
    {
        setImageSets("WXDi-P02-057");
        
        setOriginalName("紅魔　ゼパル");
        setAltNames("コウマゼパル Kouma Zeparu");
        setDescription("jp",
                "@E @[他の赤のシグニ１体を場からトラッシュに置く]@：対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Zepar, Crimson Evil");
        setDescription("en",
                "@E @[Put another red SIGNI on your field into its owner's trash]@: Vanish target SIGNI on your opponent's field with power 3000 or less."
        );
        
        setName("en_fan", "Zepar, Crimson Devil");
        setDescription("en_fan",
                "@E @[Put 1 of your other red SIGNI into the trash]@: Target 1 of your opponent's SIGNI with power 3000 or less, and banish it."
        );
        
		setName("zh_simplified", "红魔 桀派");
        setDescription("zh_simplified", 
                "@E 其他的红色的精灵1只从场上放置到废弃区:对战对手的力量3000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new TrashCost(new TargetFilter().SIGNI().withColor(CardColor.RED).except(cardId)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
            banish(target);
        }
    }
}
