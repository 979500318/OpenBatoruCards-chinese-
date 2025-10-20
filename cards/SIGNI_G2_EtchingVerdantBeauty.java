package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_G2_EtchingVerdantBeauty extends Card {

    public SIGNI_G2_EtchingVerdantBeauty()
    {
        setImageSets("WX24-P3-083");

        setOriginalName("翠美　エッチング");
        setAltNames("スイビエッチング Suibi Ecchingu");
        setDescription("jp",
                "@C：あなたのエナゾーンに＜美巧＞のシグニがあるかぎり、このシグニのパワーは＋2000される。\n" +
                "@A #D @[エナゾーンから＜美巧＞のシグニ１枚をトラッシュに置く]@：対戦相手のパワー5000以下のシグニ１体を対象とし、それをエナゾーンに置く。"
        );

        setName("en", "Etching, Verdant Beauty");
        setDescription("en",
                "@C: As long there is a <<Beautiful Technique>> SIGNI in your ener zone, this SIGNI gets +2000 power.\n" +
                "@A #D @[Put 1 <<Beautiful Technique>> SIGNI from your ener zone into the trash]@: Target 1 of your opponent's SIGNI with power 5000 or less, and put it into the ener zone."
        );

		setName("zh_simplified", "翠美 蚀刻版画");
        setDescription("zh_simplified", 
                "@C :你的能量区有<<美巧>>精灵时，这只精灵的力量+2000。\n" +
                "@A 横置从能量区把<<美巧>>精灵1张放置到废弃区:对战对手的力量5000以下的精灵1只作为对象，将其放置到能量区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(2);
        setPower(8000);

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

            registerConstantAbility(this::onConstEffCond, new PowerModifier(2000));

            registerActionAbility(new AbilityCostList(new DownCost(), new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.BEAUTIFUL_TECHNIQUE).fromEner())), this::onActionEff);

        }

        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BEAUTIFUL_TECHNIQUE).fromEner().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            putInEner(target);
        }
    }
}
