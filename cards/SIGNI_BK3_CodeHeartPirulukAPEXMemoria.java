package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_BK3_CodeHeartPirulukAPEXMemoria extends Card {

    public SIGNI_BK3_CodeHeartPirulukAPEXMemoria()
    {
        setImageSets("WXDi-P09-048", "WXDi-P09-048P","WX25-P2-118");

        setOriginalName("コードハート　ピルルクＡＰＥＸ//メモリア");
        setAltNames("コードハートピルルクアペクスメモリア Koodo Haato Piruruku Apekusu Memoria");
        setDescription("jp",
                "@C：[[シャドウ（スペル）]]\n" +
                "@U $T1：あなたがスペルを使用したとき、対戦相手のシグニ１体を対象とし、%Bか%Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－8000する。\n" +
                "@E：あなたのデッキの上からカードを３枚見る。その中からスペル１枚を公開し手札に加え、残りをトラッシュに置く。"
        );

        setName("en", "Piruluk APEX//Memoria, Code: Heart");
        setDescription("en",
                "@C: [[Shadow -- Spell]]\n" +
                "@U $T1: When you use a spell, you may pay %B or %K. If you do, target SIGNI on your opponent's field gets --8000 power until end of turn.\n" +
                "@E: Look at the top three cards of your deck. Reveal a spell from among them and add it to your hand and put the rest into your trash."
        );
        
        setName("en_fan", "Code Heart Piruluk APEX//Memoria");
        setDescription("en_fan",
                "@C: [[Shadow (spell)]]\n" +
                "@U $T1: When you use a spell, target 1 of your opponent's SIGNI, and you may pay %B or %K. If you do, until end of turn, it gets --8000 power.\n" +
                "@E: Look at the top 3 cards of your deck. Reveal 1 spell from among them, and add it to your hand, and put the rest into the trash."
        );

		setName("zh_simplified", "爱心代号 皮璐璐可APEX//回忆");
        setDescription("zh_simplified", 
                "@C :[[暗影（魔法）]]\n" +
                "@U $T1 当你把魔法使用时，对战对手的精灵1只作为对象，可以支付%B:或%K。这样做的场合，直到回合结束时为止，其的力量-8000。\n" +
                "@E :从你的牌组上面看3张牌。从中把魔法1张公开加入手牌，剩下的放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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
            
            registerStockAbility(new StockAbilityShadow(this::onStockEffAddCond));

            AutoAbility auto = registerAutoAbility(GameEventId.USE_SPELL, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getCardReference().getType() == CardType.SPELL ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && pay(new EnerCost(Cost.color(CardColor.BLUE, 1)), new EnerCost(Cost.color(CardColor.BLACK, 1))))
            {
                gainPower(target, -8000, ChronoDuration.turnEnd());
            }
        }

        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().spell().fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            trash(getCardsInLooked(getOwner()));
        }
    }
}
