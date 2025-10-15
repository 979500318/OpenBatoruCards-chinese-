package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_B3_YamatoTakeruAzureGeneralPrincess extends Card {

    public SIGNI_B3_YamatoTakeruAzureGeneralPrincess()
    {
        setImageSets("WX24-P3-054");

        setOriginalName("蒼将姫　ヤマトタケル");
        setAltNames("ソウショウキヤマトタケル Soushouki Yamato Takeru");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手は手札を１枚捨てる。&E４枚以上@0代わりに対戦相手の手札を１枚見ないで選び、捨てさせる。\n" +
                "@A #D：カードを１枚引く。"
        );

        setName("en", "Yamato Takeru, Azure General Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, your opponent discards 1 card from their hand. &E4 or more@0 Instead, choose 1 card from your opponent's hand without looking, and discard it.\n" +
                "@A #D: Draw 1 card."
        );

		setName("zh_simplified", "苍将姬 日本武尊");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，对战对手把手牌1张舍弃。&E4张以上@0作为替代，不看对战对手的手牌选1张，舍弃。\n" +
                "@A #D:抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(12000);

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
            auto.setRecollect(4);
            
            registerActionAbility(new DownCost(), this::onActionEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!getAbility().isRecollectFulfilled())
            {
                discard(getOpponent(), 1);
            } else {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }
        
        private void onActionEff()
        {
            draw(1);
        }
    }
}
