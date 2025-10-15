package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class SIGNI_W3_CodeHeartPRomappi extends Card {
    
    public SIGNI_W3_CodeHeartPRomappi()
    {
        setImageSets("WXDi-P01-035");
        
        setOriginalName("コードハート　Ｐロマピ");
        setAltNames("コードハートピーロマピ Koodo Haato Pii Romapi");
        setDescription("jp",
                "=T ＜Card Jockey＞\n" +
                "^C：対戦相手は追加で%X %Xを支払わないかぎり、[[ガード]]ができない。\n" +
                "@E %W：あなたのデッキの上からカードを３枚見る。その中からスペルと白のシグニをそれぞれ１枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "PRJ - MAP, Code: Heart");
        setDescription("en",
                "=T <<Card Jockey>>\n" +
                "^C: Your opponent cannot [[Guard]] unless they pay an additional %X %X.\n" +
                "@E %W: Look at the top three cards of your deck. Reveal up to one spell and one white SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Code Heart P Romappi");
        setDescription("en_fan",
                "=T <<Card Jockey>>\n" +
                "^C: Your opponent can't [[Guard]] unless they pay %X %X.\n" +
                "@E %W: Look at the top 3 cards of your deck. Reveal up to 1 spell and white SIGNI each from among them, and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "爱心代号 光雕投影");
        setDescription("zh_simplified", 
                "=T<<Card:Jockey>>\n" +
                "^C对战对手如果不追加把%X %X:支付，那么不能[[防御]]。\n" +
                "@E %W:从你的牌组上面看3张牌。从中把魔法和白色的精灵各1张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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
            
            registerConstantAbility(this::onConstEffCond, new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD,
                TargetFilter.HINT_OWNER_OP, data -> new EnerCost(Cost.colorless(2)))
            );
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.CARD_JOCKEY) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            look(3);
            
            DataTable<CardIndex> data = new DataTable<>();
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().spell().fromLooked()).get();
            if(cardIndex != null) data.add(cardIndex);
            
            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.WHITE).fromLooked()).get();
            if(cardIndex != null) data.add(cardIndex);
            
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
