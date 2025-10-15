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
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class PIECE_GRB_HarmonyCall extends Card {
    
    public PIECE_GRB_HarmonyCall()
    {
        setImageSets("WXDi-D01-011");
        
        setOriginalName("ハーモニー・コール");
        setAltNames("ハーモニーコール Haamonii Kooru");
        setDescription("jp",
                "=P =T ＜アンシエント・サプライズ＞＆全員レベル１以上\n\n" +
                "あなたのデッキの上からカードを８枚見る。その中からレベル１とレベル２とレベル３のシグニをそれぞれ１枚まで場に出し、残りをシャッフルしてデッキの一番下に置く。その後、ターン終了時まで、対象のレベル１のシグニ１体は[[アサシン]]を得、対象のレベル２のシグニ１体は[[ダブルクラッシュ]]を得、対象のレベル３のシグニ１体は[[ランサー]]を得る。"
        );
        
        setName("en", "Harmonic Call");
        setDescription("en",
                "=U You have =T <<Ancient Surprise>> on your field with all members level one or more.\n\n" +
                "Look at the top eight cards of your deck. Put up to one level one SIGNI, one level two SIGNI, and one level three SIGNI from among them onto your field. Put the rest on the bottom of your deck in random order. Then, target level one SIGNI gains [[Assassin]], target level two SIGNI gains [[Double Crush]], and target level three SIGNI gains [[Lancer]] until end of turn."
        );
        
        setName("en_fan", "Harmony Call");
        setDescription("en_fan",
                "=U =T <<Ancient Surprise>> and all of them are level 1 or higher\n\n" +
                "Look at the top 8 cards of your deck. Put up to 1 level 1, level 2, and level 3 SIGNI each from among them onto the field, and shuffle the rest and put them on the bottom of your deck. Then, until end of turn, 1 target level 1 SIGNI gains [[Assassin]], 1 target level 2 SIGNI gains [[Double Crush]], and 1 target level 3 SIGNI gains [[Lancer]]."
        );
        
		setName("zh_simplified", "和声·呼唤");
        setDescription("zh_simplified", 
                "=U=T<<アンシエント･サプライズ>>＆全员等级1以上\n" +
                "从你的牌组上面看8张牌。从中把等级1和等级2和等级3的精灵各1张最多出场，剩下的洗切放置到牌组最下面。然后，直到回合结束时为止，对象的等级1的精灵1只得到[[暗杀]]，对象的等级2的精灵1只得到[[双重击溃]]，对象的等级3的精灵1只得到[[枪兵]]。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.GREEN, CardColor.RED, CardColor.BLUE);
        setCost(Cost.colorless(8));
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
            
            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            return new TargetFilter().own().anyLRIG().withLevel(1,0).getValidTargetsCount() == 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            look(8);
            
            DataTable<CardIndex> data = new DataTable<>();
            for(int i=0;i<3;i++)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().except(data).fromLooked().withLevel(i+1).playable()).get();
                if(cardIndex != null) data.add(cardIndex);
            }
            putOnField(data);
            
            int countLooked = getLookedCount();
            if(countLooked > 0)
            {
                forEachCardInLooked(cardIndexLooked -> {
                    returnToDeck(cardIndexLooked, DeckPosition.BOTTOM);
                });
                shuffleDeck(countLooked, DeckPosition.BOTTOM);
            }
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withLevel(1)).get();
            attachAbility(target, new StockAbilityAssassin(), ChronoDuration.turnEnd());
            
            target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withLevel(2)).get();
            attachAbility(target, new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
            
            target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withLevel(3)).get();
            attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
        }
    }
}
