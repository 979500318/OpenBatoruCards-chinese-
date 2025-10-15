package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K3_TamanoriGreatTrap extends Card {

    public SIGNI_K3_TamanoriGreatTrap()
    {
        setImageSets("WXK01-065");

        setOriginalName("大罠　タマノリ");
        setAltNames("ダイビンタマノリ Daibin Tamanori");
        setDescription("jp",
                "@C：あなたの＜トリック＞のシグニのパワーを＋1000する。\n" +
                "@U $T1：あなたのデッキからカードがトラッシュに置かれたとき、ターン終了時まで、あなたのすべての＜トリック＞のシグニのパワーを＋2000する。"
        );

        setName("en", "Tamanori, Great Trap");
        setDescription("en",
                "@C: All of your <<Trick>> SIGNI get +1000 power.\n" +
                "@U $T1: When a card is put from your deck into the trash, until end of turn, all of your <<Trick>> SIGNI get +2000 power."
        );

		setName("zh_simplified", "大罠 踩球");
        setDescription("zh_simplified", 
                "@C :你的<<トリック>>精灵的力量+1000。\n" +
                "@U $T1 :当从你的牌组把牌放置到废弃区时，直到回合结束时为止，你的全部的<<トリック>>精灵的力量+2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
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

            registerConstantAbility(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.TRICK), new PowerModifier(1000));

            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            auto.setActiveLocation(CardLocation.DECK_MAIN);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.TRICK).getExportedData(), 2000, ChronoDuration.turnEnd());
        }
    }
}
