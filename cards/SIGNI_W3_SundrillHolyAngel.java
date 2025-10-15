package open.batoru.data.cards;

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

public final class SIGNI_W3_SundrillHolyAngel extends Card {
    
    public SIGNI_W3_SundrillHolyAngel()
    {
        setImageSets("WXDi-P05-051");
        
        setOriginalName("聖英　サンドリル");
        setAltNames("セイエイサンドリル Seiei Sandoriru Sundrill");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるシグニのレベルの合計が偶数の場合、対戦相手のシグニ１体を対象とし、%W %W %Xを支払ってもよい。そうした場合、それを手札に戻す。"
        );
        
        setName("en", "Mathdrill, Blessed Wisdom");
        setDescription("en",
                "@U: At the beginning of your attack phase, if the total level of SIGNI on your field is even, you may pay %W %W %X. If you do, return target SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Sundrill, Holy Wisdom");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if the combined level of all SIGNI on your field is even, target 1 of your opponent's SIGNI, and you may pay %W %W %X. If you do, return it to their hand."
        );
        
		setName("zh_simplified", "圣英 算术练习");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的精灵的等级的合计在偶数的场合，对战对手的精灵1只作为对象，可以支付%W %W%X。这样做的场合，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
        setLevel(3);
        setPower(12000);
        
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
            if(new TargetFilter().own().SIGNI().getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).sum() % 2 == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.WHITE, 2) + Cost.colorless(1)))
                {
                    addToHand(target);
                }
            }
        }
    }
}
