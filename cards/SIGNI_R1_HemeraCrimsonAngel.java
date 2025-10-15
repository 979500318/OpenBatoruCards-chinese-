package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R1_HemeraCrimsonAngel extends Card {
    
    public SIGNI_R1_HemeraCrimsonAngel()
    {
        setImageSets("WXDi-P05-053");
        
        setOriginalName("紅天　ヘーメラー");
        setAltNames("コウテンヘーメラー Kouten Heemeraa");
        setDescription("jp",
                "@E %R：あなたの場に＜天使＞のシグニが２体ある場合、対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。あなたの場に＜天使＞のシグニが３体以上ある場合、代わりに対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Hemera, Crimson Angel");
        setDescription("en",
                "@E %R: If there are two <<Angel>> SIGNI on your field, vanish target SIGNI on your opponent's field with power 3000 or less. If there are three or more <<Angel>> SIGNI on your field, instead vanish target SIGNI on your opponent's field with power 5000 or less."
        );
        
        setName("en_fan", "Hemera, Crimson Angel");
        setDescription("en_fan",
                "@E %R: If there are 2 <<Angel>> SIGNI on your field, target 1 of your opponent's SIGNI with power 3000 or less, and banish it. If there are 3 or more <<Angel>> SIGNI on your field, instead target 1 of your opponent's SIGNI with power 5000 or less, and banish it."
        );
        
		setName("zh_simplified", "红天 赫墨拉");
        setDescription("zh_simplified", 
                "@E %R:你的场上的<<天使>>精灵在2只的场合，对战对手的力量3000以下的精灵1只作为对象，将其破坏。你的场上的<<天使>>精灵在3只以上的场合，作为替代，对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            int power = 0;
            int count = new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).getValidTargetsCount();
            if(count == 2) power = 3000;
            else if(count >= 3) power = 5000;
            
            if(power != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,power)).get();
                banish(target);
            }
        }
    }
}
