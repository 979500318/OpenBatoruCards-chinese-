package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_R1_FirecrackerDissonaSmallGun extends Card {

    public SIGNI_R1_FirecrackerDissonaSmallGun()
    {
        setImageSets("WXDi-P12-065");

        setOriginalName("小砲　バクチク//ディソナ");
        setAltNames("ショウホウバクチクディソナ Shouhou Bakuchiku Disona");
        setDescription("jp",
                "@E @[手札から#Sのカードを１枚捨てる]@：対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Firecracker//Dissona, Small Cannon");
        setDescription("en",
                "@E @[Discard a #S card]@: Vanish target SIGNI on your opponent's field with power 5000 or less."
        );
        
        setName("en_fan", "Firecracker//Dissona, Small Gun");
        setDescription("en_fan",
                "@E @[Discard 1 #S @[Dissona]@ card from your hand]@: Target 1 of your opponent's SIGNI with power 5000 or less, and banish it."
        );

		setName("zh_simplified", "小炮 鞭炮//失调");
        setDescription("zh_simplified", 
                "@E 从手牌把#S的牌1张舍弃:对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
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
            
            registerEnterAbility(new DiscardCost(new TargetFilter().dissona()), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            banish(target);
        }
    }
}
