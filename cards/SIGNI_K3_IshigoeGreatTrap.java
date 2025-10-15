package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K3_IshigoeGreatTrap extends Card {

    public SIGNI_K3_IshigoeGreatTrap()
    {
        setImageSets("WXK01-104");

        setOriginalName("大罠　イシゴエ");
        setAltNames("ダイビンイシゴエ Daibin Ishigoe");
        setDescription("jp",
                "@U：このカードがデッキからトラッシュに置かれたとき、あなたのメインフェイズの場合、このカードをトラッシュから場に出してもよい。"
        );

        setName("en", "Ishigoe, Great Trap");
        setDescription("en",
                "@U: When this SIGNI is put from your deck into the trash, if it is your main phase, you may put this SIGNI from your trash onto the field."
        );

		setName("zh_simplified", "大罠 黑石川五右卫门");
        setDescription("zh_simplified", 
                "@U :当这张牌从牌组放置到废弃区时，你的主要阶段的场合，可以把这张牌从废弃区出场。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(3);
        setPower(7000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setActiveLocation(CardLocation.DECK_MAIN);
        }
        
        private void onAutoEff()
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH && isOwnTurn() && getCurrentPhase() == GamePhase.MAIN && playerChoiceActivate())
            {
                putOnField(getCardIndex());
            }
        }
    }
}
