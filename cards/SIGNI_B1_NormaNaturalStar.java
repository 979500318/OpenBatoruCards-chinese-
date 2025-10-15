package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B1_NormaNaturalStar extends Card {
    
    public SIGNI_B1_NormaNaturalStar()
    {
        setImageSets("WXDi-P04-063");
        
        setOriginalName("羅星　ノーマ");
        setAltNames("ラセイノーマ Rasei Nooma");
        setDescription("jp",
                "@U：あなたの効果によって対戦相手が手札を１枚捨てたとき、対戦相手のシグニ１体を対象とし、それを凍結する。"
        );
        
        setName("en", "Norma, Natural Planet");
        setDescription("en",
                "@U: Whenever your opponent discards a card by your effect, freeze target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Norma, Natural Star");
        setDescription("en_fan",
                "@U: Whenever your opponent discards 1 card from their hand by your effect, target 1 of your opponent's SIGNI, and freeze it."
        );
        
		setName("zh_simplified", "罗星 矩尺座");
        setDescription("zh_simplified", 
                "@U :当因为你的效果把对战对手的手牌1张舍弃时，对战对手的精灵1只作为对象，将其冻结。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSourceCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            freeze(target);
        }
    }
}
