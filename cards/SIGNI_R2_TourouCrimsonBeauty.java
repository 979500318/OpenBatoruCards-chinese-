package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_R2_TourouCrimsonBeauty extends Card {

    public SIGNI_R2_TourouCrimsonBeauty()
    {
        setImageSets("WX24-P3-071");

        setOriginalName("紅美　トウロウ");
        setAltNames("コウビ トウロウ Koubi Tourou");
        setDescription("jp",
                "@A #D @[エナゾーンから＜美巧＞のシグニ１枚をトラッシュに置く]@：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Tourou, Crimson Beauty");
        setDescription("en",
                "@A #D @[Put 1 <<Beautiful Technique>> SIGNI from your ener zone into the trash]@: Target 1 of your opponent's SIGNI with power 8000 or less, and banish it." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "红美 灯笼");
        setDescription("zh_simplified", 
                "@A 横置从能量区把<<美巧>>精灵1张放置到废弃区:对战对手的力量8000以下的精灵1只作为对象，将其破坏。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        // Contributed by NebelTal
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new AbilityCostList(new DownCost(), new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.BEAUTIFUL_TECHNIQUE).fromEner())), this::onActionEff);

            registerLifeBurstAbility(this::onActionEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
