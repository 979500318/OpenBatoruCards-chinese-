package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_JeronimoVerdantGeneral extends Card {

    public SIGNI_G1_JeronimoVerdantGeneral()
    {
        setImageSets("WXDi-P07-078");

        setOriginalName("翠将　ジェロニモ");
        setAltNames("スイショウジェロニモ Suishou Jeronimo");
        setDescription("jp",
                "@C：このシグニが覚醒状態であるかぎり、このシグニのパワーは＋2000され、このシグニは@>@U：このシグニがアタックしたとき、【エナチャージ１】をする。@@を得る。\n" +
                "@U：あなたのターン終了時、このシグニは覚醒する。"
        );

        setName("en", "Geronimo, Jade General");
        setDescription("en",
                "@C: As long as this SIGNI is awakened, it gets +2000 power and gains@>@U: Whenever this SIGNI attacks, [[Ener Charge 1]].@@\n" +
                "@U: At the end of your turn, this SIGNI is awakened. "
        );
        
        setName("en_fan", "Jeronimo, Verdant General");
        setDescription("en_fan",
                "@C: As long as this SIGNI is awakened, it gets +2000 power, and it gains:" +
                "@>@U: Whenever this SIGNI attacks, [[Ener Charge 1]].@@" +
                "@U: At the end of your turn, this SIGNI awakens."
        );

		setName("zh_simplified", "翠将 杰罗尼莫");
        setDescription("zh_simplified", 
                "@C :这只精灵在觉醒状态时，这只精灵的力量+2000，这只精灵得到\n" +
                "@>@U :当这只精灵攻击时，[[能量填充1]]。@@\n" +
                "@U :你的回合结束时，这只精灵觉醒。（精灵觉醒后在场上保持觉醒状态）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(2000), new AbilityGainModifier(this::onConstEffModGetSample));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onConstEffCond()
        {
            return isState(CardStateFlag.AWAKENED) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            enerCharge(1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }
    }
}
