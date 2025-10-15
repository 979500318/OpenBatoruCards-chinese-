package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.*;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public class SIGNI_K2_CodeRideMachinaDissona extends Card {

    public SIGNI_K2_CodeRideMachinaDissona()
    {
        setImageSets("WXDi-P13-086");

        setOriginalName("コードライド　マキナ//ディソナ");
        setAltNames("コードライドマキナディソナ Koodo Raido Makina Disona");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニの下に#Sのカードがある場合、対戦相手のデッキの上からカードを４枚トラッシュに置く。\n" +
                "@E：あなたのデッキの一番上のカードをこのシグニの下に置く。"
        );

        setName("en", "Machina//Dissona, Code: Ride");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is a #S card underneath this SIGNI, put the top four cards of your opponent's deck into their trash.\n@E: Put the top card of your deck under this SIGNI."
        );
        
        setName("en_fan", "Code Ride Machina//Dissona");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there is a #S @[Dissona]@ card under this SIGNI, your opponent puts the top 4 cards of their deck into the trash.\n" +
                "@E: Put the top card of your deck under this SIGNI."
        );

		setName("zh_simplified", "骑乘代号 玛琪娜//失调");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，这只精灵的下面有#S的牌的场合，从对战对手的牌组上面把4张牌放置到废弃区。\n" +
                "@E :你的牌组最上面的牌放置到这只精灵的下面。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().dissona().under(getCardIndex()).getValidTargetsCount() > 0)
            {
                millDeck(getOpponent(), 4);
            }
        }
        
        private void onEnterEff()
        {
            attach(getCardIndex(), CardLocation.DECK_MAIN, CardUnderType.UNDER_GENERIC);
        }
    }
}
