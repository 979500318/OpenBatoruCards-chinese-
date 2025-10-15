package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B2_HypnosAzureAngel extends Card {
    
    public SIGNI_B2_HypnosAzureAngel()
    {
        setImageSets("WXDi-P00-061");
        
        setOriginalName("蒼天　ヒュプノス");
        setAltNames("ソウテンヒュプノス Souten Hyupunosu");
        setDescription("jp",
                "@U：このシグニがバトルによって凍結状態のシグニをバニッシュしたとき、対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "Hypnos, Azure Angel");
        setDescription("en",
                "@U: Whenever this SIGNI vanishes a frozen SIGNI through battle, your opponent discards a card."
        );
        
        setName("en_fan", "Hypnos, Azure Angel");
        setDescription("en_fan",
                "@U: Whenever this SIGNI banishes a frozen SIGNI in battle, your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "苍天 修普诺斯");
        setDescription("zh_simplified", 
                "@U :当这只精灵因为战斗把冻结状态的精灵破坏时，对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return caller.getIndexedInstance().isState(CardStateFlag.FROZEN) &&
                   getEvent().getSourceCardIndex() == getCardIndex() && getEvent().getSourceAbility() == null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }
    }
}
