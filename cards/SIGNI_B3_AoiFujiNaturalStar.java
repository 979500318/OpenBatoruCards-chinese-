package open.batoru.data.cards;

import open.batoru.core.Deck;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B3_AoiFujiNaturalStar extends Card {

    public SIGNI_B3_AoiFujiNaturalStar()
    {
        setImageSets("WXDi-P09-068");

        setOriginalName("羅星　富士葵");
        setAltNames("ラセイフジアオイ Rasei Fuji Aoi");
        setDescription("jp",
                "@E：あなたのデッキの一番上を公開する。それがレベル１のシグニの場合、カードを１枚引く。そうでない場合、そのカードをデッキの一番下に置く。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Fuji Aoi, Natural Planet");
        setDescription("en",
                "@E: Reveal the top card of your deck. If that card is a level one SIGNI, draw a card. If it isn't, put that card on the bottom of your deck." +
                "~#Choose one -- \n$$1 Down up to two target SIGNI on your opponent's field. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Aoi Fuji, Natural Plant");
        setDescription("en_fan",
                "@E: Reveal the top card of your deck. If it is a level 1 SIGNI, draw 1 card. If it is not, put it on the bottom of your deck." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "罗星 富士葵");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面公开。那张牌是等级1的精灵的场合，抽1张牌。不是的场合，那张牌放置到牌组最下面。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵2只最多作为对象，将这些#D。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null ||
               !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) || cardIndex.getIndexedInstance().getLevelByRef() != 1 ||
               draw(1).get() == null)
            {
                returnToDeck(cardIndex, Deck.DeckPosition.BOTTOM);
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
                down(data);
            } else {
                draw(1);
            }
        }
    }
}
