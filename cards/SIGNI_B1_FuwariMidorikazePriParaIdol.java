package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B1_FuwariMidorikazePriParaIdol extends Card {

    public SIGNI_B1_FuwariMidorikazePriParaIdol()
    {
        setImageSets("WXDi-P10-058");

        setOriginalName("プリパラアイドル　緑風ふわり");
        setAltNames("プリパラアイドルミドリカゼフワリ Puripara Aidoru Midorikaze Fuwari");
        setDescription("jp",
                "@U $T1：あなたが＜プリパラ＞のシグニを１枚捨てたとき、あなたの＜プリパラ＞のシグニ１体を対象とし、ターン終了時まで、それのパワーを＋2000する。\n" +
                "@E：カードを１枚引き、手札を１枚捨てる。"
        );

        setName("en", "Midorikaze Fuwari, Pripara Idol");
        setDescription("en",
                "@U $T1: When you discard a <<Pripara>> SIGNI, target <<Pripara>> SIGNI on your field gets +2000 power until end of turn.\n" +
                "@E: Draw a card and discard a card."
        );
        
        setName("en_fan", "Fuwari Midorikaze, PriPara Idol");
        setDescription("en_fan",
                "@U $T1: When you discard 1 <<PriPara>> SIGNI, target 1 of your <<PriPara>> SIGNI, and until end of turn, it gets +2000 power.\n" +
                "@E: Draw 1 card, and discard 1 card from your hand."
        );

		setName("zh_simplified", "美妙天堂偶像 绿风芙羽梨");
        setDescription("zh_simplified", 
                "@U $T1 :当你把<<プリパラ>>精灵1张舍弃时，你的<<プリパラ>>精灵1只作为对象，直到回合结束时为止，其的力量+2000。\n" +
                "@E :抽1张牌，手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
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

            AutoAbility auto = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) &&
                   caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.PRIPARA) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.PRIPARA)).get();
            gainPower(target, 2000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            draw(1);
            discard(1);
        }
    }
}
