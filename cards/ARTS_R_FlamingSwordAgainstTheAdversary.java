package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class ARTS_R_FlamingSwordAgainstTheAdversary extends Card {

    public ARTS_R_FlamingSwordAgainstTheAdversary()
    {
        setImageSets("WX24-P1-021");

        setOriginalName("剣一炎敵");
        setAltNames("ケンイチエンテキ Kenichi Enteki");
        setDescription("jp",
                "対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Flaming Sword Against the Adversary");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );

        setName("zh_simplified", "剑一炎敌");
        setDescription("zh_simplified", 
                "对战对手的力量10000以下的精灵1只作为对象，将其破坏。"
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
    }
}

