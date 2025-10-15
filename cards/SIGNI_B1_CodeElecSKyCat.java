package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_CodeElecSKyCat extends Card {

    public SIGNI_B1_CodeElecSKyCat()
    {
        setImageSets("WX24-D3-11");

        setOriginalName("コードエレキ　Ｓカイキャット");
        setAltNames("コードエレキエスカイキャット Koodo Ereki Esu Kai Kyatto");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたが青のアーツを使用していた場合、カードを１枚引く。"
        );

        setName("en", "Code Elec S Ky Cat");
        setDescription("en",
                "@U: When this SIGNI attacks, if you used a blue ARTS this turn, draw 1 card."
        );

		setName("zh_simplified", "电动代号 猫爬架");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合你把蓝色的必杀使用过的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller()) && event.getCaller().getColor().matches(CardColor.BLUE)) > 0)
            {
                draw(1);
            }
        }
    }
}
