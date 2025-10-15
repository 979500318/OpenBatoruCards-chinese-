package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class ARTS_W_ShiningSword extends Card {

    public ARTS_W_ShiningSword()
    {
        setImageSets("WX24-P1-018");

        setOriginalName("シャイニング・ソード");
        setAltNames("シャイニングソード Shainingu Soodo");
        setDescription("jp",
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Shining Sword");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 8000 or less, and put it into the trash."
        );

		setName("zh_simplified", "闪耀·刀剑");
        setDescription("zh_simplified", 
                "对战对手的力量8000以下的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().withPower(0,8000)).get();
            trash(target);
        }
    }
}

