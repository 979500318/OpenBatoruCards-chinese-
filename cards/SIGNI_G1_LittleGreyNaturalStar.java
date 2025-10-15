package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_LittleGreyNaturalStar extends Card {

    public SIGNI_G1_LittleGreyNaturalStar()
    {
        setImageSets("WX24-P3-082");

        setOriginalName("羅星　リトルグレイ");
        setAltNames("ラセイリトルグレイ Rasei Ritoru Gurei");
        setDescription("jp",
                "@E：あなたのデッキの一番上を公開する。そのカードがレベル１のシグニの場合、【エナチャージ１】をする。"
        );

        setName("en", "Little Grey, Natural Star");
        setDescription("en",
                "@E: Reveal the top card of your deck. If it is a level 1 SIGNI, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "罗星 小灰人");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面公开。那张牌是等级1的精灵的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                if(cardIndex.getIndexedInstance().getLevelByRef() == 1)
                {
                    enerCharge(1);
                } else {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
    }
}
