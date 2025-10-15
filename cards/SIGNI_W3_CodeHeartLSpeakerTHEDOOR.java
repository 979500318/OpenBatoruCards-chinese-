package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_W3_CodeHeartLSpeakerTHEDOOR extends Card {

    public SIGNI_W3_CodeHeartLSpeakerTHEDOOR()
    {
        setImageSets("WXDi-P15-056");
        setLinkedImageSets("WXDi-P15-010");

        setOriginalName("コードハート　Lスピーカ//THE DOOR");
        setAltNames("コードハートエルスピーカザドアー Koodo Haato Eru Supiika Za Doaa");
        setDescription("jp",
                "@C：このシグニは同じシグニゾーンに【ゲート】があるかぎり、@>@U：このシグニがアタックしたとき、あなたの場に《防衛者MC.LION-3rd》がいる場合、%W %Wを支払ってもよい。そうした場合、このシグニをアップし、ターン終了時まで、このシグニは能力を失う。@@を得る。\n" +
                "@U：あなたのアタックフェイズ開始時、次の対戦相手のターン終了時まで、同じシグニゾーンに【ゲート】があるあなたのすべてのシグニのパワーを＋2000する。"
        );

        setName("en", "L Speaker//THE DOOR, Code: Heart");
        setDescription("en",
                "@C: As long as this SIGNI is in the same SIGNI Zone as a [[Gate]], it gains@>@U: Whenever this SIGNI attacks, if \"Defender MC LION - 3rd\" is on your field, you may pay %W %W. If you do, up this SIGNI and it loses its abilities until end of turn.@@@U: At the beginning of your attack phase, all SIGNI on your field in the same SIGNI Zone as a [[Gate]] get +2000 power until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Code Heart L-Speaker//THE DOOR");
        setDescription("en_fan",
                "@C: As long as this SIGNI is on a SIGNI zone with a [[Gate]], it gains:" +
                "@>@U: Whenever this SIGNI attacks, if your LRIG is \"Defender MC.LION - 3rd\", you may pay %W %W. If you do, up this SIGNI, and until end of turn, it loses its abilities.@@" +
                "@U: At the beginning of your attack phase, until the end of your opponent's next turn, all of your SIGNI on SIGNI zones with a [[Gate]] get +2000 power."
        );

		setName("zh_simplified", "爱心代号 音箱//THE DOOR");
        setDescription("zh_simplified", 
                "@C :这只精灵的相同精灵区有[[大门]]时，得到\n" +
                "@>@U :当这只精灵攻击时，你的场上有《防衛者ＭＣ．ＬＩＯＮ－３ｒｄ》的场合，可以支付%W %W。这样做的场合，这只精灵竖直，直到回合结束时为止，这只精灵的能力失去。@@\n" +
                "@U :你的攻击阶段开始时，直到下一个对战对手的回合结束时为止，相同精灵区有[[大门]]的你的全部的精灵的力量+2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEFENSE_FACTION,CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onConstEffCond()
        {
            return hasZoneObject(CardUnderType.ZONE_GATE) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("防衛者MC.LION-3rd") &&
               payEner(Cost.color(CardColor.WHITE, 2)))
            {
                up();

                disableAllAbilities(getCardIndex(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(new TargetFilter().own().SIGNI().withZoneObject(CardUnderType.ZONE_GATE).getExportedData(), 2000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
