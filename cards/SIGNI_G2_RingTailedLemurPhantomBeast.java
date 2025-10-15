package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G2_RingTailedLemurPhantomBeast extends Card {

    public SIGNI_G2_RingTailedLemurPhantomBeast()
    {
        setImageSets("WX24-P1-075");

        setOriginalName("幻獣　ワオキツネザル");
        setAltNames("ゲンジュウワオキツネザル Genjuu Wao Kitsunezaru");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このシグニのパワーが10000以上の場合、【エナチャージ１】をする。"
        );

        setName("en", "Ring-Tailed Lemur, Phantom Beast");
        setDescription("en",
                "@U: At the beginning of your attack phase, if this SIGNI's power is 10000 or more, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "幻兽 环尾狐猴");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这只精灵的力量在10000以上的场合，[[能量填充1]]。（你的牌组最上面的牌放置到能量区）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(2);
        setPower(8000);

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
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getPower().getValue() >= 10000)
            {
                enerCharge(1);
            }
        }
    }
}
