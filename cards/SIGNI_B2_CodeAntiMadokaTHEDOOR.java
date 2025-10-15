package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_B2_CodeAntiMadokaTHEDOOR extends Card {

    public SIGNI_B2_CodeAntiMadokaTHEDOOR()
    {
        setImageSets("WXDi-P16-074");

        setOriginalName("コードアンチ　マドカ//THE DOOR");
        setAltNames("コードアンチマドカザドアー Koodo Anchi Madoka Za Doaa");
        setDescription("jp",
                "@C：このシグニは同じシグニゾーンに【ゲート】があるかぎり、@>@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－5000する。@@を得る。\n" +
                "@U $T1：同じシグニゾーンに【ゲート】があるあなたのシグニ１体がバニッシュされたとき、対戦相手は手札を１枚捨てる。" +
                "~#：カードを３枚引き、手札を１枚捨てる。"
        );

        setName("en", "Madoka//THE DOOR, Code: Anti");
        setDescription("en",
                "@C: As long as this SIGNI is in the same SIGNI Zone as a [[Gate]], it gains@>@U: At the beginning of your attack phase, you may pay %X. If you do, target SIGNI on your opponent's field gets --5000 power until end of turn.@@@U $T1: When a SIGNI on your field in the same SIGNI Zone as a [[Gate]] is vanished, your opponent discards a card." +
                "~#Draw three cards and discard a card."
        );
        
        setName("en_fan", "Code Anti Madoka//THE DOOR");
        setDescription("en_fan",
                "@C: As long as this SIGNI is on a SIGNI zone with a [[Gate]], it gains:" +
                "@>@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may pay %X. If you do, until end of turn, it gets --5000 power.@@" +
                "@U $T1: When your SIGNI on a SIGNI zone with a [[Gate]] is banished, your opponent discards 1 card from their hand." +
                "~#Draw 3 cards, and discard 1 card from your hand."
        );

		setName("zh_simplified", "古兵代号 円//THE DOOR");
        setDescription("zh_simplified", 
                "@C :这只精灵的相同精灵区有[[大门]]时，得到\n" +
                "@>@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以支付%X。这样做的场合，直到回合结束时为止，其的力量-5000。@@\n" +
                "@U $T1 :当相同精灵区有[[大门]]的你的精灵1只被破坏时，对战对手把手牌1张舍弃。" +
                "~#抽3张牌，手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEFENSE_FACTION,CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(2);
        setPower(8000);

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

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return hasZoneObject(CardUnderType.ZONE_GATE) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                gainPower(target, -5000, ChronoDuration.turnEnd());
            }
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().hasZoneObject(CardUnderType.ZONE_GATE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }
        
        private void onLifeBurstEff()
        {
            draw(3);
            discard(1);
        }
    }
}
