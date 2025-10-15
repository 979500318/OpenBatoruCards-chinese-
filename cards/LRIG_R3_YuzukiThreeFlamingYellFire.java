package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_R3_YuzukiThreeFlamingYellFire extends Card {
    
    public LRIG_R3_YuzukiThreeFlamingYellFire()
    {
        setImageSets("WXDi-P06-011", "WXDi-P06-011U");
        
        setOriginalName("炎唱火　遊月・参");
        setAltNames("エンショウカユヅキサン Enshouka Yuzuki San");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のエナゾーンからカード１枚を対象とし、それをトラッシュに置いてもよい。そうした場合、対戦相手は【エナチャージ１】をしてもよい。\n" +
                "@E：対戦相手は自分のシグニ１体を選びバニッシュする。\n" +
                "@A $G1 %R0：【エナチャージ２】をする。その後、対戦相手のエナゾーンにカードが４枚以上ある場合、対戦相手のエナゾーンからカードを２枚まで対象とし、それらをトラッシュに置く。"
        );
        
        setName("en", "Yuzuki Three, Blazing Fire Chant");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may put target card from your opponent's Ener Zone into their trash. If you do, your opponent may [[Ener Charge 1]].\n" +
                "@E: Your opponent chooses a SIGNI on their field and vanishes it.\n" +
                "@A $G1 %R0: [[Ener Charge 2]]. Then, if there are four or more cards in your opponent's Ener Zone, put up to two target cards in your opponent's Ener Zone into their trash."
        );
        
        setName("en_fan", "Yuzuki Three, Flaming Yell Fire");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 card from your opponent's ener zone, and you may put it into the trash. If you do, your opponent may [[Ener Charge 1]].\n" +
                "@E: Your opponent chooses 1 of their SIGNI and banishes it.\n" +
                "@A $G1 %R0: [[Ener Charge 2]]. Then, if there are 4 or more cards in your opponent's ener zone, target up to 2 cards from your opponent's ener zone, and put them into the trash."
        );
        
		setName("zh_simplified", "炎唱火 游月·叁");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从对战对手的能量区把1张牌作为对象，可以将其放置到废弃区。这样做的场合，对战对手可以[[能量填充1]]。\n" +
                "@E :对战对手选自己的精灵1只破坏。\n" +
                "@A $G1 %R0:[[能量填充2]]。然后，对战对手的能量区的牌在4张以上的场合，从对战对手的能量区把牌2张最多作为对象，将这些放置到废弃区。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUZUKI);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);
        
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
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner()).get();
            
            if(target != null && playerChoiceActivate() &&
               trash(target) && playerChoiceActivate(getOpponent()))
            {
                enerCharge(getOpponent(), 1);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BANISH).own().SIGNI()).get();
            banish(cardIndex);
        }
        
        private void onActionEff()
        {
            enerCharge(2);
            
            if(getEnerCount(getOpponent()) >= 4)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.BURN).OP().fromEner());
                trash(data);
            }
        }
    }
}
