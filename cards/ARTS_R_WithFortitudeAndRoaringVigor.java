package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;
import open.batoru.data.ability.stock.StockAbilityTripleCrush;

public final class ARTS_R_WithFortitudeAndRoaringVigor extends Card {

    public ARTS_R_WithFortitudeAndRoaringVigor()
    {
        setImageSets("WDK01-007");

        setOriginalName("質実轟健");
        setAltNames("シツジツゴウケン Shitsujitsu Gouken");
        setDescription("jp",
                "@[ベット]@ -- #C\n\n" +
                "あなたがベットする場合、このアーツの使用コストは%R0になる。\n\n" +
                "あなたのドライブ状態のシグニ１体を対象とし、ターン終了時まで、それは【ダブルクラッシュ】を得る。そのシグニがレベル３以上の場合、代わりにターン終了時まで、それは【トリプルクラッシュ】を得る。"
        );

        setName("en", "With Fortitude and Roaring Vigor");
        setDescription("en",
                "@[Bet]@ -- #C\n\n" +
                "If you bet, the cost for using this ARTS becomes %R0.\n\n" +
                "Target 1 of your SIGNI in the drive state, and until end of turn, it gains [[Double Crush]]. If that SIGNI is level 3 or higher, it gains [[Triple Crush]] instead."
        );

        setName("es", "Con Fortaleza y un Vigor Rugiente.");
        setDescription("es",
                "@[Apostar]@ -- #C\n\n" +
                "Si apostaste, el costo de usar esta ARTS se vuelve %R0.\n\n" +
                "Selecciona 1 SIGNI propia que este conduciendo, y hasta el final del turno, esta gana [[Double Crush]]. Si esa SIGNI es nivel 3 o mayor, en cambio, gana [[Triple Crush]]"
        );

        setName("zh_simplified", "质实轰健");
        setDescription("zh_simplified", 
                "下注—#C\n" +
                "你下注的场合，这张必杀的使用费用变为%R0。\n" +
                "你的驾驶状态的精灵1只作为对象，直到回合结束时为止，其得到[[双重击溃]]。那只精灵在等级3以上的场合，作为替代，直到回合结束时为止，其得到[[三重击溃]]。"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setBetCost(new CoinCost(1), Cost.color(CardColor.RED, 0), ModifierMode.SET);
        }
        
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().drive()).get();
            if(target != null) attachAbility(target, target.getIndexedInstance().getLevel().getValue() < 3 ? new StockAbilityDoubleCrush() : new StockAbilityTripleCrush(), ChronoDuration.turnEnd());
        }
    }
}
