package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W1_CodeMazeMuzicaTHEDOOR extends Card {

    public SIGNI_W1_CodeMazeMuzicaTHEDOOR()
    {
        setImageSets("WXDi-P15-076");

        setOriginalName("コードメイズ　ムジカ//THE DOOR");
        setAltNames("コードメイズムジカザドアー Koodo Meizu Mujika Za Doaa");
        setDescription("jp",
                "@C：このシグニは同じシグニゾーンに【ゲート】があるかぎり、@>@U：あなたのターン終了時、対戦相手のシグニ１体を対象とし、それをトラッシュに置く。@@を得る。\n" +
                "@C：あなたの場に【ゲート】があるかぎり、このシグニのパワーは＋5000される。"
        );

        setName("en", "Muzica//THE DOOR, Code: Maze");
        setDescription("en",
                "@C: As long as this SIGNI is in the same SIGNI Zone as a [[Gate]], it gains@>@U: At the end of your turn, put target SIGNI on your opponent's field into its owner's trash.@@@C: As long as there is a [[Gate]] on your field, this SIGNI gets +5000 power."
        );
        
        setName("en_fan", "Code Maze Muzica//THE DOOR");
        setDescription("en_fan",
                "@C: As long as this SIGNI is on a SIGNI zone with a [[Gate]], it gains:" +
                "@>@U: At the end of your turn, target 1 of your opponent's SIGNI, and put it into the trash.@@" +
                "@C: As long as there is a [[Gate]] on your field, this SIGNI gets +5000 power."
        );

		setName("zh_simplified", "迷宫代号 穆希卡//THE DOOR");
        setDescription("zh_simplified", 
                "@C :这只精灵的相同精灵区有[[大门]]时，得到\n" +
                "@>@U :你的回合结束时，对战对手的精灵1只作为对象，将其放置到废弃区。@@\n" +
                "@C :你的场上有[[大门]]时，这只精灵的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEFENSE_FACTION,CardSIGNIClass.LABYRINTH);
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

            registerConstantAbility(this::onConstEff1Cond, new AbilityGainModifier(this::onConstEff1ModGetSample));
            registerConstantAbility(this::onConstEff2Cond, new PowerModifier(5000));
        }

        private ConditionState onConstEff1Cond()
        {
            return hasZoneObject(CardUnderType.ZONE_GATE) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEff1ModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            trash(target);
        }

        private ConditionState onConstEff2Cond()
        {
            return new TargetFilter().own().SIGNI().zone().withZoneObject(CardUnderType.ZONE_GATE).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
