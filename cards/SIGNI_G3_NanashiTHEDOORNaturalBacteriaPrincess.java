package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventCoin;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G3_NanashiTHEDOORNaturalBacteriaPrincess extends Card {

    public SIGNI_G3_NanashiTHEDOORNaturalBacteriaPrincess()
    {
        setImageSets("WXDi-P15-053", "WXDi-P15-053P");

        setOriginalName("羅菌姫　ナナシ//THE DOOR");
        setAltNames("ラキンヒメナナシザドアー Rakinhime Nanashi Za Doaa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、【エナチャージ１】をする。このターンにあなたが#Cを合計１枚以上支払っていた場合、追加で【エナチャージ１】をする。\n" +
                "@A #C：次の対戦相手のターン終了時まで、このシグニは[[シャドウ（レベル３以上）]]を得る。\n" +
                "@A $T1 %G #C #C：あなたのトラッシュにあるすべてのカードをデッキに加えてシャッフルする。"
        );

        setName("en", "Nanashi//THE DOOR, Bacteria Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, [[Ener Charge 1]]. If you have paid a total of one or more #C this turn, [[Ener Charge 1]].\n@A #C: This SIGNI gains [[Shadow -- Level three or more]] until the end of your opponent's next end phase. \n@A $T1 %G #C #C: Shuffle all cards in your trash into your deck."
        );
        
        setName("en_fan", "Nanashi//THE DOOR, Natural Bacteria Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, [[Ener Charge 1]]. If you paid 1 or more #C this turn, additionally [[Ener Charge 1]].\n" +
                "@A #C: Until the end of your opponent's next turn, this SIGNI gains [[Shadow (level 3 or higher)]].\n" +
                "@A $T1 %G #C #C: Shuffle all cards from your trash into the deck."
        );

		setName("zh_simplified", "罗菌姬 无名//THE DOOR");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，[[能量填充1]]。这个回合你把币:合计1个以上支付过的场合，追加[[能量填充1]]。\n" +
                "@A #C:直到下一个对战对手的回合结束时为止，这只精灵得到[[暗影（等级3以上）]]。（这只精灵不会被对战对手的等级3以上的分身和等级3以上的精灵作为对象）\n" +
                "@A $T1 %G#C #C:你的废弃区的全部的牌加入牌组洗切。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.BACTERIA);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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

            registerActionAbility(new CoinCost(1), this::onActionEff1);

            ActionAbility act2 = registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.GREEN, 1)), new CoinCost(2)), this::onActionEff2);
            act2.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(1);
            
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() < 0) > 0)
            {
                enerCharge(1);
            }
        }

        private void onActionEff1()
        {
            attachAbility(getCardIndex(), new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getLevel().getValue() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onActionEff2()
        {
            returnToDeck(getCardsInTrash(getOwner()), DeckPosition.TOP);
            shuffleDeck();
        }
    }
}
