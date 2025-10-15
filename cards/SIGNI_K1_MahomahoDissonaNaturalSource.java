package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_MahomahoDissonaNaturalSource extends Card {

    public SIGNI_K1_MahomahoDissonaNaturalSource()
    {
        setImageSets("WXDi-P12-084", "SPDi27-05");

        setOriginalName("羅原　まほまほ//ディソナ");
        setAltNames("ラゲンマホマホディソナ Ragen Mahomaho Disona");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のデッキの上からカードを２枚トラッシュに置く。"
        );

        setName("en", "Mahomaho//Dissona, Natural Element");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, put the top two cards of your opponent's deck into their trash."
        );
        
        setName("en_fan", "Mahomaho//Dissona, Natural Source");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, put the top 2 cards of your opponent's deck into the trash."
        );

		setName("zh_simplified", "罗原 真帆帆//失调");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从对战对手的牌组上面把2张牌放置到废弃区。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            millDeck(getOpponent(), 2);
        }
    }
}
