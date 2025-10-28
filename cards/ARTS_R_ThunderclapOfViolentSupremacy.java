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

public final class ARTS_R_ThunderclapOfViolentSupremacy extends Card {

    public ARTS_R_ThunderclapOfViolentSupremacy()
    {
        setImageSets("WXK01-012");

        setOriginalName("烈覇迅雷");
        setAltNames("レッパジンライ Reppa Jinrai");
        setDescription("jp",
                "対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Thunderclap of Violent Supremacy");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 12000 or less, and banish it."
        );

        setName("zh_simplified", "烈霸迅雷");
        setDescription("zh_simplified", 
                "对战对手的力量12000以下的精灵1只作为对象，将其破坏。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
    }
}
