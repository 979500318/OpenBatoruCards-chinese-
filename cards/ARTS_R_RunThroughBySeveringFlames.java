package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_R_RunThroughBySeveringFlames extends Card {

    public ARTS_R_RunThroughBySeveringFlames()
    {
        setImageSets("WX24-D2-05", "SPDi37-15");

        setOriginalName("断炎轢断");
        setAltNames("ダンエンレキダン Danen Rekidan");
        setDescription("jp",
                "対戦相手のライフクロス１枚をクラッシュする。"
        );

        setName("en", "Run Through by Severing Flames");
        setDescription("en",
                "Crush 1 of your opponent's life cloth."
        );

        setName("es", "Corriendo a travez de llamas cortantes");
        setDescription("es",
                "Destruye 1 Life Cloth oponente."
        );

        setName("zh_simplified", "断炎轹断");
        setDescription("zh_simplified", 
                "对战对手的生命护甲1张击溃。"
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
            crush(getOpponent());
        }
    }
}

