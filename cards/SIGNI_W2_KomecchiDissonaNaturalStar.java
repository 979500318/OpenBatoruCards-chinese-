package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class SIGNI_W2_KomecchiDissonaNaturalStar extends Card {

    public SIGNI_W2_KomecchiDissonaNaturalStar()
    {
        setImageSets("WXDi-P13-060");

        setOriginalName("羅星　コメッチ//ディソナ");
        setAltNames("ラセイコメッチディソナ Rasei Komecchi Disona");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの一番上を公開する。そのカードが#Sの場合、カードを１枚引くか【エナチャージ１】をする。"
        );

        setName("en", "Cometty//Dissona, Natural Planet");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If that card is #S, draw a card or [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Komecchi//Dissona, Natural Star");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If it is a #S @[Dissona]@ card, draw 1 card or [[Ener Charge 1]]."
        );

		setName("zh_simplified", "罗星 彗星//失调");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，你的牌组最上面公开。那张牌是#S的场合，抽1张牌或[[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(5000);

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
            CardIndex cardIndex = reveal();
            if(cardIndex == null) return;
            
            if(!cardIndex.getIndexedInstance().isState(CardStateFlag.IS_DISSONA))
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            } else {
                int action = playerChoiceAction(ActionHint.DRAW, ActionHint.ENER);
                if((action == 1 && draw(1).get() == null) ||
                   (action == 2 && enerCharge(1).get() == null))
                {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
    }
}

