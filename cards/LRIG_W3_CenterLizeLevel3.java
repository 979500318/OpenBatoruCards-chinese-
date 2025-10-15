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
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_W3_CenterLizeLevel3 extends Card {
    
    public LRIG_W3_CenterLizeLevel3()
    {
        setImageSets("WXDi-D02-04L");
        
        setOriginalName("【センター】リゼ　レベル３");
        setAltNames("センターリゼレベルサン Sentaa Rize Reberu San Center Lize");
        setDescription("jp",
                "=T ＜さんばか＞\n" +
                "^A $T1 %W0：あなたの＜バーチャル＞のシグニ１体を対象とし、次の対戦相手のターン終了まで、それのパワーを＋2000する。\n" +
                "@E：あなたのデッキの上からカードを３枚見る。その中から＜バーチャル＞のシグニを好きな枚数公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "[Center] Lize, Level 3");
        setDescription("en",
                "=T <<Sanbaka>>\n" +
                "^A $T1 %W0: Target <<Virtual>> SIGNI on your field gets +2000 power until the end of your opponent's next end phase.\n" +
                "@E: Look at the top three cards of your deck. Reveal any number of <<Virtual>> SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "[Center] Lize Level 3");
        setDescription("en_fan",
                "=T <<Sanbaka>>\n" +
                "^A $T1 %W0: Target 1 of your <<Virtual>> SIGNI, and until the end of your opponent's next turn, that SIGNI gets +2000 power.\n" +
                "@E: Look at the top 3 cards of your deck. Reveal any number of <<Virtual>> SIGNI from among them and add them to your hand, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "【核心】莉泽 等级3");
        setDescription("zh_simplified", 
                "=T<<さんばか>>\n" +
                "^A$T1 %W0:你的<<バーチャル>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+2000。\n" +
                "@E :从你的牌组上面看3张牌。从中把<<バーチャル>>精灵任意张数公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LIZE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onActionEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.SANBAKA) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL)).get();
            gainPower(cardIndex, 2000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onEnterEff()
        {
            look(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromLooked());
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
