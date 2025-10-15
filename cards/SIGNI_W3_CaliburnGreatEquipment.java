package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W3_CaliburnGreatEquipment extends Card {

    public SIGNI_W3_CaliburnGreatEquipment()
    {
        setImageSets("WX24-D1-19");

        setOriginalName("大装　カリバン");
        setAltNames("タイソウカリバン Daisou Kariban");
        setDescription("jp",
                "@A %W #D：対戦相手のパワー8000以下のシグニ１体を対象とし、それを手札に戻す。あなたのライフクロスが２枚以下の場合、代わりに対戦相手のパワー10000以下のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Caliburn, Great Equipment");
        setDescription("en",
                "@A %W #D: Target 1 of your opponent's SIGNI with power 8000 or less, and return it to their hand. If you have 2 or less life cloth, instead target 1 of your opponent's SIGNI with power 10000 or less, and return it to their hand."
        );

		setName("zh_simplified", "大装 石中剑");
        setDescription("zh_simplified", 
                "@A %W#D:对战对手的力量8000以下的精灵1只作为对象，将其返回手牌。你的生命护甲在2张以下的场合，作为替代，对战对手的力量10000以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.WHITE, 1)), new DownCost()), this::onActionEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0, getLifeClothCount(getOwner()) > 2 ? 8000 : 10000)).get();
            addToHand(target);
        }
    }
}
