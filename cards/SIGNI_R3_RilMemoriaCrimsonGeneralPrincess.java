package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventCoin;

public final class SIGNI_R3_RilMemoriaCrimsonGeneralPrincess extends Card {

    public SIGNI_R3_RilMemoriaCrimsonGeneralPrincess()
    {
        setImageSets("WXDi-P09-039", "WXDi-P09-039P");

        setOriginalName("紅将姫　リル//メモリア");
        setAltNames("コウショウキリルメモリア Koushouki Riru Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたが#Cを合計１枚以上支払っていた場合、対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %R %X：あなたのトラッシュから&Rを持つシグニ１枚を対象とし、それを場に出す。\n" +
                "@A #C：対戦相手は自分のルリグデッキからカードを１枚公開する。"
        );

        setName("en", "Ril//Memoria, Crimson Warlord");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have paid a total of one or more #C this turn, vanish target SIGNI on your opponent's field with power 8000 or less.\n" +
                "@E %R %X: Put target SIGNI with a &R from your trash onto your field.\n" +
                "@A #C: Your opponent reveals a card from their LRIG Deck."
        );
        
        setName("en_fan", "Ril//Memoria, Crimson General Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if you paid 1 or more #C this turn, target 1 of your opponent's SIGNI with power 8000 or less, and banish it.\n" +
                "@E %R %X: Target 1 &R SIGNI from your trash, and put it onto the field.\n" +
                "@A #C: Your opponent reveals 1 card from their LRIG deck."
        );

		setName("zh_simplified", "红将姬 莉露//回忆");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，这个回合你把币:合计1个以上支付过的场合，对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n" +
                "@E %R%X:从你的废弃区把持有[升阶]的精灵1张作为对象，将其出场。\n" +
                "@A #C:对战对手从自己的分身牌组把1张牌公开。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(10000);

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

            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1)), this::onEnterEff);

            registerActionAbility(new CoinCost(1), this::onActionEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() < 0) > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                banish(target);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withUseCondition(UseCondition.RISE).fromTrash().playable()).get();
            putOnField(target);
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.REVEAL).own().fromLocation(CardLocation.DECK_LRIG).anyCard()).get();
            reveal(cardIndex);
            returnToDeck(cardIndex, DeckPosition.TOP);
        }
    }
}
