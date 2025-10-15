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

public final class SIGNI_G3_DaidaraPhantomApparition extends Card {

    public SIGNI_G3_DaidaraPhantomApparition()
    {
        setImageSets("WXK01-059");

        setOriginalName("幻怪　ダイダラ");
        setAltNames("ゲンカイダイダラ Genkai Daidara");
        setDescription("jp",
                "@C：あなたの＜怪異＞のシグニのパワーを＋1000する。\n" +
                "@U $T1：あなたがアーツを使用したとき、ターン終了時まで、あなたのすべての＜怪異＞のシグニのパワーを＋2000する。"
        );

        setName("en", "Daidara, Phantom Apparition");
        setDescription("en",
                "@C: All of your <<Apparition>> SIGNI get +1000 power.\n" +
                "@U $T1: When you use an ARTS, until end of turn, all of your <<Apparition>> SIGNI get +2000 power."
        );

		setName("zh_simplified", "幻怪 大太法师");
        setDescription("zh_simplified", 
                "@C :你的<<怪異>>精灵的力量+1000。\n" +
                "@U $T1 :当你把必杀使用时，直到回合结束时为止，你的全部的<<怪異>>精灵的力量+2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
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
            
            registerConstantAbility(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.APPARITION), new PowerModifier(1000));

            AutoAbility auto = registerAutoAbility(GameEventId.USE_ARTS, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.APPARITION).getExportedData(), 2000, ChronoDuration.turnEnd());
        }
    }
}
