package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_CodeEatCandyApple extends Card {

    public SIGNI_G1_CodeEatCandyApple()
    {
        setImageSets("WX25-P1-089");

        setOriginalName("コードイート　リンゴアメ");
        setAltNames("コードイートリンゴアメ Koodo Iito Ringoame");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニの下にあるシグニ1枚を対象とし、あなたのエナゾーンにそれと共通するクラスを持つシグニがない場合、それをエナゾーンに置く。\n" +
                "@E：あなたのデッキの一番上のカードをこのシグニの下に置く。"
        );

        setName("en", "Code Eat Candy Apple");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, target 1 SIGNI under this SIGNI, and if there are no SIGNI in your ener zone that share a common class with that SIGNI, put it into the ener zone.\n" +
                "@E: Put the top card of your deck under this SIGNI."
        );

		setName("zh_simplified", "食用代号 苹果糖");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这只精灵的下面的精灵1张作为对象，你的能量区不持有与其共通类别的精灵的场合，将其放置到能量区。\n" +
                "@E :你的牌组最上面的牌放置到这只精灵的下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
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

            registerEnterAbility(this::onEnterEff);
        }

        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).own().SIGNI().under(getCardIndex()).except(CardType.SPELL)).get();
            
            if(target != null &&
               new TargetFilter().own().SIGNI().fromEner().withClass(target.getIndexedInstance().getSIGNIClass()).getValidTargetsCount() == 0)
            {
                putInEner(target);
            }
        }

        private void onEnterEff()
        {
            attach(getCardIndex(), CardLocation.DECK_MAIN, CardUnderType.UNDER_GENERIC);
        }
    }
}
