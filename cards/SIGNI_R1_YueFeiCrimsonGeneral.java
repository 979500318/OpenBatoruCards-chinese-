package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_R1_YueFeiCrimsonGeneral extends Card {
    
    public SIGNI_R1_YueFeiCrimsonGeneral()
    {
        setImageSets("WXDi-P01-054", "SPDi01-46");
        
        setOriginalName("紅将　ガクヒ");
        setAltNames("コウショウガクヒ Koushou Gakuhi");
        setDescription("jp",
                "@A #D：対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。このシグニのパワーが5000以上の場合、代わりに対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Yue Fei, Crimson General");
        setDescription("en",
                "@A #D: Vanish target SIGNI on your opponent's field with power 2000 or less. If this SIGNI's power is 5000 or more, instead vanish target SIGNI on your opponent's field with power 5000 or less."
        );
        
        setName("en_fan", "Yue Fei, Crimson General");
        setDescription("en_fan",
                "@A #D: Target 1 of your opponent's SIGNI with power 2000 or less, and banish it. If this SIGNI's power is 5000 or more, instead target 1 of your opponent's SIGNI with power 5000 or less, and banish it."
        );
        
		setName("zh_simplified", "红将 岳飞");
        setDescription("zh_simplified", 
                "@A 横置:对战对手的力量2000以下的精灵1只作为对象，将其破坏。这只精灵的力量在5000以上的场合，作为替代，对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
            
            registerActionAbility(new DownCost(), this::onActionEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,getCardIndex().getIndexedInstance().getPower().getValue() < 5000 ? 2000 : 5000)).get();
            banish(target);
        }
    }
}
