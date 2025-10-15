package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B1_LeviathanSeaSnakeOfDemonicSeas extends Card {

    public SIGNI_B1_LeviathanSeaSnakeOfDemonicSeas()
    {
        setImageSets("WXK01-089");

        setOriginalName("魔海の海蛇　レヴィアタン");
        setAltNames("Mマカイノウミヘビレヴィアタン akai no Umihebi Reviatan");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：このターン終了時、カードを１枚引く。"
        );

        setName("en", "Leviathan, Sea Snake of Demonic Seas");
        setDescription("en",
                "@E @[Discard 1 card from your hand]@: At the end of this turn, draw 1 card."
        );

		setName("zh_simplified", "魔海的海蛇 利维坦");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:这个回合结束时，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(1000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new DiscardCost(1), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            callDelayedEffect(ChronoDuration.turnEnd(), () -> draw(1));
        }
    }
}
