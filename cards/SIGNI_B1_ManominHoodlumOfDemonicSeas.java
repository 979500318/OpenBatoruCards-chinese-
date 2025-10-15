package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_ManominHoodlumOfDemonicSeas extends Card {

    public SIGNI_B1_ManominHoodlumOfDemonicSeas()
    {
        setImageSets("WDK02-017");

        setOriginalName("魔海の不良　マノミン");
        setAltNames("マカイノフリョウマノミン Makai no Furyou Manomin");
        setDescription("jp",
                "@E：各プレイヤーは手札を１枚捨てる。" +
                "~#：カードを１枚引く。"
        );

        setName("en", "Manomin, Hoodlum of Demonic Seas");
        setDescription("en",
                "@E: Each player discards 1 card from their hand." +
                "~#Draw 1 card."
        );

		setName("zh_simplified", "魔界的不良 粉双带");
        setDescription("zh_simplified", 
                "@E :各玩家把手牌1张舍弃。" +
                "~#抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
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
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            discard(1);
            discard(getOpponent(), 1);
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
        }
    }
}
