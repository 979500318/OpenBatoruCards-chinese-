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

public final class ARTS_G_SmallGainBigLoss extends Card {

    public ARTS_G_SmallGainBigLoss()
    {
        setImageSets("WXK01-025");

        setOriginalName("小利大損");
        setAltNames("ゲットウェーブ Getto Ueebu Get Wave");
        setDescription("jp",
                "対戦相手のパワー10000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Small Gain, Big Loss");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 10000 or more, and banish it."
        );

        setName("zh_simplified", "小利大损");
        setDescription("zh_simplified", 
                "对战对手的力量10000以上的精灵1只作为对象，将其破坏。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            banish(target);
        }
    }
}
