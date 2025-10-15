package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R2_BardicheMediumEquipment extends Card {
    
    public SIGNI_R2_BardicheMediumEquipment()
    {
        setImageSets("WXDi-P07-063");
        
        setOriginalName("中装　バルディッシュ");
        setAltNames("チュウソウバルディッシュ Chuusou Barudicche");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にレベル３のシグニが２体以上ある場合、%R %Rを支払ってもよい。そうした場合、ターン終了時まで、このシグニは@>@U：このシグニがアタックしたとき、対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。@@を得る。"
        );
        
        setName("en", "Bardiche, High Armed");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are two or more level three SIGNI on your field, you may pay %R %R. If you do, this SIGNI gains@>@U: Whenever this SIGNI attacks, vanish target SIGNI on your opponent's field with power 12000 or less.@@until end of turn."
        );
        
        setName("en_fan", "Bardiche, Medirum Equipment");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 2 or more level 3 SIGNI on your field, you may pay %R %R. If you do, until end of turn, this SIGNI gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power 12000 or less, and banish it."
        );
        
		setName("zh_simplified", "中装 巴迪什");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的等级3的精灵在2只以上的场合，可以支付%R %R。这样做的场合，直到回合结束时为止，这只精灵得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的力量12000以下的精灵1只作为对象，将其破坏。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(2);
        setPower(8000);
        
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
            if(new TargetFilter().own().SIGNI().withLevel(3).getValidTargetsCount() >= 2 &&
               payEner(Cost.color(CardColor.RED, 2)))
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
    }
}
