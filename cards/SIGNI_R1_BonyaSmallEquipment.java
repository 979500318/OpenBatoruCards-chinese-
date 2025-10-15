package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R1_BonyaSmallEquipment extends Card {
    
    public SIGNI_R1_BonyaSmallEquipment()
    {
        setImageSets("WXDi-P06-053");
        
        setOriginalName("小装　ボーニャ");
        setAltNames("ショウソウボーニャ Shousou Boonya");
        setDescription("jp",
                "@E %X %X %X：あなたのデッキの上からカードを５枚見る。その中から赤のシグニを１枚までと、白か青か緑か黒のシグニを１枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Bonya, Lightly Armed");
        setDescription("en",
                "@E %X %X %X: Look at the top five cards of your deck. Reveal up to one red SIGNI and up to one white, blue, green, or black SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in any order." +
                "~#Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Bonya, Small Equipment");
        setDescription("en_fan",
                "@E %X %X %X: Look at the top 5 cards of your deck. Reveal up to 1 red SIGNI and up to 1 white, blue, green or black SIGNI, and add them to your hand, and put the rest on the bottom of your deck in any order." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );
        
		setName("zh_simplified", "小装 箭矢");
        setDescription("zh_simplified", 
                "@E %X %X %X:从你的牌组上面看5张牌。从中把红色的精灵1张最多和，白色或蓝色或绿色或黑色的精灵1张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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
            
            registerEnterAbility(new EnerCost(Cost.colorless(3)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            look(5);
            
            DataTable<CardIndex> data = new DataTable<>();
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.RED).fromLooked()).get();
            if(cardIndex != null) data.add(cardIndex);
            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.WHITE,CardColor.BLUE,CardColor.GREEN,CardColor.BLACK).fromLooked().except(data)).get();
            if(cardIndex != null) data.add(cardIndex);
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
