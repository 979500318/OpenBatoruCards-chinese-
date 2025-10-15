package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class SIGNI_R1_FireTorchSmallTrap extends Card {

    public SIGNI_R1_FireTorchSmallTrap()
    {
        setImageSets("WX24-P3-066");

        setOriginalName("小罠　ファイヤートーチ");
        setAltNames("ショウビンファイヤートーチ Shoubin Faiyaa Toochi");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニと同じシグニゾーンに【マジックボックス】がある場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2対戦相手のパワー5000以下のシグニ１体を対象とし、このシグニと同じシグニゾーンにある【マジックボックス】１つを表向きにしトラッシュに置いてもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Fire Torch, Small Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is a [[Magic Box]] in the same zone as this SIGNI, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 2000 or less, and banish it.\n" +
                "$$2 Target 1 of your opponent's SIGNI with power 5000 or less, and you may put 1 [[Magic Box]] in the same zone as this SIGNI face-up into the trash. If you do, banish it."
        );

		setName("zh_simplified", "小罠 火舞");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，与这只精灵相同精灵区有[[魔术箱]]的场合，从以下的2种选1种。\n" +
                "$$1 对战对手的力量2000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 对战对手的力量5000以下的精灵1只作为对象，可以把与这只精灵相同精灵区的[[魔术箱]]1个表向并放置到废弃区。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            DataTable<CardIndex> data = new TargetFilter().own().withUnderType(CardUnderType.ZONE_MAGIC_BOX).fromSafeLocation(getCardIndex().getLocation()).getExportedData();
            if(!data.isEmpty())
            {
                if(playerChoiceMode() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
                    banish(target);
                } else {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
                    
                    if(target != null && playerChoiceActivate())
                    {
                        trash(data);
                        
                        banish(target);
                    }
                }
            }
        }
    }
}
