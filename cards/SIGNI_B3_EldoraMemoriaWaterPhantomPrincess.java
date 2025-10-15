package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B3_EldoraMemoriaWaterPhantomPrincess extends Card {
    
    public SIGNI_B3_EldoraMemoriaWaterPhantomPrincess()
    {
        setImageSets("WXDi-P06-037", "WXDi-P06-037P");
        
        setOriginalName("幻水姫　エルドラ//メモリア");
        setAltNames("ゲンスイヒメエルドラメモリア　Gensuihime Erudora Memoria");
        setDescription("jp",
                "@C：このシグニのパワーはあなたの手札２枚につき＋1000される。\n" +
                "@U：このシグニがアタックしたとき、あなたのデッキの上からカードを３枚見る。その中から##を持つカードを好きな枚数公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Eldora//Memoria, Aquatic Phantom Queen");
        setDescription("en",
                "@C: This SIGNI gets +1000 power for every two cards in your hand.\n" +
                "@U: Whenever this SIGNI attacks, look at the top three cards of your deck. Reveal any number of cards with ## from among them and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Eldora//Memoria, Water Phantom Princess");
        setDescription("en_fan",
                "@C: This SIGNI gets +1000 power for every 2 cards in your hand.\n" +
                "@U: Whenever this SIGNI attacks, look at the top 3 cards of your deck. Reveal any number of cards with ## @[Life Burst]@ from among them, and add them to your hand, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "幻水姬 艾尔德拉//回忆");
        setDescription("zh_simplified", 
                "@C :这只精灵的力量依据你的手牌的数量，每有2张就+1000。\n" +
                "@U 当这只精灵攻击时，从你的牌组上面看3张牌。从中把持有##的牌任意张数公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new PowerModifier(this::onConstEffModGetValue));
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }

        private double onConstEffModGetValue(CardIndex cardIndex)
        {
            return 1000 * (getHandCount(getOwner()) / 2);
        }
        
        private void onAutoEff()
        {
            look(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().lifeBurst().fromLooked());
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
