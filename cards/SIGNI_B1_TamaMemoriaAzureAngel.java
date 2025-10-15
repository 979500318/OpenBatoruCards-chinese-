package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_B1_TamaMemoriaAzureAngel extends Card {

    public SIGNI_B1_TamaMemoriaAzureAngel()
    {
        setImageSets("WXDi-P11-064", "WXDi-P11-064P");

        setOriginalName("蒼天　タマ//メモリア");
        setAltNames("ソウテンタマメモリア Souten Tama Memoria");
        setDescription("jp",
                "@U $T1：あなたのターンの間、あなたの他の＜天使＞のシグニ１体が場に出るか、あなたの効果によって対戦相手が手札を１枚捨てたとき、次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。"
        );

        setName("en", "Tama//Memoria, Azure Angel");
        setDescription("en",
                "@U $T1: During your turn, when another <<Angel>> SIGNI enters your field, or your opponent discards a card by your effect, this SIGNI gets +4000 power until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Tama//Memoria, Azure Angel");
        setDescription("en_fan",
                "@U $T1: During your turn, when 1 of your other <<Angel>> SIGNI enters the field or when your opponent discards 1 card from their hand by your effect, until the end of your opponent's next turn, this SIGNI gets +4000 power."
        );

		setName("zh_simplified", "苍天 小玉//回忆");
        setDescription("zh_simplified", 
                "@U $T1 :你的回合期间，当你的其他的<<天使>>精灵1只出场或，因为你的效果把对战对手的手牌1张舍弃时，直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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

            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() &&
                   ((caller != getCardIndex() && CardLocation.isSIGNI(EventMove.getDataMoveLocation()) &&
                     caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.ANGEL)) ||
                    (caller.isEffectivelyAtLocation(CardLocation.HAND) && EventMove.getDataMoveLocation() == CardLocation.TRASH &&
                     !isOwnCard(caller) && getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSourceCardIndex())
                   )) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
