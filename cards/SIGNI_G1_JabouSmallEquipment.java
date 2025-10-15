package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_JabouSmallEquipment extends Card {

    public SIGNI_G1_JabouSmallEquipment()
    {
        setImageSets("WX24-P1-074");

        setOriginalName("小装　ジャボウ");
        setAltNames("ショウソウジャボウ Shousou Jabou");
        setDescription("jp",
                "@C：あなたのエナゾーンに＜アーム＞のシグニがあるかぎり、このシグニのパワーは＋5000される。"
        );

        setName("en", "Jabou, Small Equipment");
        setDescription("en",
                "@C: As long as there is an <<Arm>> SIGNI in your ener zone, this SIGNI gets +5000 power."
        );

		setName("zh_simplified", "小装 蛇矛");
        setDescription("zh_simplified", 
                "@C :你的能量区有<<アーム>>精灵时，这只精灵的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ARM).fromEner().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
