package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_R3_MarsCrimsonAngelPrincess extends Card {
    
    public SIGNI_R3_MarsCrimsonAngelPrincess()
    {
        setImageSets("WXDi-P00-035");
        
        setOriginalName("紅天姫　マルス");
        setAltNames("コウテンキマルス Koutenki Marusu");
        setDescription("jp",
                "@U：このシグニが対戦相手のシグニ１体をバニッシュしたとき、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。\n" +
                "@E @[手札から赤のカードを３枚捨てる]@：ターン終了時まで、このシグニは[[アサシン]]を得る。"
        );
        
        setName("en", "Mars, Crimson Angel Queen");
        setDescription("en",
                "@U: Whenever this SIGNI vanishes a SIGNI on your opponent's field, put target card from your opponent's Ener Zone that doesn't share a color with your opponent's center LRIG into its owner's trash.\n" +
                "@E @[Discard three red cards from your hand]@: This SIGNI gains [[Assassin]] until end of turn."
        );
        
        setName("en_fan", "Mars, Crimson Angel Princess");
        setDescription("en_fan",
                "@U: Whenever this SIGNI banishes 1 of your opponent's SIGNI, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash.\n" +
                "@E @[Discard 3 red cards from your hand]@: Until end of turn, this SIGNI gains [[Assassin]]."
        );
        
		setName("zh_simplified", "红天姬 玛尔斯");
        setDescription("zh_simplified", 
                "@U :当这只精灵把对战对手的精灵1只破坏时，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。\n" +
                "@E 从手牌把红色的牌3张舍弃:直到回合结束时为止，这只精灵得到[[暗杀]]。（这只精灵攻击，不与正面的精灵进行战斗，给予对战对手伤害）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new DiscardCost(3, new TargetFilter().withColor(CardColor.RED)), this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex cardIndex)
        {
            return !isOwnCard(cardIndex) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex cardIndex)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor()))).get();
            trash(target);
        }
        
        private void onEnterEff()
        {
            attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
        }
    }
}
