package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.stock.StockAbilityLancer;
import open.batoru.data.ability.stock.StockAbilitySLancer;

import java.util.List;

public final class SIGNI_G3_CodeOrderMelMemoria extends Card {

    public SIGNI_G3_CodeOrderMelMemoria()
    {
        setImageSets("WXDi-P09-044", "WXDi-P09-044P");

        setOriginalName("コードオーダー　メル//メモリア");
        setAltNames("コードオーダーメルメモリア Koodo Oodaa Meru Memoria");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを２枚このシグニの下に置く。\n" +
                "@A %G：あなたのデッキの上からカードを２枚見る。その中から１枚をこのシグニの下に置き、残りを好きな順番でデッキの一番下に置く。\n" +
                "@A @[このシグニの下から同名のカード２枚をトラッシュに置く]@：ターン終了時まで、このシグニは【ランサー】を得る。\n" +
                "@A #C：ターン終了時まで、このシグニのパワーを＋5000する。"
        );

        setName("en", "Mel//Memoria, Code: Order");
        setDescription("en",
                "@E: Put the top two cards of your deck under this SIGNI. \n" +
                "@A %G: Look at the top two cards of your deck. Put a card from among them under this SIGNI and put the rest on the bottom of your deck in any order.\n" +
                "@A @[Put two cards with the same name underneath this SIGNI into their owner's trash]@: This SIGNI gains [[Lancer]] until end of turn.\n" +
                "@A #C: This SIGNI gets +5000 power until end of turn."
        );
        
        setName("en_fan", "Code Order Mel//Memoria");
        setDescription("en_fan",
                "@E: Put the top 2 cards of your deck under this SIGNI.\n" +
                "@A %G: Look at the top 2 cards of your deck. Put 1 of them under this SIGNI, and put the rest on the bottom of your deck in any order.\n" +
                "@A @[Put 2 cards with the same name from under this SIGNI into the trash]@: Until end of turn, this SIGNI gains [[Lancer]].\n" +
                "@A #C: Until end of turn, this SIGNI gets +5000 power."
        );

		setName("zh_simplified", "点单代号 梅露//回忆");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把2张牌放置到这只精灵的下面。（表向放置）\n" +
                "@A %G:从你的牌组上面看2张牌。从中把1张放置到这只精灵的下面，剩下的任意顺序放置到牌组最下面。\n" +
                "@A 从这只精灵的下面把相同名字的牌2张放置到废弃区:直到回合结束时为止，这只精灵得到[[枪兵]]。\n" +
                "@A #C:直到回合结束时为止，这只精灵的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);

            registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 1)), this::onActionEff1);
            
            ActionAbility act2 = registerActionAbility(new TrashCost(2, new TargetFilter().under(cardId), this::onActionEff2CostCond), this::onActionEff2);
            act2.setCondition(this::onActionEff2Cond);
            
            registerActionAbility(new CoinCost(1), this::onActionEff3);
        }
        
        private void onEnterEff()
        {
            attach(getCardIndex(), CardLocation.DECK_MAIN, CardUnderType.UNDER_GENERIC, 2);
        }

        private void onActionEff1()
        {
            look(2);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.UNDER).own().fromLooked()).get();
            attach(getCardIndex(), cardIndex, CardUnderType.UNDER_GENERIC);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private ConditionState onActionEff2Cond()
        {
            if(getCardIndex().getIndexedInstance().getAbilityList().stream().anyMatch(ability ->
                ability.getSourceStockAbility() instanceof StockAbilityLancer ||
                ability.getSourceStockAbility() instanceof StockAbilitySLancer)) return ConditionState.WARN;
            
            DataTable<CardIndex> data = new TargetFilter().own().SIGNI().under(getCardIndex()).getExportedData();
            return data.size() != data.stream().map(c -> c.getCardReference().getOriginalName()).distinct().count() ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onActionEff2CostCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 2 && listPickedCards.get(0).getIndexedInstance().getName().getValue().contains(listPickedCards.get(1).getCardReference().getOriginalName());
        }
        private void onActionEff2()
        {
            attachAbility(getCardIndex(), new StockAbilityLancer(), ChronoDuration.turnEnd());
        }
        
        private void onActionEff3()
        {
            gainPower(getCardIndex(), 5000, ChronoDuration.turnEnd());
        }
    }
}
