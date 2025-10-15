package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R3_BlueAdamasNaturalPyroxene extends Card {
    
    public SIGNI_R3_BlueAdamasNaturalPyroxene()
    {
        setImageSets("WXDi-P06-036");
        
        setOriginalName("羅輝石　ブルーアダマス");
        setAltNames("ラキセキブルーアダマス Rakiseki Buruu Adamasu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのデッキの一番上を公開し、カードを１枚引く。その後、この効果で公開したカードが＜宝石＞のシグニの場合、%B %R %Xを支払ってもよい。そうした場合、対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Blue Adamas, Natural Pyroxene");
        setDescription("en",
                "@U: At the beginning of your attack phase, reveal the top card of your deck and draw a card. Then, if a <<Jewel>> SIGNI is revealed with this effect, you may pay %B %R %X. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Blue Adamas, Natural Pyroxene");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, reveal the top card of your deck, and draw 1 card. If you revealed a <<Gem>> SIGNI this way, you may pay %B %R %X. If you do, target 1 of your opponent's SIGNI, and banish it."
        );
        
		setName("zh_simplified", "罗辉石 蔚蓝金刚");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的牌组最上面公开，抽1张牌。然后，这个效果公开的牌是<<宝石>>精灵的场合，可以支付%B%R%X。这样做的场合，对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                boolean wasGem = CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) && cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.GEM);
                
                if(draw(1).get() == null) returnToDeck(cardIndex, DeckPosition.TOP);
                
                if(wasGem && payEner(Cost.color(CardColor.BLUE, 1) + Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                    banish(target);
                }
            }
        }
    }
}
