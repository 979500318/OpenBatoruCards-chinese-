package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class ARTS_R_BlazingStraightPunch extends Card {

    public ARTS_R_BlazingStraightPunch()
    {
        setImageSets("WX24-P1-019");

        setOriginalName("正拳突火");
        setAltNames("セイケンヅカ Seikenzuka");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、それをバニッシュする。その後、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカードを３枚まで対象とし、それらをトラッシュに置く。"
        );

        setName("en", "Blazing Straight Punch");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and banish it. Then, target up to 3 cards from your opponent's ener zone that don't share a common color with your opponent's center LRIG, and put them into the trash."
        );

		setName("zh_simplified", "正拳突火");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，将其破坏。然后，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌3张最多作为对象，将这些放置到废弃区。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);

            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner());
            trash(data);
        }
    }
}

