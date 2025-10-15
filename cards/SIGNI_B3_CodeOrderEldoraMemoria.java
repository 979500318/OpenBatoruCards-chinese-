package open.batoru.data.cards;

import open.batoru.catalog.description.DescriptionParser;
import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

import java.util.List;
import java.util.StringJoiner;

public final class SIGNI_B3_CodeOrderEldoraMemoria extends Card {

    public SIGNI_B3_CodeOrderEldoraMemoria()
    {
        setImageSets("WXDi-P10-040", "WXDi-P10-040P");

        setOriginalName("コードオーダー　エルドラ//メモリア");
        setAltNames("コードオーダーエルドラメモリア Koodo Oodaa Erudora Memoria");
        setDescription("jp",
                "@C：[[シャドウ（{{このシグニの下にあるシグニと同じレベルのシグニ$レベル%1のシグニ}}）]]\n" +
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、このシグニの下からスペルを好きな枚数トラッシュに置く。ターン終了時まで、それのパワーをこの方法でトラッシュに置いたカード１枚につき－5000する。\n" +
                "@A $T1 %B0：あなたのデッキの上からカードを３枚見る。その中からスペルと青のシグニをそれぞれ１枚までこのシグニの下に置き、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Eldora//Memoria, Code: Order");
        setDescription("en",
                "@C: [[Shadow -- {{SIGNI with the same level as the SIGNI underneath this SIGNI$Level %1 SIGNI}}]]\n" +
                "@U: Whenever this SIGNI attacks, put any number of spells underneath this SIGNI into their owner's trash. Target SIGNI on your opponent's field gets --5000 power for each card put into the trash this way until end of turn.\n" +
                "@A $T1 %B0: Look at the top three cards of your deck. Put up to one spell and one blue SIGNI from among them under this SIGNI, and put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Code Order Eldora Memoria");
        setDescription("en_fan",
                "@C: [[Shadow ({{SIGNI with the same level as the SIGNI under this SIGNI$level %1 SIGNI}})]]\n" +
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may put any number of spells from under this SIGNI into the trash. Until end of turn, it gets --5000 power for each card put into the trash this way.\n" +
                "@A $T1 %B0: Look at the top 3 cards of your deck. Put up to 1 spell and up to 1 blue SIGNI from among them under this SIGNI, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "点单代号 艾尔德拉//回忆");
        setDescription("zh_simplified", 
                "@C :[[暗影（与这只精灵的下面的精灵相同等级的精灵）]]\n" +
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，从这只精灵的下面把魔法任意张数放置到废弃区。直到回合结束时为止，其的力量依据这个方法放置到废弃区的牌的数量，每有1张就-5000。\n" +
                "@A $T1 %B0:从你的牌组上面看3张牌。从中把魔法和蓝色的精灵各1张最多放置到这只精灵的下面，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().under(getCardIndex()).except(CardType.SPELL).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond, this::onAttachedStockEffDynamicDesc));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   new TargetFilter().own().SIGNI().under(getCardIndex()).except(CardType.SPELL).withLevel(cardIndexSource.getIndexedInstance().getLevel().getValue()).getValidTargetsCount() > 0 ?
                    ConditionState.OK : ConditionState.BAD;
        }
        private String onAttachedStockEffDynamicDesc()
        {
            DataTable<CardIndex> data = new TargetFilter().own().SIGNI().under(getCardIndex()).except(CardType.SPELL).getExportedData();
            if(data.isEmpty()) return "";
            if(data.size() == 1) return DescriptionParser.formatNumber(data.get().getIndexedInstance().getLevel().getValue());

            StringJoiner result = new StringJoiner(DescriptionParser.formatSeparator());
            List<Integer> levels = data.stream().map(cardIndex -> cardIndex.getIndexedInstance().getLevel().getValue()).distinct().toList();
            for(Integer level : levels) result.add(DescriptionParser.formatNumber(level));
            return result.toString();
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0, AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.TRASH).own().spell().under(getCardIndex()));
                
                if(data.get() != null) gainPower(target, -5000 * trash(data), ChronoDuration.turnEnd());
            }
        }

        private void onActionEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.UNDER).own().spell().fromLooked()).get();
            attach(getCardIndex(), cardIndex, CardUnderType.UNDER_GENERIC);
            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.UNDER).own().SIGNI().withColor(CardColor.BLUE).fromLooked()).get();
            attach(getCardIndex(), cardIndex, CardUnderType.UNDER_GENERIC);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
