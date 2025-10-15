package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_KogenbuPhantomWarBeast extends Card {

    public SIGNI_G1_KogenbuPhantomWarBeast()
    {
        setImageSets("WX24-D4-11");

        setOriginalName("幻闘獣　コゲンブ");
        setAltNames("ゲントウジュウコゲンブ Gentoujuu Kogenbu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたが緑のアーツを使用していた場合、【エナチャージ１】をする。"
        );

        setName("en", "Kogenbu, Phantom War Beast");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you used a green ARTS this turn, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "幻斗兽 小玄武");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合你把绿色的必杀使用过的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller()) && event.getCaller().getColor().matches(CardColor.GREEN)) > 0)
            {
                enerCharge(1);
            }
        }
    }
}
