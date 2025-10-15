package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.RevealCost;

public final class SIGNI_R2_AphroditeCrimsonAngel extends Card {
    
    public SIGNI_R2_AphroditeCrimsonAngel()
    {
        setImageSets("WXDi-P06-055");
        
        setOriginalName("紅天　アフロディテ");
        setAltNames("コウテンアフロディテ Kouten Afurodite");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のパワー12000以下のシグニ１体を対象とし、あなたの手札から＜天使＞のシグニを６枚公開し%R %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Aphrodite, Crimson Angel");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may reveal six <<Angel>> SIGNI from your hand and pay %R %X. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Aphrodite, Crimson Angel");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 12000 or less, and you may reveal 6 <<Angel>> SIGNI from your hand and pay %R %X. If you do, banish it."
        );
        
		setName("zh_simplified", "红天 阿弗洛狄忒");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的力量12000以下的精灵1只作为对象，可以从你的手牌把<<天使>>精灵6张公开并支付%R%X。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null &&
               payAll(new RevealCost(6, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromHand()), new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1))))
            {
                banish(target);
            }
        }
    }
}
