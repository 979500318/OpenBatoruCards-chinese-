package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class LRIGA_W2_MCLIONBuildUp extends Card {
    
    public LRIGA_W2_MCLIONBuildUp()
    {
        setImageSets("WXDi-P03-017");
        
        setOriginalName("MC.LION-BUILD UP");
        setAltNames("エムシーリオンビルドアップ Emu Shii Rion Birudo Appu");
        setDescription("jp",
                "@E：対戦相手のレベル２以下のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "@E %X %X %X %X %X %X：対戦相手のレベル３のシグニ１体を対象とし、それをトラッシュに置く。\n" +
                "@E %W %W：あなたのデッキの上からカードを５枚見る。その中からカードを２枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "MC LION - BUILD UP");
        setDescription("en",
                "@E: Target level two or less SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn.\n" +
                "@E %X %X %X %X %X %X: Put target level three SIGNI on your opponent's field into its owner's trash.\n" +
                "@E %W %W: Look at the top five cards of your deck. Add up to two cards from among them to your hand and put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "MC.LION - BUILD UP");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 2 or lower SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "@E %X %X %X %X %X %X: Target 1 of your opponent's level 3 SIGNI, and put it into the trash.\n" +
                "@E %W %W: Look at the top 5 cards of your deck. Add up to 2 cards from among them to your hand, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "MC.LION-BUILD UP");
        setDescription("zh_simplified", 
                "@E :对战对手的等级2以下的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "@E %X %X %X %X %X %X:对战对手的等级3的精灵1只作为对象，将其放置到废弃区。\n" +
                "@E %W %W:从你的牌组上面看5张牌。从中把牌2张最多加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(6)), this::onEnterEff2);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 2)), this::onEnterEff3);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI().withLevel(0,2)).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().withLevel(3)).get();
            trash(target);
        }
        
        private void onEnterEff3()
        {
            look(5);
            
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
