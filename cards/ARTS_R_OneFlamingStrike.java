package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_R_OneFlamingStrike extends Card {

    public ARTS_R_OneFlamingStrike()
    {
        setImageSets("WX24-P2-034");

        setOriginalName("炎打一振");
        setAltNames("エンダイッシン Endaisshin");
        setDescription("jp",
                "対戦相手のセンタールリグと共通する色を持たない対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "One Flaming Strike");
        setDescription("en",
                "Target 1 of your opponent's SIGNI that doesn't share a common color with your opponent's center LRIG, and banish it."
        );

        setName("zh_simplified", "炎打一振");
        setDescription("zh_simplified", 
                "不持有与对战对手的核心分身共通颜色的对战对手的精灵1只作为对象，将其破坏。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
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

            registerARTSAbility(this::onARTSEff);
        }
        
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor()))).get();
            banish(target);
        }
    }
}
