package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G2_NipponosaurusPhantomDragon extends Card {

    public SIGNI_G2_NipponosaurusPhantomDragon()
    {
        setImageSets("SPDi01-125");

        setOriginalName("幻竜　ニッポノ");
        setAltNames("ゲンリュウニッポノ Genryuu Nippono");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手が%Xを支払わないかぎり、【エナチャージ１】をする。"
        );

        setName("en", "Nipponosaurus, Phantom Dragon");
        setDescription("en",
                "@U: At the beginning of your attack phase, [[Ener Charge 1]] unless your opponent pays %X."
        );

		setName("zh_simplified", "幻龙 日本龙");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，如果对战对手不把%X:支付，那么[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(2);
        setPower(5000);

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
            if(!payEner(getOpponent(), Cost.colorless(1)))
            {
                enerCharge(1);
            }
        }
    }
}
