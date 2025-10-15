package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B2_DelphinusNaturalStar extends Card {
    
    public SIGNI_B2_DelphinusNaturalStar()
    {
        setImageSets("WXDi-P06-064");
        
        setOriginalName("羅星　デルフィヌス");
        setAltNames("ラセイデルフィヌス Rasei Derufinasu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのデッキの一番上を公開する。そのカードがレベル１のシグニの場合、カードを１枚引く。"
        );
        
        setName("en", "Delphinus, Natural Planet");
        setDescription("en",
                "@U: At the beginning of your attack phase, reveal the top card of your deck. If that card is a level one SIGNI, draw a card."
        );
        
        setName("en_fan", "Delphinus, Natural Star");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, reveal the top card of your deck. If it is a level 1 SIGNI, draw 1 card."
        );
        
		setName("zh_simplified", "罗星 海豚座");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的牌组最上面公开。那张牌是等级1的精灵的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(5000);
        
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
            
            if(cardIndex == null || !CardType.isSIGNI(cardIndex.getCardReference().getType()) || cardIndex.getIndexedInstance().getLevelByRef() != 1 || draw(1).get() == null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
    }
}
