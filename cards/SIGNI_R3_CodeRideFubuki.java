package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R3_CodeRideFubuki extends Card {

    public SIGNI_R3_CodeRideFubuki()
    {
        setImageSets("WXK01-047");

        setOriginalName("コードライド　フブキ");
        setAltNames("コードライドフブキ Koodo Raido Fubuki");
        setDescription("jp",
                "@C：あなたの＜乗機＞のシグニのパワーを＋1000する。\n" +
                "@U $T1：あなたのシグニ１体がドライブ状態になったとき、ターン終了時まで、あなたのすべての＜乗機＞のシグニのパワーを＋2000する。"
        );

        setName("en", "Code Ride Fubuki");
        setDescription("en",
                "@C: All of your <<Riding Machine>> SIGNI get +1000 power.\n" +
                "@U $T1: When 1 of your SIGNI enters the drive state, until end of turn, all of your <<Riding Machine>> SIGNI get +2000 power."
        );

		setName("zh_simplified", "骑乘代号 吹雪");
        setDescription("zh_simplified", 
                "@C :你的<<乗機>>精灵的力量+1000。\n" +
                "@U $T1 :当你的精灵1只变为驾驶状态时，直到回合结束时为止，你的全部的<<乗機>>精灵的力量+2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(3);
        setPower(7000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.RIDING_MACHINE), new PowerModifier(1000));

            AutoAbility auto = registerAutoAbility(GameEventId.DRIVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.RIDING_MACHINE).getExportedData(), 2000, ChronoDuration.turnEnd());
        }
    }
}
