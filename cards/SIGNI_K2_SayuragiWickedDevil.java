package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventRefresh;

public final class SIGNI_K2_SayuragiWickedDevil extends Card {
    
    public SIGNI_K2_SayuragiWickedDevil()
    {
        setImageSets("WXDi-P07-093");
        
        setOriginalName("凶魔　サユラギ");
        setAltNames("キョウマサユラギ Kyouma Sayuragi");
        setDescription("jp",
                "@U $T1：あなたのデッキの上からカード１枚がトラッシュに置かれたとき、ターン終了時まで、このシグニのパワーを＋5000する。\n" +
                "@U $T1：あなたがリフレッシュしたとき、【エナチャージ１】をする。\n" +
                "@A $T1 %K0：あなたのデッキの上からカードを３枚トラッシュに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );
        
        setName("en", "Sayuragi, Doomed Evil");
        setDescription("en",
                "@U $T1: When a card is put from your deck into your trash, this SIGNI gets +5000 power until end of turn.\n" +
                "@U $T1: When you refresh your deck, [[Ener Charge 1]].\n" +
                "@A $T1 %K0: Put the top three cards of your deck into your trash." +
                "~#You may discard a card. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Sayuragi, Wicked Devil");
        setDescription("en_fan",
                "@U $T1: When a card is put from your deck into the trash, until end of turn, this SIGNI gets +5000 power.\n" +
                "@U $T1: When you refresh, [[Ener Charge 1]].\n" +
                "@A $T1 %K0: Put the top 3 cards of your deck into the trash." +
                "~#Target 1 of your opponent's SIGNI, and you may discard 1 card from your hand. If you do, until end of turn, it gets --12000 power."
        );
        
		setName("zh_simplified", "凶魔 小瑶");
        setDescription("zh_simplified", 
                "@U $T1 :当从你的牌组把1张牌放置到废弃区时，直到回合结束时为止，这只精灵的力量+5000。\n" +
                "@U $T1 :当你重构时，[[能量填充1]]。\n" +
                "@A $T1 %K0:从你的牌组上面把3张牌放置到废弃区。" +
                "~#对战对手的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.TRASH, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.REFRESH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.isEffectivelyAtLocation(CardLocation.DECK_MAIN) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            gainPower(getCardIndex(), 5000, ChronoDuration.turnEnd());
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return ((EventRefresh)getEvent()).getPlayer() == getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            enerCharge(1);
        }
        
        private void onActionEff()
        {
            millDeck(3);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
