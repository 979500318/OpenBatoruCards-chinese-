package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_R1_GaaruruPriParaIdol extends Card {

    public SIGNI_R1_GaaruruPriParaIdol()
    {
        setImageSets("WXDi-P10-051");

        setOriginalName("プリパラアイドル　ガァルル");
        setAltNames("プリパラアイドルガァルル Puripara Aidoru Gaaruru");
        setDescription("jp",
                "@A #D：対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A #D：対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。この能力はあなたの場に他の＜プリパラ＞のシグニがある場合にしか使用できない。"
        );

        setName("en", "Gaaruru, Pripara Idol");
        setDescription("en",
                "@A #D: Vanish target SIGNI on your opponent's field with power 2000 or less.\n" +
                "@A #D: Vanish target SIGNI on your opponent's field with power 3000 or less. This ability can only be used if there is another <<Pripara>> SIGNI on your field."
        );
        
        setName("en_fan", "Gaaruru, PriPara Idol");
        setDescription("en_fan",
                "@A #D: Target 1 of your opponent's SIGNI with power 2000 or less, and banish it.\n" +
                "@A #D: Target 1 of your opponent's SIGNI with power 3000 or less, and banish it. This ability can only be used if there is another <<PriPara>> SIGNI on your field."
        );

		setName("zh_simplified", "美妙天堂偶像 卡露露");
        setDescription("zh_simplified", 
                "@A #D:对战对手的力量2000以下的精灵1只作为对象，将其破坏。\n" +
                "@A #D:对战对手的力量3000以下的精灵1只作为对象，将其破坏。这个能力只有在你的场上有其他的<<プリパラ>>精灵的场合才能使用。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
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

            registerActionAbility(new DownCost(), this::onActionEff1);
            
            ActionAbility act2 = registerActionAbility(new DownCost(), this::onActionEff2);
            act2.setCondition(this::onActionEff2Cond);
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
            banish(target);
        }
        
        private ConditionState onActionEff2Cond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PRIPARA).except(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
            banish(target);
        }
    }
}
