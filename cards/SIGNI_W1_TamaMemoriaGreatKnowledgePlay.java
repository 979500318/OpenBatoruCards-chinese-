package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_W1_TamaMemoriaGreatKnowledgePlay extends Card {

    public SIGNI_W1_TamaMemoriaGreatKnowledgePlay()
    {
        setImageSets("WXDi-P10-047", "WXDi-P10-047P");

        setOriginalName("偉智の遊　タマ//メモリア");
        setAltNames("イチノユウタマメモリア Ichi no Yuu Tama Memoria");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、あなたのデッキの一番上を見る。そのカードとエナゾーンにあるこのシグニを入れ替えてもよい。\n" +
                "@E：あなたのデッキの上からカードを２枚見る。その中からカード１枚をデッキの一番上に戻し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Tama//Memoria, Great Knowledge Play");
        setDescription("en",
                "@U: When this SIGNI is vanished, look at the top card of your deck. You may swap that card with this SIGNI in your Ener Zone.\n" +
                "@E: Look at the top two cards of your deck. Put a card on top of your deck, and put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Tama//Memoria, Great Knowledge Play");
        setDescription("en_fan",
                "@U: When this SIGNI is banished, look at the top card of your deck. You may exchange that card with this SIGNI in the ener zone.\n" +
                "@E: Look at the top 2 cards of your deck. Put one of them on the top of your deck, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "伟智之游 小玉//回忆");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，看你的牌组最上面。可以把那张牌与能量区的这张精灵交换。\n" +
                "@E :从你的牌组上面看2张牌。从中把1张牌返回牌组最上面，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
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

            registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
        }

        private void onAutoEff()
        {
            CardIndex cardIndex = look();
            
            if(cardIndex != null)
            {
                if(getCardIndex().getLocation() == CardLocation.ENER && playerChoiceActivate())
                {
                    putInEner(cardIndex);
                    returnToDeck(getCardIndex(), DeckPosition.TOP);
                } else {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
        
        private void onEnterEff()
        {
            look(2);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
            returnToDeck(cardIndex, DeckPosition.TOP);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
