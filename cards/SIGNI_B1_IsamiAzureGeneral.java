package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_IsamiAzureGeneral extends Card {

    public SIGNI_B1_IsamiAzureGeneral()
    {
        setImageSets("WX25-P2-080");

        setOriginalName("蒼将　イサミ");
        setAltNames("ソウショウイサミ Soushou Isami");
        setDescription("jp",
                "@U：あなたのターン終了時、カードを１枚引く。対戦相手の場に凍結状態のシグニがない場合、手札を１枚捨てる。"
        );

        setName("en", "Isami, Azure General");
        setDescription("en",
                "@U: At the end of your turn, draw 1 card. If there are no frozen SIGNI on your opponent's field, discard 1 card from your hand."
        );

		setName("zh_simplified", "苍将 近藤勇");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，抽1张牌。对战对手的场上没有冻结状态的精灵的场合，手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(3000);

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
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
            
            if(new TargetFilter().OP().SIGNI().withState(CardStateFlag.FROZEN).getValidTargetsCount() == 0)
            {
                discard(1);
            }
        }
    }
}
