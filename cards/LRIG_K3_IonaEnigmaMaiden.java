package open.batoru.data.cards;

import open.batoru.UtilRandom;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameConst.SIGNIZonePosition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.FieldZone;
import open.batoru.game._3d.Group3D;
import open.batoru.game.animations.AnimationSpinnerRotateCustom;
import open.batoru.game.gfx.GFXZoneSpinner;
import open.batoru.game.gfx.GFXZoneSpinner.GFXSphericalSpinnerObject;

public final class LRIG_K3_IonaEnigmaMaiden extends Card {

    public LRIG_K3_IonaEnigmaMaiden()
    {
        setImageSets("WX24-P2-030", "WX24-P2-030U");

        setOriginalName("エニグマ/メイデン　イオナ");
        setAltNames("エニグマメイデンイオナ Eniguma Meiden Iona");
        setDescription("jp",
                "@U $TO $T1：あなたの＜迷宮＞のシグニ１体が場に出たとき、対戦相手のシグニゾーン１つを指定する。このターン、そのシグニゾーンにあるシグニのパワーを－5000する。\n" +
                "@A $G1 @[@|クレイヴ|@]@ %K0：次の対戦相手のターン終了時まで、このルリグは@>@C：アタックフェイズの間、対戦相手のシグニのパワーをあなたの場にあるシグニ１体につき－2000する。@@を得る。"
        );

        setName("en", "Iona, Enigma/Maiden");
        setDescription("en",
                "@U $TO $T1: When 1 of your <<Labyrinth>> SIGNI enters the field, choose 1 of your opponent's SIGNI zones. This turn, the SIGNI in that SIGNI zone get --5000 power.\n" +
                "@A $G1 @[@|Crave|@]@ %K0: Until the end of your opponent's next turn, this LRIG gains:" +
                "@>@C: During the attack phase, your opponent's SIGNI get --2000 power for each SIGNI on your field."
        );

		setName("zh_simplified", "谜言/少女 伊绪奈");
        setDescription("zh_simplified", 
                "@U $TO $T1 :当你的<<迷宮>>精灵1只出场时，对战对手的精灵区1个指定。这个回合，那个精灵区的精灵的力量-5000。\n" +
                "@A $G1 @[@|渴望|@]@%K0:直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@C :攻击阶段期间，对战对手的精灵的力量依据你的场上的精灵的数量，每有1只就-2000。@@\n"
        );

        setLRIGType(CardLRIGType.IONA);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Crave");
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.LABYRINTH) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            FieldZone fieldZone = playerTargetZone(new TargetFilter().OP().SIGNI()).get();
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().fromLocation(fieldZone.getZoneLocation()), new PowerModifier(-5000));
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
            
            Group3D[] spinNodes = new Group3D[3];
            for(int i=0;i<spinNodes.length;i++) spinNodes[i] = new GFXSphericalSpinnerObject(15,40, 10);
            GFXZoneSpinner spinner = new GFXZoneSpinner(getOpponent(),fieldZone.getZoneLocation(), new AnimationSpinnerRotateCustom(5000, 150, -50), spinNodes);
            GFXZoneSpinner.attachToChronoRecord(record, spinner);
            
            attachPlayerAbility(getOwner(), attachedConst, record);
        }

        private void onActionEff()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(), new PowerModifier(this::onAttachedConstEff2ModGetValue));
            attachedConst.setCondition(this::onAttachedConstEff2Cond);
            
            ChronoRecord record = new ChronoRecord(getCardIndex(), ChronoDuration.nextTurnEnd(getOpponent()));
            for(SIGNIZonePosition zonePosition : SIGNIZonePosition.values())
            {
                Group3D[] spinNodes = new Group3D[3];
                for(int i=0;i<spinNodes.length;i++) spinNodes[i] = new GFXSphericalSpinnerObject(20,50, 10, new int[]{200, 0, 200});
                GFXZoneSpinner spinner = new GFXZoneSpinner(getOpponent(), zonePosition.getLocation(), new AnimationSpinnerRotateCustom(4000, 90, -50), spinNodes);
                GFXZoneSpinner.attachToChronoRecord(record, spinner);
            }
            attachAbility(getCardIndex(), attachedConst, record);
        }
        private ConditionState onAttachedConstEff2Cond()
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) ? ConditionState.OK : ConditionState.BAD;
        }
        private double onAttachedConstEff2ModGetValue(CardIndex addPower)
        {
            return -2000 * getSIGNICount(getOwner());
        }
    }
}
