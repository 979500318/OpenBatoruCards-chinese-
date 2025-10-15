package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIG_R3_TawilTreTheAwakened extends Card {
    
    public LRIG_R3_TawilTreTheAwakened()
    {
        setImageSets("WXDi-P00-009");
        
        setOriginalName("目醒めし者　タウィル＝トレ");
        setAltNames("メザメシモノタウィルトレ Mezameshimono Tauiru Tore");
        setDescription("jp",
                "=T ＜アンシエント・サプライズ＞\n" +
                "^A $T1 @[手札から赤のカードを２枚捨てる]@：カードを２枚引く。\n" +
                "@E：あなたのデッキの上からカードを３枚見る。その中から赤のカードを好きな枚数公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Tawil =Tre=, Awakaned One");
        setDescription("en",
                "=T <<Ancient Surprise>>\n" +
                "^A $T1 @[Discard two red cards from your hand]@: Draw two cards.\n" +
                "@E: Look at the top three cards of your deck. Reveal any number of red cards from among them and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Tawil-Tre, the Awakened");
        setDescription("en_fan",
                "=T <<Ancient Surprise>>\n" +
                "^A $T1 @[Discard 2 red cards from your hand]@: Draw 2 cards.\n" +
                "@E: Look at the top 3 cards of your deck. Reveal any number of red cards from among them, and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "目醒者 塔维尔=TRE");
        setDescription("zh_simplified", 
                "=T<<アンシエント・サプライズ>>\n" +
                "^A$T1 从手牌把红色的牌2张舍弃:抽2张牌。\n" +
                "@E :从你的牌组上面看3张牌。从中把红色的牌任意张数公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAWIL);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act = registerActionAbility(new DiscardCost(2, new TargetFilter().fromHand().withColor(CardColor.RED)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onActionEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff()
        {
            draw(2);
        }
        
        private void onEnterEff()
        {
            look(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().fromLooked().withColor(CardColor.RED));
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
