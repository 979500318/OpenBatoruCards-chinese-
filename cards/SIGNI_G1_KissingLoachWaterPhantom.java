package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_KissingLoachWaterPhantom extends Card {

    public SIGNI_G1_KissingLoachWaterPhantom()
    {
        setImageSets("WXDi-P15-094");

        setOriginalName("幻水　アユモド");
        setAltNames("ゲンスイアユモド Gensui Ayumodo");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの手札が６枚以上ある場合、【エナチャージ１】をする。"
        );

        setName("en", "Kissing Loach, Phantom Aquatic Beast");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you have six or more cards in your hand, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Kissing Loach, Water Phantom");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there are 6 or more cards in your hand, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "幻水 短薄鳅");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的手牌在6张以上的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
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
            if(getHandCount(getOwner()) >= 6)
            {
                enerCharge(1);
            }
        }
    }
}
