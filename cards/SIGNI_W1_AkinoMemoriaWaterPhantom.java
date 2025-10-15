package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_W1_AkinoMemoriaWaterPhantom extends Card {
    
    public SIGNI_W1_AkinoMemoriaWaterPhantom()
    {
        setImageSets("WXDi-P08-051", "WXDi-P08-051P", "SPDi01-90");
        
        setOriginalName("幻水　アキノ//メモリア");
        setAltNames("ゲンスイアキノメモリア Gensui Akino Memoria");
        setDescription("jp",
                "@C：このシグニは覚醒状態であるかぎり、@>@U：このシグニがアタックしたとき、あなたのデッキの上からカードを２枚見る。その中からカードを１枚まで手札に加え、残りを好きな順番デッキの一番下に置く。@@を得る。\n" +
                "@E：対戦相手の手札が６枚以上ある場合、このシグニは覚醒する。"
        );
        
        setName("en", "Akino//Memoria, Water Phantom");
        setDescription("en",
                "@C: As long as this SIGNI is awakened, it gains@>@U: Whenever this SIGNI attacks, look at the top two cards of your deck. Add up to one card to your hand and put the rest on the bottom of your deck in any order.@@" +
                "@E: If your opponent has six or more cards in their hand, this SIGNI is awakened. "
        );
        
        setName("en_fan", "Akino//Memoria, Water Phantom");
        setDescription("en_fan",
                "@C: As long as this SIGNI is awakened, it gains:" +
                "@>@U: Whenever this SIGNI attacks, look at the top 2 cards of your deck. Add up to 1 card from among them to your hand, and put the rest on the bottom of your deck in any order.@@" +
                "@E: If there are 6 or more cards in your opponent's hand, this SIGNI awakens."
        );
        
		setName("zh_simplified", "幻水 昭乃//回忆");
        setDescription("zh_simplified", 
                "@C :这只精灵在觉醒状态时，得到\n" +
                "@>@U :当这只精灵攻击时，从你的牌组上面看2张牌。从中把牌1张最多加入手牌，剩下的任意顺序放置到牌组最下面。@@\n" +
                "@E :对战对手的手牌在6张以上的场合，这只精灵觉醒。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return isState(CardStateFlag.AWAKENED) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            look(2);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onEnterEff()
        {
            if(getHandCount(getOpponent()) >= 6)
            {
                getCardStateFlags().addValue(CardStateFlag.AWAKENED);
            }
        }
    }
}
