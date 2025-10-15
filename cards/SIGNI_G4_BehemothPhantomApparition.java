package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class SIGNI_G4_BehemothPhantomApparition extends Card {

    public SIGNI_G4_BehemothPhantomApparition()
    {
        setImageSets("WXK01-092");

        setOriginalName("幻怪　ベヒーモス");
        setAltNames("ゲンカイベヒーモス Genkai Behiimosu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたがアーツを使用していた場合、%G %G %Gを支払ってもよい。そうした場合、あなたのデッキの一番上のカードをライフクロスに加える。"
        );

        setName("en", "Behemoth, Phantom Apparition");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you used ARTS this turn, you may pay %G %G %G. If you do, add the top card of your deck to life cloth."
        );

		setName("zh_simplified", "幻怪 贝希摩斯");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合你把必杀使用过的场合，可以支付%G %G %G。这样做的场合，你的牌组最上面的牌加入生命护甲。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(4);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY);
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
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller())) > 0 &&
               payEner(Cost.color(CardColor.GREEN, 3)))
            {
                addToLifeCloth(1);
            }
        }
    }
}
