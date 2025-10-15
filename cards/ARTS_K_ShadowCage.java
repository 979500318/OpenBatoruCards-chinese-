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

public final class ARTS_K_ShadowCage extends Card {

    public ARTS_K_ShadowCage()
    {
        setImageSets("WX24-P2-040");

        setOriginalName("シャドウ・ケージ");
        setAltNames("シャドウケージ Shadou Keeji");
        setDescription("jp",
                "対戦相手のトラッシュにあるいずれかのカードと同じ名前の、対戦相手のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Shadow Cage");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with the same name as any card in your opponent's trash, and put it into the trash."
        );

		setName("zh_simplified", "暗影·牢笼");
        setDescription("zh_simplified", 
                "与对战对手的废弃区的任一张的牌相同名字的，对战对手的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
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
            String[] names = getCardsInTrash(getOpponent()).stream().map(cardIndex -> cardIndex.getIndexedInstance().getName().getValue()).toArray(String[]::new);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().withName(names)).get();
            trash(target);
        }
    }
}
