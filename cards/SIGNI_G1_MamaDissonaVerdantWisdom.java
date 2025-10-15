package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_MamaDissonaVerdantWisdom extends Card {

    public SIGNI_G1_MamaDissonaVerdantWisdom()
    {
        setImageSets("WXDi-P13-076");

        setOriginalName("翠英　ママ//ディソナ");
        setAltNames("スイエイママディソナ Suiei Mama Disona");
        setDescription("jp",
                "@C：あなたの場にいるルリグのレベルの合計が３であるかぎり、このシグニのパワーは＋5000される。\n" +
                "@C：あなたの場にいるルリグのレベルの合計が７であるかぎり、このシグニのパワーは＋7000される。"
        );

        setName("en", "Mama//Dissona, Jade Mind");
        setDescription("en",
                "@C: As long as the total level of LRIG on your field is exactly three, this SIGNI gets +5000 power.\n@C: As long as the total level of LRIG on your field is exactly seven, this SIGNI gets +7000 power."
        );
        
        setName("en_fan", "Mama//Dissona, Verdant Wisdom");
        setDescription("en_fan",
                "@C: As long as the total level of your LRIGs is 3, this SIGNI gets +5000 power.\n" +
                "@C: As long as the total level of your LRIGs is 7, this SIGNI gets +7000 power."
        );

		setName("zh_simplified", "翠英 妈妈//失调");
        setDescription("zh_simplified", 
                "@C :你的场上的分身的等级的合计在3时，这只精灵的力量+5000。\n" +
                "@C :你的场上的分身的等级的合计在7时，这只精灵的力量+7000。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
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

            registerConstantAbility(this::onConstEff1Cond, new PowerModifier(5000));
            registerConstantAbility(this::onConstEff2Cond, new PowerModifier(7000));
        }
        
        private ConditionState onConstEff1Cond()
        {
            return new TargetFilter().own().anyLRIG().getExportedData().stream().
                    mapToDouble(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).sum() == 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private ConditionState onConstEff2Cond()
        {
            return new TargetFilter().own().anyLRIG().getExportedData().stream().
                    mapToDouble(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).sum() == 7 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
