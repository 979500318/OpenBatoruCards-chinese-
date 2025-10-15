package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R1_CodeRidePockebi extends Card {

    public SIGNI_R1_CodeRidePockebi()
    {
        setImageSets("WXK01-078");

        setOriginalName("コードライド　ポケバイ");
        setAltNames("コードライドポケバイ Koodo Raido Pokebi");
        setDescription("jp",
                "@U：このシグニがドライブ状態になったとき、カードを１枚引き、手札を１枚捨てる。"
        );

        setName("en", "Code Ride Pockebi");
        setDescription("en",
                "@U: When this SIGNI enters the drive state, draw 1 card, and discard 1 card from your hand."
        );

		setName("zh_simplified", "骑乘代号 袖珍自行车");
        setDescription("zh_simplified", 
                "@U :当这只精灵变为驾驶状态时，抽1张牌，手牌1张舍弃。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.DRIVE, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            draw(1);
            discard(1);
        }
    }
}
