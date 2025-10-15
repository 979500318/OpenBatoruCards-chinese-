package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class ARTS_G_RealizationOfAGreatAmbition extends Card {

    public ARTS_G_RealizationOfAGreatAmbition()
    {
        setImageSets("WDK03-007");

        setOriginalName("大願成就");
        setAltNames("ガンマバースト Ganma Baasuto Gamma Burst");
        setDescription("jp",
                "対戦相手のすべてのシグニをトラッシュに置く。"
        );

        setName("en", "Realization of a Great Ambition");
        setDescription("en",
                "Put all of your opponent's SIGNI from the field into the trash."
        );

		setName("zh_simplified", "大愿成就");
        setDescription("zh_simplified", 
                "对战对手的全部的精灵放置到废弃区。\n"
        );

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 3) + Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

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
            trash(getSIGNIOnField(getOpponent()));
        }
    }
}
