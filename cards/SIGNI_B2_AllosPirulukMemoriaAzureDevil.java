package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B2_AllosPirulukMemoriaAzureDevil extends Card {

    public SIGNI_B2_AllosPirulukMemoriaAzureDevil()
    {
        setImageSets("WXDi-P10-062", "WXDi-P10-062P");

        setOriginalName("蒼魔　アロス・ピルルク//メモリア");
        setAltNames("ソウマアロスピルルクメモリア Souma Arosu Piruruku Memoria");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたの手札が０枚の場合、カードを１枚引いてもよい。\n" +
                "@E @[手札を３枚捨てる]@：対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A $T1 #C #C #C：対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Allos Piruluk//Memoria, Azure Evil");
        setDescription("en",
                "@U: At the end of your turn, if you have no cards in your hand, you may draw a card.\n" +
                "@E @[Discard three cards]@: Vanish target level two or less SIGNI on your opponent's field.\n" +
                "@A $T1 #C #C #C: Your opponent discards a card at random."
        );
        
        setName("en_fan", "Allos Piruluk//Memoria, Azure Devil");
        setDescription("en_fan",
                "@U: At the end of your turn, if there are 0 cards in your hand, you may draw 1 card.\n" +
                "@E @[Discard 3 cards from your hand]@: Target 1 of your opponent's level 2 or lower SIGNI, and banish it.\n" +
                "@A $T1 #C #C #C: Choose 1 card from your opponent's hand without looking, and discard it."
        );

		setName("zh_simplified", "苍魔 阿洛斯·皮璐璐可//回忆");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，你的手牌在0张的场合，可以抽1张牌。\n" +
                "@E 手牌3张舍弃:对战对手的等级2以下的精灵1只作为对象，将其破坏。\n" +
                "@A $T1 #C #C #C:不看对战对手的手牌选1张，舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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

            registerEnterAbility(new DiscardCost(3), this::onEnterEff);

            ActionAbility act = registerActionAbility(new CoinCost(3), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getHandCount(getOwner()) == 0 && playerChoiceActivate())
            {
                draw(1);
            }
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
            banish(target);
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
    }
}
