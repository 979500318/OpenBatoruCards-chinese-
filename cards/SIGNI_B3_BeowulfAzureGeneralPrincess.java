package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B3_BeowulfAzureGeneralPrincess extends Card {

    public SIGNI_B3_BeowulfAzureGeneralPrincess()
    {
        setImageSets("WX25-P2-056", "WX25-P2-056U");
        setLinkedImageSets("WX25-P2-022");

        setOriginalName("蒼将姫　ベオウルフ");
        setAltNames("ソウショウキベオウルフ Soushouki Beourufu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《ソウイ＝スリー》がいる場合、対戦相手のすべてのシグニを凍結する。\n" +
                "@U：このシグニがアタックしたとき、あなたの場に他の＜武勇＞のシグニがある場合、カードを１枚引くか、対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Beowulf, Azure General Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, if your LRIG is \"Soui-Three\", freeze all of your opponent's SIGNI.\n" +
                "@U: Whenever this SIGNI attacks, if there is another <<Valor>> SIGNI on your field, draw 1 card or your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "苍将姬 贝奥武夫");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《ソウイ＝スリー》的场合，对战对手的全部的精灵冻结。\n" +
                "@U :当这只精灵攻击时，你的场上有其他的<<武勇>>精灵的场合，抽1张牌或，对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("ソウイ＝スリー"))
            {
                freeze(getSIGNIOnField(getOpponent()));
            }
        }

        private void onAutoEff2()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VALOR).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                if(playerChoiceAction(ActionHint.DRAW, ActionHint.DISCARD) == 1)
                {
                    draw(1);
                } else {
                    discard(getOpponent(), 1);
                }
            }
        }
    }
}
