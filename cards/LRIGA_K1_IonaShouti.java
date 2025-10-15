package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K1_IonaShouti extends Card {

    public LRIGA_K1_IonaShouti()
    {
        setImageSets("WXDi-P13-033");

        setOriginalName("イオナ・シャウティ");
        setAltNames("イオナシャウティ Iona Shauti");
        setDescription("jp",
                "@E：ターン終了時まで、対戦相手のすべてのシグニのパワーを－3000する。"
        );

        setName("en", "Iona Shouty");
        setDescription("en",
                "@E: All SIGNI on your opponent's field get --3000 power until end of turn. "
        );
        
        setName("en_fan", "Iona Shouti");
        setDescription("en_fan",
                "@E: Until end of turn, all of your opponent's SIGNI get --3000 power."
        );

		setName("zh_simplified", "伊绪奈·窈窕");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，对战对手的全部的精灵的力量-3000。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.BLACK);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            gainPower(getSIGNIOnField(getOpponent()), -3000, ChronoDuration.turnEnd());
        }
    }
}
