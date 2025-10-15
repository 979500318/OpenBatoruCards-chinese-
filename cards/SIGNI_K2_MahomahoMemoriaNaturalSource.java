package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K2_MahomahoMemoriaNaturalSource extends Card {

    public SIGNI_K2_MahomahoMemoriaNaturalSource()
    {
        setImageSets("WXDi-P09-081", "WXDi-P09-081P", "SPDi01-97");

        setOriginalName("羅原　まほまほ//メモリア");
        setAltNames("ラゲンマホマホメモリアル Ragen Mahomaho Memoria");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手が手札を１枚捨てないかぎり、対戦相手のデッキの上からカードを４枚トラッシュに置く。"
        );

        setName("en", "Mahomaho//Memoria, Natural Element");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, put the top four cards of your opponent's deck into their trash unless your opponent discards a card."
        );
        
        setName("en_fan", "Mahomaho//Memoria, Natural Source");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, put the top 4 cards of your opponent's deck into the trash unless your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "罗原 真帆帆//回忆");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，如果对战对手不把手牌1张舍弃，那么从对战对手的牌组上面把4张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(2);
        setPower(5000);

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
            if(discard(getOpponent(), 0,1).get() == null)
            {
                millDeck(getOpponent(), 4);
            }
        }
    }
}
