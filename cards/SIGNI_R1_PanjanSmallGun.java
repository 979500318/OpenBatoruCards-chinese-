package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.CardIndexSnapshot;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_R1_PanjanSmallGun extends Card {

    public SIGNI_R1_PanjanSmallGun()
    {
        setImageSets("WX24-P2-069");

        setOriginalName("小砲　パンジャン");
        setAltNames("ショウホウパンジャン Shouhou Panjan");
        setDescription("jp",
                "@E @[アップ状態のルリグ１体をダウンする]@：この方法でダウンしたルリグと共通する色を持つ対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Panjan, Small Gun");
        setDescription("en",
                "@E @[Down 1 of your upped LRIG]@: Target 1 of your opponent's SIGNI with power 3000 or less that shares a common color with the LRIG downed this way, and banish it." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "小炮 潘加朱姆火箭车");
        setDescription("zh_simplified", 
                "@E 竖直状态的分身1只横置:持有与这个方法横置的分身共通颜色的对战对手的力量3000以下的精灵1只作为对象，将其破坏。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new DownCost(new TargetFilter().upped().anyLRIG()), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000).withColor(((CardIndexSnapshot)getAbility().getCostPaidData().get()).getColor())).get();
            banish(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
