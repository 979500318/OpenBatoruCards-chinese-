package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_G2_WOLFMemoriaExplosiveGun extends Card {

    public SIGNI_G2_WOLFMemoriaExplosiveGun()
    {
        setImageSets("WXDi-P09-073", "WXDi-P09-073P");

        setOriginalName("爆砲　WOLF//メモリア");
        setAltNames("バクホウウルフメモリア Bakuhou Urufu Memoria");
        setDescription("jp",
                "@C：このシグニは覚醒状態であるかぎり、@>@U：あなたのターン終了時、【エナチャージ１】をする。@@を得る。\n" +
                "@U：このシグニがバトルによって対戦相手のシグニ１体をバニッシュしたとき、このシグニは覚醒する。"
        );

        setName("en", "WOLF//Memoria, Explosive Gun");
        setDescription("en",
                "@C: As long as this SIGNI is awakened, this SIGNI gains@>@U: At the end of your turn, [[Ener Charge 1]].@@" +
                "@U: When this SIGNI vanishes a SIGNI on your opponent's field through battle, it is awakened. "
        );
        
        setName("en_fan", "WOLF//Memoria, Explosive Gun");
        setDescription("en_fan",
                "@C: As long as this SIGNI is awakened, this SIGNI gains:" +
                "@>@U: At the end of your turn, [[Ener Charge 1]].@@" +
                "@U: Whenever this SIGNI banishes your opponent's SIGNI in battle, this SIGNI awakens."
        );

		setName("zh_simplified", "爆炮 WOLF//回忆");
        setDescription("zh_simplified", 
                "@C :这只精灵在觉醒状态时，得到\n" +
                "@>@U :你的回合结束时，[[能量填充1]]。@@\n" +
                "@U :当这只精灵因为战斗把对战对手的精灵1只破坏时，这只精灵觉醒。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
        }

        private ConditionState onConstEffCond()
        {
            return isState(CardStateFlag.AWAKENED) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);

            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() && getEvent().getSourceAbility() == null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }
    }
}
