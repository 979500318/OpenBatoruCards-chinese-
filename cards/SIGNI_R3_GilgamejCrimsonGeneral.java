package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class SIGNI_R3_GilgamejCrimsonGeneral extends Card {
    
    public SIGNI_R3_GilgamejCrimsonGeneral()
    {
        setImageSets("WXDi-P07-067");
        
        setOriginalName("紅将　ギルガメジ");
        setAltNames("ソウショウギルガメジ Soushou Girugameji");
        setDescription("jp",
                "=R あなたの赤のシグニ１体の上に置く\n\n" +
                "@E %R %R %R：ターン終了時まで、このシグニは【ダブルクラッシュ】を得る。\n" +
                "@A #C #C #C #C #C：ターン終了時まで、このシグニは【アサシン】を得る。"
        );
        
        setName("en", "Gilgamej, Crimson General");
        setDescription("en",
                "=R Place on top of a red SIGNI on your field. \n" +
                "@E %R %R %R: This SIGNI gains [[Double Crush]] until end of turn. \n" +
                "@A #C #C #C #C #C: This SIGNI gains [[Assassin]] until end of turn. "
        );
        
        setName("en_fan", "Gilgamej, Crimson General");
        setDescription("en_fan",
                "=R Put on 1 of your red SIGNI\n\n" +
                "@E %R %R %R: Until end of turn, this SIGNI gains [[Double Crush]].\n" +
                "@A #C #C #C #C #C: Until end of turn, this SIGNI gains [[Assassin]]."
        );
        
		setName("zh_simplified", "红将 吉尔伽美什");
        setDescription("zh_simplified", 
                "=R在你的红色的精灵1只的上面放置（这个条件没有满足则不能出场）\n" +
                "@E %R %R %R:直到回合结束时为止，这只精灵得到[[双重击溃]]。\n" +
                "@A #C #C #C #C #C:直到回合结束时为止，这只精灵得到[[暗杀]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
            
            setUseCondition(UseCondition.RISE, 1, new TargetFilter().withColor(CardColor.RED));
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 3)), this::onEnterEff);
            registerActionAbility(new CoinCost(5), this::onActionEff);
        }
        
        private void onEnterEff()
        {
            attachAbility(getCardIndex(), new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
        }
        
        private void onActionEff()
        {
            attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
        }
    }
}
