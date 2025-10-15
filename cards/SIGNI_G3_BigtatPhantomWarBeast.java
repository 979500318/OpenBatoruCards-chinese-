package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G3_BigtatPhantomWarBeast extends Card {

    public SIGNI_G3_BigtatPhantomWarBeast()
    {
        setImageSets("WX24-D4-19");

        setOriginalName("幻闘獣　ビグタット");
        setAltNames("ゲントウジュウビグタット Gentoujuu BiguTatto");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、【エナチャージ１】をする。\n" +
                "@E：あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000する。"
        );

        setName("en", "Bigtat, Phantom War Beast");
        setDescription("en",
                "@U: At the beginning of your attack phase, [[Ener Charge 1]].\n" +
                "@E: Target 1 of your SIGNI, and until end of turn, it gets +5000 power."
        );

		setName("zh_simplified", "幻斗兽 雪怪");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，[[能量填充1]]。\n" +
                "@E :你的精灵1只作为对象，直到回合结束时为止，其的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 5000, ChronoDuration.turnEnd());
        }
    }
}
