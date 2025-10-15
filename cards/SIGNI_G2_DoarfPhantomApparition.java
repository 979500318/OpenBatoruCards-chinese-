package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class SIGNI_G2_DoarfPhantomApparition extends Card {

    public SIGNI_G2_DoarfPhantomApparition()
    {
        setImageSets("WXK01-097");

        setOriginalName("幻怪　ドアーフ");
        setAltNames("ゲンカイドアーフ Genkai Doaafu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたがアーツを使用していた場合、カードを１枚引き、【エナチャージ１】をする。"
        );

        setName("en", "Doarf, Phantom Apparition");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you used ARTS this turn, draw 1 card, and [[Ener Charge 1]]."
        );

		setName("zh_simplified", "幻怪 侏儒");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合你把必杀使用过的场合，抽1张牌，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(2);
        setPower(3000);

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
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller())) > 0)
            {
                draw(1);
                enerCharge(1);
            }
        }
    }
}
