package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_StinkBugPhantomInsect extends Card {

    public SIGNI_G1_StinkBugPhantomInsect()
    {
        setImageSets("SPDi01-134", "SPDi01-134P");

        setOriginalName("幻蟲　カメムシ");
        setAltNames("ゲンチュウカメムシ Genchuu Kamemushi");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場にあるシグニが持つ色が合計２種類以上ある場合、【エナチャージ１】をする。"
        );

        setName("en", "Stink Bug, Phantom Insect");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there are 2 or more colors among SIGNI on your field, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "幻虫 椿象虫");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上的精灵持有颜色合计2种类以上的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(1);
        setPower(2000);

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
            if(getColorsCount(getSIGNIOnField(getOwner())) >= 2)
            {
                enerCharge(1);
            }
        }
    }
}
