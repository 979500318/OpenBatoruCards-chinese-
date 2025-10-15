package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_CassiopeiaNaturalStarPrincess extends Card {
    
    public SIGNI_W3_CassiopeiaNaturalStarPrincess()
    {
        setImageSets("WXDi-P02-036");
        
        setOriginalName("羅星姫　カシオペヤ");
        setAltNames("ラセイキカシオペヤ Raseiki Kashiopeya");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの上からカードを４枚公開する。その中にレベル１のシグニが３枚以上ある場合、次の対戦相手のターン終了時まで、このシグニのパワーは＋3000され、このシグニは[[シャドウ（シグニ）]]を得る。この効果で公開したカードを好きな順番でデッキの一番下に置く。\n" +
                "@U：あなたのメインフェイズ開始時、対戦相手のシグニ１体を対象とし、%W %Xを支払ってもよい。そうした場合、それを手札に戻す。"
        );
        
        setName("en", "Casseopeia, Natural Planet Queen");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, reveal the top four cards of your deck. If there are three or more level one SIGNI among them, until the end of your opponent's next end phase, this SIGNI gets +3000 power and gains [[Shadow -- SIGNI]]. Put the revealed cards on the bottom of your deck in any order.\n" +
                "@U: At the beginning of your main phase, you may pay %W %X. If you do, return target SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Cassiopeia, Natural Star Princess");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, reveal the top 4 cards of your deck. If there are 3 or more level 1 SIGNI among them, until the end of your opponent's next turn, this SIGNI gets +3000 power, and [[Shadow (SIGNI)]]. Put the revealed this way cards on the bottom of your deck in any order.\n" +
                "@U: At the beginning of your main phase, target 1 of your opponent's SIGNI, and you may pay %W %X. If you do, return it to their hand."
        );
        
		setName("zh_simplified", "罗星姬 仙后座");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从你的牌组上面把4张牌公开。其中的等级1的精灵在3张以上的场合，直到下一个对战对手的回合结束时为止，这只精灵的力量+3000，这只精灵得到[[暗影（精灵）]]。这个效果公开的牌任意顺序放置到牌组最下面。\n" +
                "@U :你的主要阶段开始时，对战对手的精灵1只作为对象，可以支付%W%X。这样做的场合，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private void onAutoEff1()
        {
            reveal(4);
            
            if(new TargetFilter().own().SIGNI().withLevel(1).fromRevealed().getValidTargetsCount() >= 3)
            {
                gainPower(getCardIndex(), 3000, ChronoDuration.nextTurnEnd(getOpponent()));
                attachAbility(getCardIndex(), new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
            }
            
            while(getRevealedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromRevealed()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)))
            {
                addToHand(target);
            }
        }
    }
}
