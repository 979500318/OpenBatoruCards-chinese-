package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.CardDataColor;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.AbilityORCost;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class ARTS_W_CrossSword extends Card {

    public ARTS_W_CrossSword()
    {
        setImageSets("WX24-P4-026");

        setOriginalName("クロス・ソード");
        setAltNames("クロスソード Kurosu Soodo");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中からシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。この方法で手札に加えたカード１枚が白で、もう１枚が赤か青か緑か黒の場合、次の対戦相手のターン終了時まで、あなたのセンタールリグは@>@C：あなたが【ガード】する際、#Gを持つカードを１枚捨てる代わりに手札を１枚捨ててもよい。@@を得る。"
        );

        setName("en", "Cross Sword");
        setDescription("en",
                "Look at the top 5 cards of your deck. Reveal up to 2 SIGNI from among them, add them to your hand, and put the rest on the bottom of your deck in any order. If you added 1 white, and another red, blue, green, or black card to your hand this way, until the end of your opponent's next turn, your center LRIG gains:" +
                "@>@C: You may [[Guard]] by discarding 1 card from your hand instead of discarding 1 card with #G @[Guard]@."
        );

		setName("zh_simplified", "交错·刀剑");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。这个方法加入手牌的牌1张是白色且，另1张是红色或蓝色或绿色或黑色的场合，直到下一个对战对手的回合结束时为止，你的核心分身得到\n" +
                "@>@C 你[[防御]]时，把持有#G的牌1张舍弃，作为替代，可以把手牌1张舍弃。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked());
            boolean match = false;
            if(reveal(data) == 2)
            {
                CardDataColor color1 = data.get(0).getIndexedInstance().getColor();
                CardDataColor color2 = data.get(1).getIndexedInstance().getColor();
                
                match = (color1.matches(CardColor.WHITE) && color2.matches(CardColor.RED, CardColor.GREEN, CardColor.BLUE, CardColor.BLACK)) ||
                        (color2.matches(CardColor.WHITE) && color1.matches(CardColor.RED, CardColor.GREEN, CardColor.BLUE, CardColor.BLACK));
            }
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            if(match)
            {
                ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD, TargetFilter.HINT_OWNER_OWN, dataRC ->
                    new AbilityORCost(AbilityORCost.REPLACE_DEFAULT, new DiscardCost(0,1, ChoiceLogic.BOOLEAN))
                ));
                attachAbility(getLRIG(getOwner()), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
    }
}
