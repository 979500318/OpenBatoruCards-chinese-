package open.batoru.data.cards;

import open.batoru.catalog.description.DescriptionParser;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_CurieHolyWisdomPrincess extends Card {

    public SIGNI_W3_CurieHolyWisdomPrincess()
    {
        setImageSets("WX24-P1-040");

        setOriginalName("聖英姫　キュリー");
        setAltNames("セイエイキキュリー Seieiki Kyurii");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのアップ状態のルリグ１体をダウンしてもよい。その後、この方法でダウンしたルリグと同じレベルの対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それを手札に戻す。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたのアップ状態のルリグ１体をダウンしてもよい。次の対戦相手のターン終了時まで、このシグニは[[シャドウ（{{この方法でダウンしたルリグと同じレベルのシグニ$レベル%1のシグニ}}）]]を得る。"
        );

        setName("en", "Curie, Holy Wisdom Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may down 1 of your upped LRIG. Then, you may target 1 of your opponent's SIGNI with the same level as the LRIG downed this way, and pay %X. If you do, return it to their hand.\n" +
                "@U: At the beginning of your attack phase, you may down 1 of your upped LRIG. Until the end of your opponent's next turn, this SIGNI gains [[Shadow ({{SIGNI with the same level as the LRIG downed this way$level %1 SIGNI}})]]."
        );

		setName("zh_simplified", "圣英姬 居里夫人");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以把你的竖直状态的分身1只横置。然后，与这个方法横置的分身相同等级的对战对手的精灵1只作为对象，可以支付%X。这样做的场合，将其返回手牌。\n" +
                "@U :你的攻击阶段开始时，可以把你的竖直状态的分身1只横置。直到下一个对战对手的回合结束时为止，这只精灵得到[[暗影（与这个方法横置的分身相同等级的精灵）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
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
            auto1.setCondition(this::onAutoEffCond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.DOWN).own().anyLRIG()).get();
            
            if(down(cardIndex))
            {
                CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(cardIndex.getIndexedInstance().getLevel().getValue())).get();
                
                if(target != null && payEner(Cost.colorless(1)))
                {
                    addToHand(target);
                }
            }
        }
        
        private int cachedLevel;
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.DOWN).own().anyLRIG()).get();

            if(down(cardIndex))
            {
                cachedLevel = cardIndex.getIndexedInstance().getLevel().getValue();
                StockAbilityShadow attachedStock = new StockAbilityShadow(this::onAttachedStockEffAddCond, () -> DescriptionParser.formatNumber(cachedLevel));
                
                attachAbility(getCardIndex(), attachedStock, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                    cardIndexSource.getIndexedInstance().getLevel().getValue() == cachedLevel ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
