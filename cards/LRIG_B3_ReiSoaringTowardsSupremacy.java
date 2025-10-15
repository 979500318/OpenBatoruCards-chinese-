package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DownCost;

public final class LRIG_B3_ReiSoaringTowardsSupremacy extends Card {
    
    public LRIG_B3_ReiSoaringTowardsSupremacy()
    {
        setImageSets("WXDi-P03-009", "SPDi07-03","SPDi08-03","SPDi09-03");
        
        setOriginalName("至高へ飛翔　レイ");
        setAltNames("シコウへヒショウレイ Shikou he Hishou Rei");
        setDescription("jp",
                "=T ＜Ｎｏ　Ｌｉｍｉｔ＞\n" +
                "^U：あなたのアタックフェイズ開始時、このターンにあなたが効果によってカードを２枚以上引いていた場合、レベル２以下のシグニ１体を対象とし、%B %Xを支払ってもよい。そうした場合、それをデッキの一番下に置く。\n" +
                "@E：カードを１枚引き[[エナチャージ１]]をする。\n" +
                "@A $G1 @[アップ状態のレベル２のルリグ２体をダウンする]@：手札をすべて捨てる。この方法で捨てた青のカードと同じ枚数、対戦相手は手札を捨てる。"
        );
        
        setName("en", "Rei, On the Wings of Supremacy");
        setDescription("en",
                "=T <<No Limit>>\n" +
                "^U: At the beginning of your attack phase, if you have drawn two or more cards by effects this turn, you may pay %B %X. If you do, put target level two or less SIGNI on the bottom of its owner's deck.\n" +
                "@E: Draw a card and [[Ener Charge 1]].\n" +
                "@A $G1 @[Down two upped level two LRIG]@: Discard your hand. Your opponent discards cards equal to the number of blue cards you discarded this way."
        );
        
        setName("en_fan", "Rei, Soaring Towards Supremacy");
        setDescription("en_fan",
                "=T <<No Limit>>\n" +
                "^U: At the beginning of your attack phase, if you drew 2 or more cards with your effects this turn, target 1 of your opponent's level 2 or lower SIGNI, and you may pay %B %X. If you do, put it on the bottom of the deck.\n" +
                "@E: Draw 1 card and [[Ener Charge 1]].\n" +
                "@A $G1 @[Down 2 of your upped level 2 LRIGs]@: Discard all cards from your hand. Your opponent discard cards from their hand equal to the number of blue cards discarded this way."
        );
        
		setName("zh_simplified", "向至高飞翔 令");
        setDescription("zh_simplified", 
                "=T<<No:Limit>>\n" +
                "^U:你的攻击阶段开始时，这个回合你因为效果抽牌2张以上的场合，等级2以下的精灵1只作为对象，可以支付%B%X。这样做的场合，将其放置到牌组最下面。\n" +
                "@E :抽1张牌并[[能量填充1]]。\n" +
                "@A $G1 竖直状态的等级2的分身2只#D:手牌全部舍弃。与这个方法舍弃的蓝色的牌相同张数，对战对手把手牌舍弃。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REI);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
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
            
            ActionAbility act = registerActionAbility(new DownCost(2, new TargetFilter().own().anyLRIG().withLevel(2).upped()), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.NO_LIMIT) && isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.DRAW && event.getSourceAbility() != null && isOwnCard(event.getSource())) >= 2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withLevel(0,2)).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1)))
                {
                    returnToDeck(target, DeckPosition.BOTTOM);
                }
            }
        }
        
        private void onEnterEff()
        {
            draw(1);
            enerCharge(1);
        }
        
        private void onActionEff()
        {
            DataTable<CardIndex> data = discard(getCardsInHand(getOwner()));
            
            if(data.get() != null)
            {
                discard(getOpponent(), (int)data.stream().filter(cardIndex -> cardIndex.getIndexedInstance().getColor().matches(CardColor.BLUE)).count());
            }
        }
    }
}
