package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_W3_LupanneGreatThievingTrap extends Card {
    
    public SIGNI_W3_LupanneGreatThievingTrap()
    {
        setImageSets("WXDi-P08-038");
        
        setOriginalName("大盗罠　ルパンヌ");
        setAltNames("ダイトウビンルパンヌ Daitoubin Rupanne");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、あなたのライフクロス１枚をトラッシュに置いてもよい。そうした場合、このシグニをエナゾーンからライフクロスに加える。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2カあなたのデッキの上からカードを４枚見る。その中からカードを２枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Lupanne, Thieving Master Trickster");
        setDescription("en",
                "@U: When this SIGNI is vanished, you may put one of your Life Cloth into your trash. If you do, add this SIGNI from your Ener Zone to your Life Cloth." +
                "~#Choose one -- \n$$1 Return target upped SIGNI on your opponent's field to its owner's hand. \n$$2 Look at the top four cards of your deck. Add up to two cards from among them to your hand and put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Lupanne, Great Thieving Trap");
        setDescription("en_fan",
                "@U: When this SIGNI is banished, you may put 1 of your life cloth into the trash. If you do, add this SIGNI from your ener zone to life cloth." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and return it to their hand.\n" +
                "$$2 Look at the top 4 cards of your deck. Add up to 2 cards from among them to your hand, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "大盗罠  鲁邦");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，可以把你的生命护甲1张放置到废弃区。这样做的场合，这张精灵从能量区加入生命护甲。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 从你的牌组上面看4张牌。从中把牌2张最多加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
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
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(getCardIndex().getLocation() == CardLocation.ENER && playerChoiceActivate() && trash(CardLocation.LIFE_CLOTH).get() != null)
            {
                addToLifeCloth(getCardIndex());
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
                addToHand(target);
            } else {
                look(4);
                
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromLooked());
                addToHand(data);
                
                while(getLookedCount() > 0)
                {
                    CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                }
            }
        }
    }
}
