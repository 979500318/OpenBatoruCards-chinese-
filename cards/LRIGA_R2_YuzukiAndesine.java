package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_R2_YuzukiAndesine extends Card {

    public LRIGA_R2_YuzukiAndesine()
    {
        setImageSets("WXDi-P10-026");

        setOriginalName("遊月・アンデシン");
        setAltNames("ユヅキアンデシン Yuzuki Andeshin");
        setDescription("jp",
                "@E：対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %X %X：対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカードを２枚まで対象とし、それらをトラッシュに置く。"
        );

        setName("en", "Yuzuki Andesine");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 12000 or less.\n" +
                "@E %X %X: Put up to two target cards from your opponent's Ener Zone that does not share a color with your opponent's Center LRIG into their trash."
        );
        
        setName("en_fan", "Yuzuki Andesine");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 12000 or less, and banish it.\n" +
                "@E %X %X: Target up to 2 cards from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put them into the trash."
        );

		setName("zh_simplified", "游月·中长石");
        setDescription("zh_simplified", 
                "@E :对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n" +
                "@E %X %X:从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌2张最多作为对象，将这些放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.YUZUKI);
        setColor(CardColor.RED);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
        private void onEnterEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner());
            trash(data);
        }
    }
}
