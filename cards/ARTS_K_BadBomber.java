package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class ARTS_K_BadBomber extends Card {

    public ARTS_K_BadBomber()
    {
        setImageSets("WDK04-007");

        setOriginalName("バッド・ボンバー");
        setAltNames("バッドボンバー Baddo Bonba");
        setDescription("jp",
                "ターン終了時まで、対戦相手のすべてのシグニのパワーを－12000する。"
        );

        setName("en", "Bad Bomber");
        setDescription("en",
                "Until end of turn, all of your opponent's SIGNI get --12000 power."
        );

        setName("es", "Bad Bomber");
        setDescription("es",
                "Hasta el final del turno, todas las SIGNI oponentes pierden --12000 poder."
        );

        setName("zh_simplified", "恶性·投弹");
        setDescription("zh_simplified", 
                "直到回合结束时为止，对战对手的全部的精灵的力量-12000。"
        );

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
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
            forEachSIGNIOnField(getOpponent(), cardIndex -> gainPower(cardIndex, -12000, ChronoDuration.turnEnd()));
        }
    }
}
