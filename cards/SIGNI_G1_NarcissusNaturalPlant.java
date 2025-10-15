package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_NarcissusNaturalPlant extends Card {

    public SIGNI_G1_NarcissusNaturalPlant()
    {
        setImageSets("WX24-P4-077");

        setOriginalName("羅植　スイセン");
        setAltNames("ラショクスイセン Rashoku Suisen");
        setDescription("jp",
                "@C：あなたのエナゾーンに＜植物＞のシグニがあるかぎり、このシグニのパワーは＋5000される。"
        );

        setName("en", "Narcissus, Natural Plant");
        setDescription("en",
                "@C: As long as there is a <<Plant>> SIGNI in your ener zone, this SIGNI gets +5000 power."
        );

		setName("zh_simplified", "罗植 水仙");
        setDescription("zh_simplified", 
                "@C :你的能量区有<<植物>>精灵时，这只精灵的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
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

            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PLANT).fromEner().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
