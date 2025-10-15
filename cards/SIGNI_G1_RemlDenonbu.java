package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_RemlDenonbu extends Card {

    public SIGNI_G1_RemlDenonbu()
    {
        setImageSets("WXDi-P14-089");

        setOriginalName("電音部　りむる");
        setAltNames("デンオンブリムル Denonbu Remuru");
        setDescription("jp",
                "@C：あなたのエナゾーンに＜電音部＞のシグニがあるかぎり、このシグニのパワーは＋5000される。"
        );

        setName("en", "DEN-ON-BU Reml");
        setDescription("en",
                "@C: As long as there is a <<DEN-ON-BU>> SIGNI in your Ener Zone, this SIGNI gets +5000 power."
        );
        
        setName("en_fan", "Reml, Denonbu");
        setDescription("en_fan",
                "@C: As long as there is a <<Denonbu>> SIGNI in your ener zone, this SIGNI gets +5000 power."
        );

		setName("zh_simplified", "电音部 利姆露");
        setDescription("zh_simplified", 
                "@C :你的能量区有<<電音部>>精灵时，这只精灵的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DENONBU).fromEner().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
