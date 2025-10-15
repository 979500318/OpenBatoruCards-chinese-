package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityRide;

public final class LRIG_R4_LaylaFullThrottle extends Card {

    public LRIG_R4_LaylaFullThrottle()
    {
        setImageSets("WDK01-001");

        setOriginalName("レイラ＝フルスロットル");
        setAltNames("レイラフルスロットル Reira Furu Surottoru");
        setDescription("jp",
                "=I\n" +
                "@E %R：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A #C：あなたのデッキから＜乗機＞のシグニ１枚を探して場に出し、デッキをシャッフルする。"
        );

        setName("en", "Layla-Full Throttle");
        setDescription("en",
                "=I\n" +
                "@E %R: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@A #C: Search your deck for 1 <<Riding Machine>> SIGNI, put it onto the field, and shuffle your deck."
        );

		setName("zh_simplified", "蕾拉=全速前进");
        setDescription("zh_simplified", 
                "[[骑乘]]\n" +
                "@E %R:对战对手的精灵1只作为对象，将其破坏。\n" +
                "@A #C:从你的牌组找<<乗機>>精灵1张出场，牌组洗切。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setLevel(4);
        setLimit(11);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerStockAbility(new StockAbilityRide());
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1)), this::onEnterEff);
            
            registerActionAbility(new CoinCost(1), this::onActionEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = searchDeck(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.RIDING_MACHINE).playable()).get();
            putOnField(cardIndex);
            
            shuffleDeck();
        }
    }
}
