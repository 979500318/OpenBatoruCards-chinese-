package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

import java.util.List;

public final class ARTS_B_AzureWish extends Card {

    public ARTS_B_AzureWish()
    {
        setImageSets("WX24-P2-006", "WX24-P2-006U");

        setOriginalName("アズール・ウィッシュ");
        setAltNames("アズールウィッシュ Azuuru Uisshu");
        setDescription("jp",
                "このアーツを使用する際、あなたのルリグデッキから青のアーツ１枚をルリグトラッシュに置いてもよい。そうした場合、このアーツの使用コストは%B %B %B減る。\n\n" +
                "レベルの合計が５以下になるように対戦相手のシグニを２体まで対象とし、それらをダウンする。"
        );

        setName("en", "Azure Wish");
        setDescription("en",
                "While using this ARTS, you may put 1 blue ARTS from your LRIG deck into the LRIG trash. If you do, the use cost of this ARTS is reduced by %B %B %B.\n\n" +
                "Target up to 2 of your opponent's SIGNI with a total level of 5 or lower, and down them."
        );

		setName("zh_simplified", "碧蓝·愿望");
        setDescription("zh_simplified", 
                "这张必杀使用时，可以从你的分身牌组把蓝色的必杀1张放置到分身废弃区。这样做的场合，这张必杀的使用费用减%B %B %B。\n" +
                "等级的合计在5以下的对战对手的精灵2只最多作为对象，将这些横置。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 3));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setReductionCost(new TrashCost(new TargetFilter().own().ARTS().withColor(CardColor.BLUE).except(getCardIndex()).fromLocation(CardLocation.DECK_LRIG)), Cost.color(CardColor.BLUE, 3));
        }
        
        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI(), this::onARTSEffTargetCond);
            down(data);
        }
        private boolean onARTSEffTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.isEmpty() || listPickedCards.stream().map(cardIndex -> cardIndex.getIndexedInstance().getLevel().getValue()).reduce(Integer::sum).get() <= 5;
        }
    }
}
