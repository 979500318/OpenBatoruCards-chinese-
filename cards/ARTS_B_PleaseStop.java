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
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst;

public final class ARTS_B_PleaseStop extends Card {

    public ARTS_B_PleaseStop()
    {
        setImageSets("WX24-P2-036");

        setOriginalName("プリーズ・ストップ");
        setAltNames("プリーズストップ Puriizu Sutoppu");
        setDescription("jp",
                "手札からシグニを好きな枚数捨てる。その後、この方法で捨てたシグニ１枚につきそのシグニと同じレベルの対戦相手のシグニ１体を対象とし、それらをダウンする。"
        );

        setName("en", "Please Stop");
        setDescription("en",
                "Discard any number of SIGNI from your hand. Then, for each SIGNI discarded this way, target 1 of your opponent's SIGNI with the same level as that SIGNI, and down them."
        );

		setName("zh_simplified", "压迫·阻碍");
        setDescription("zh_simplified", 
                "从手牌把精灵任意张数舍弃。然后，依据这个方法舍弃的精灵的数量，每有1张就把与那张精灵相同等级的对战对手的精灵1只作为对象，将这些横置。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(3));
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
            DataTable<CardIndex> data = discard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter().SIGNI());
            if(data != null)
            {
                for(int i=0;i<data.size();i++)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI().withLevel(data.get(i).getIndexedInstance().getLevelByRef())).get();
                    down(target);
                }
            }
        }
    }
}
