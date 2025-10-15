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

public final class SIGNI_W2_Code2434Ryushen extends Card {
    
    public SIGNI_W2_Code2434Ryushen()
    {
        setImageSets("WXDi-P00-046");
        setLinkedImageSets("WXDi-P00-044","WXDi-P00-079");
        
        setOriginalName("コード２４３４　緑仙");
        setAltNames("コードニジサンジリューシェン Koodo Nijisanji Ryuushen");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《コード２４３４　シスター・クレア》と《コード２４３４　ドーラ》がある場合、対戦相手のシグニ１体を対象とし、%W %W %Xを支払ってもよい。そうした場合、それを手札に戻す。"
        );
        
        setName("en", "Ryushen, Code 2434");
        setDescription("en",
                "@U: At the beginning of your attack phase, if \"Sister Claire, Code 2434\" and \"Dola, Code 2434\" are both on your field, you may pay %W %W %X. If you do, return target SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Code 2434 Ryushen");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if \"Code 2434 Sister Cleaire\" and \"Code 2434 Dola\" are on your field, target 1 of your opponent's SIGNI, and you may pay %W %W %X. If you do, return that SIGNI to their hand."
        );
        
		setName("zh_simplified", "2434代号 绿仙");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《コード２４３４　シスター・クレア》和《コード２４３４　ドーラ》的场合，对战对手的精灵1只作为对象，可以支付%W %W%X。这样做的场合，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(2);
        setPower(8000);
        
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
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE &&
                   new TargetFilter().own().SIGNI().withName("コード２４３４　シスター・クレア").getValidTargetsCount() > 0 &&
                   new TargetFilter().own().SIGNI().withName("コード２４３４　ドーラ").getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            
            if(cardIndex != null && payEner(Cost.color(CardColor.WHITE, 2) + Cost.colorless(1)))
            {
                addToHand(cardIndex);
            }
        }
    }
}
