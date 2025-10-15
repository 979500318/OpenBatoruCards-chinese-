package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K3_Toramaru extends Card {

    public SIGNI_K3_Toramaru()
    {
        setImageSets("WXDi-CP02-TK03A");

        setOriginalName("虎丸");
        setAltNames("トラマル");
        setDescription("jp",
                "@C：これの上にある《棗イロハ》のパワーを＋5000する。\n" +
                "@C：これの上にある《棗イロハ》は@>@U：あなたのアタックフェイズ開始時、対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらのパワーをそれぞれ－3000する。@@を得る。"
        );

        setName("en", "Toramaru");
        setDescription("en",
                "@C: The \"Natsume Iroha\" on top of this gets +5000 power.\n@C: The \"Natsume Iroha\" on top of this gains@>@U: At the beginning of your attack phase, up to two target SIGNI on your opponent's field get --3000 power until end of turn."
        );
        
        setName("en_fan", "Toramaru");
        setDescription("en_fan",
                "@C: The \"Iroha Natsume\" on top of this card gets +5000 power.\n" +
                "@C: The \"Iroha Natsume\" on top of this card gains:" +
                "@>@U: At the beginning of your attack phase, target up to 2 of your opponent's SIGNI, and until end of turn, they get --3000 power."
        );

		setName("zh_simplified", "虎丸");
        setDescription("zh_simplified", 
                "@C :此衍生上面的《棗イロハ》的力量+5000。\n" +
                "@C :此衍生上面的《棗イロハ》得到\n" +
                "@>@U :你的攻击阶段开始时，对战对手的精灵2只最多作为对象，直到回合结束时为止，这些的力量各-3000。@@\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(15000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ConstantAbility cont1 = registerConstantAbility(new TargetFilter().own().SIGNI().withName("棗イロハ").over(cardId), new PowerModifier(5000));
            cont1.setActiveUnderFlags(CardUnderCategory.UNDER);

            ConstantAbility cont2 = registerConstantAbility(new TargetFilter().own().SIGNI().withName("棗イロハ").over(cardId), new AbilityGainModifier(this::onConstEff2ModGetSample));
            cont2.setActiveUnderFlags(CardUnderCategory.UNDER);
        }

        private Ability onConstEff2ModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = getAbility().getSourceCardIndex().getIndexedInstance().playerTargetCard(0,2, new TargetFilter(TargetHint.MINUS).OP().SIGNI());
            getAbility().getSourceCardIndex().getIndexedInstance().gainPower(data, -3000, ChronoDuration.turnEnd());
        }
    }
}
