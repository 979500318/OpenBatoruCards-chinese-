package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_B_UltraSuperheroes extends Card {
    
    public PIECE_B_UltraSuperheroes()
    {
        setImageSets("WXDi-P08-003", "PR-Di026");
        
        setOriginalName("ウルトラスーパーヒーローズ");
        setAltNames("Urutora Suupaahiiroozu");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "あなたの場に白のルリグがいる場合、あなたのデッキの上からカードを５枚見る。その中からカードを２枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。\n" +
                "その後、あなたの場に赤のルリグがいる場合、対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "その後、あなたの場に黒のルリグがいる場合、対戦相手のデッキの上からカードを１０枚トラッシュに置く。"
        );
        
        setName("en", "Ultra Super Heroes");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "If there is a white LRIG on your field, look at the top five cards of your deck. Add up to two cards from among them to your hand and put the rest on the bottom of your deck in any order.\n" +
                "Then, if there is a red LRIG on your field, vanish target SIGNI on your opponent's field with power 12000 or less.\n" +
                "Then, if there is a black LRIG on your field, put the top ten cards of your opponent's deck into their trash."
        );
        
        setName("en_fan", "Ultra Superheroes");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "If there is a white LRIG on your field, look at the top 5 cards of your deck. Add up to 2 from among them to your hand, and put the rest on the bottom of your deck in any order.\n" +
                "Then, if there is a red LRIG on your field, target 1 of your opponent's SIGNI with power 12000 or less, and banish it.\n" +
                "Then, if there is a black LRIG on your field, put the top 10 cards of your opponent's deck into the trash."
        );
        
		setName("zh_simplified", "奥特超级英雄");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "你的场上有白色的分身的场合，从你的牌组上面看5张牌。从中把牌2张最多加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "然后，你的场上有红色的分身的场合，对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n" +
                "然后，你的场上有黑色的分身的场合，从对战对手的牌组上面把10张牌放置到废弃区。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            return getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount() > 0)
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
            
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.RED).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, 12000)).get();
                banish(target);
            }
            
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.BLACK).getValidTargetsCount() > 0)
            {
                millDeck(getOpponent(), 10);
            }
        }
    }
}
