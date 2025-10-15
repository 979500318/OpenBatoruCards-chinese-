package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class SIGNI_R3_NobunagaCrimsonGeneralPrincess extends Card {
    
    public SIGNI_R3_NobunagaCrimsonGeneralPrincess()
    {
        setImageSets("WXDi-P01-036");
        
        setOriginalName("紅将姫　ノブナガ");
        setAltNames("コウショウキノブナガ Koushouki Nobunaga");
        setDescription("jp",
                "=R あなたの赤のシグニ１体の上に置く\n\n" +
                "@E %R：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A %R %R：ターン終了時まで、このシグニは[[ダブルクラッシュ]]を得る。"
        );
        
        setName("en", "Nobunaga, Crimson General Queen");
        setDescription("en",
                "=R Place on top of a red SIGNI on your field.\n\n" +
                "@E %R: Vanish target SIGNI on your opponent's field with power 8000 or less.\n" +
                "@A %R %R: This SIGNI gains [[Double Crush]] until end of turn."
        );
        
        setName("en_fan", "Nobunaga, Crimson General Princess");
        setDescription("en_fan",
                "=R Put on 1 of your red SIGNI\n\n" +
                "@E %R: Target 1 of your opponent's SIGNI with power 8000 or less, and banish it.\n" +
                "@A %R %R: Until end of turn, this SIGNI gains [[Double Crush]]."
        );
        
		setName("zh_simplified", "红将姬 信长");
        setDescription("zh_simplified", 
                "=R在你的红色的精灵1只的上面放置（这个条件没有满足则不能出场）\n" +
                "@E %R:对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n" +
                "@A %R %R:直到回合结束时为止，这只精灵得到[[双重击溃]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(13000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            setUseCondition(UseCondition.RISE, 1, new TargetFilter().withColor(CardColor.RED));
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1)), this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 2)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }

        private ConditionState onActionEffCond()
        {
            return getCardIndex().getIndexedInstance().getAbilityList().stream().anyMatch(ability -> ability.getSourceStockAbility() instanceof StockAbilityDoubleCrush) ? ConditionState.WARN : ConditionState.OK;
        }
        private void onActionEff()
        {
            attachAbility(getCardIndex(), new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
        }
    }
}
