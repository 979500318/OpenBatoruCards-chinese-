package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class PIECE_X_LIFELOOPRESPECTS extends Card {
    
    public PIECE_X_LIFELOOPRESPECTS()
    {
        setImageSets("WXDi-P03-004");
        
        setOriginalName("LIFE LOOP RESPECTS");
        setAltNames("ライフループリスペクツ Raifu Ruupu Resupekutsu");
        setDescription("jp",
                "あなたのライフクロスの一番上を見て、以下の２つから１つを選ぶ。\n" +
                "$$1カードを１枚引く。\n" +
                "$$2%X %X %X %X %Xを支払い、あなたのライフクロスの一番上のカードを手札に加える。そうした場合、あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。"
        );
        
        setName("en", "LIFE LOOP RESPECTS");
        setDescription("en",
                "Look at the top card of your Life Cloth and choose one of the following.\n" +
                "$$1 Draw a card.\n" +
                "$$2 Pay %X %X %X %X %X and add the top card of your Life Cloth to your hand. If you do, shuffle your deck and add the top card of your deck to your Life Cloth."
        );
        
        setName("en_fan", "LIFE LOOP RESPECTS");
        setDescription("en_fan",
                "Look at the top card of your life cloth, and @[@|choose 1 of the following:|@]@\n" +
                "$$1 Draw 1 card.\n" +
                "$$2 Pay %X %X %X %X %X, and add the top card of your life cloth to your hand. If you do, shuffle your deck and add the top card of it to life cloth."
        );
        
		setName("zh_simplified", "LIFE LOOP RESPECTS");
        setDescription("zh_simplified", 
                "看你的生命护甲最上面，从以下的2种选1种。\n" +
                "$$1 抽1张牌。\n" +
                "$$2 支付%X %X %X %X %X，你的生命护甲最上面的牌加入手牌。这样做的场合，你的牌组洗切把最上面的牌加入生命护甲。\n"
        );

        setType(CardType.PIECE);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            CardIndex cardIndex = look(CardLocation.LIFE_CLOTH);
            
            if(playerChoiceMode() == 1)
            {
                addToLifeCloth(cardIndex);
                
                draw(1);
            } else if(cardIndex != null && payEner(Cost.colorless(5)) && addToHand(cardIndex))
            {
                shuffleDeck();
                addToLifeCloth(1);
            }
        }
    }
}
