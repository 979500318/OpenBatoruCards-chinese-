package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W2_IsisHolyAngel extends Card {
    
    public SIGNI_W2_IsisHolyAngel()
    {
        setImageSets("WXDi-P04-047");
        
        setOriginalName("聖天　イシス");
        setAltNames("セイテンイシス Seiten Ishisu");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。その中から＜天使＞のシグニを１枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。この方法でカードを手札に加えた場合、手札を１枚捨てる。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );
        
        setName("en", "Isis, Blessed Angel");
        setDescription("en",
                "@E: Look at the top three cards of your deck. Reveal up to one <<Angel>> SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order. If you added a card to your hand this way, discard a card." +
                "~#Target SIGNI on your opponent's field gains@>@C@#: This SIGNI cannot attack.@@@@until end of turn. Draw a card."
        );
        
        setName("en_fan", "Isis, Holy Angel");
        setDescription("en_fan",
                "@E: Look at the top 3 cards of your deck. Reveal up to 1 <<Angel>> SIGNI from among them, and add it to your hand, and put the rest on the bottom of your deck in any order. If you added a card to your hand with this effect, discard 1 card from your hand." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:\n" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );
        
		setName("zh_simplified", "圣天 伊希斯");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看3张牌。从中把<<天使>>精灵1张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。这个方法把牌加入手牌的场合，手牌1张舍弃。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromLooked()).get();
            reveal(cardIndex);
            boolean successAdded = addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            if(successAdded) discard(1);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
