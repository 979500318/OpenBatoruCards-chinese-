package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_Code2434HimawariHonma extends Card {
    
    public SIGNI_W3_Code2434HimawariHonma()
    {
        setImageSets("WXDi-P00-034");
        
        setOriginalName("コード２４３４　本間ひまわり");
        setAltNames("コードニジサンジホンマヒマワリ Koodo Nijisanji Honma Himawari");
        setDescription("jp",
                "@U：対戦相手のメインフェイズ開始時、あなたのデッキの一番上を公開する。そのカードが＜バーチャル＞のシグニの場合、ターン終了時まで、このシグニは[[シャドウ]]を得る。\n" +
                "@A $T1 %W：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加え、１枚をデッキの一番上に戻し、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Himawari Honma, Code 2434");
        setDescription("en",
                "@U: At the beginning of your opponent's main phase, reveal the top card of your deck. If that card is a <<Virtual>> SIGNI, this SIGNI gains [[Shadow]] until end of turn.\n" +
                "@A $T1 %W: Look at the top three cards of your deck. Reveal a SIGNI from them and add it to your hand. Put one of the remaining cards on top of your deck, and put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Code 2434 Himawari Honma");
        setDescription("en_fan",
                "@U: At the beginning of your opponent's main phase, reveal the top card of your deck. If that card is a <<Virtual>> SIGNI, until end of turn, this SIGNI gains [[Shadow]].\n" +
                "@A $T1 %W: Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them and add it to your hand, return 1 to the top of your deck and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "2434代号 本间向日葵");
        setDescription("zh_simplified", 
                "@U :对战对手的主要阶段开始时，你的牌组最上面公开。那张牌是<<バーチャル>>精灵的场合，直到回合结束时为止，这只精灵得到[[暗影]]。（这只精灵不会被对战对手作为对象）\n" +
                "@A $T1 %W:从你的牌组上面看3张牌。从中把精灵1张公开加入手牌，1张返回牌组最上面，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getCurrentPhase() == GamePhase.MAIN && !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null && CardType.isSIGNI(cardIndex.getCardReference().getType()) && cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.VIRTUAL))
            {
                attachAbility(getCardIndex(), new StockAbilityShadow(), ChronoDuration.turnEnd());
            }
            
            returnToDeck(cardIndex, DeckPosition.TOP);
        }
        
        private void onActionEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
            returnToDeck(cardIndex, DeckPosition.TOP);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
