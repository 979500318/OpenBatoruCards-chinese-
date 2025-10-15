package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_YamabikoPhantomApparition extends Card {

    public SIGNI_G1_YamabikoPhantomApparition()
    {
        setImageSets("WXK01-061");

        setOriginalName("幻怪　ヤマビコ");
        setAltNames("ゲンカイヤマビコ Genkai Yamabiko");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニのパワーが3000以上の場合、【エナチャージ１】をする。" +
                "~#：カードを１枚引く。"
        );

        setName("en", "Yamabiko, Phantom Apparition");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if this SIGNI's power is 3000 or more, [[Ener Charge 1]]." +
                "~#Draw 1 card."
        );

		setName("zh_simplified", "幻怪 山彦");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这只精灵的力量在3000以上的场合，[[能量填充1]]。" +
                "~#抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(1);
        setPower(1000);

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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            if(getPower().getValue() >= 3000)
            {
                enerCharge(1);
            }
        }

        private void onLifeBurstEff()
        {
            draw(1);
        }
    }
}
