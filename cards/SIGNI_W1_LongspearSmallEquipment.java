package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_W1_LongspearSmallEquipment extends Card {

    public SIGNI_W1_LongspearSmallEquipment()
    {
        setImageSets("WXDi-P15-084");

        setOriginalName("小装　ロングスピア");
        setAltNames("ショウソウロングスピア Shousou Rongusupia");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのルリグ１体を対象とし、アップ状態のこのシグニをダウンしてもよい。そうした場合、ターン終了時まで、それは@>@U $T1：このルリグがアタックしたとき、対戦相手のシグニ１体を対象とし、それをトラッシュに置く。@@を得る。"
        );

        setName("en", "Long Spear, Lightly Armed");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may down this upped SIGNI. If you do, target LRIG on your field gains@>@U $T1: When this LRIG attacks, put target SIGNI on your opponent's field into its owner's trash.@@until end of turn."
        );
        
        setName("en_fan", "Longspear, Small Equipment");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your LRIG, and you may down this upped SIGNI. If you do, until end of turn, it gains:" +
                "@>@U $T1: Whenever this LRIG attacks, target 1 of your opponent's SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "小装 长矛");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的分身1只作为对象，可以把竖直状态的这只精灵横置。这样做的场合，直到回合结束时为止，其得到\n" +
                "@>@U $T1 :当这只分身攻击时，对战对手的精灵1只作为对象，将其放置到废弃区。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!getCardIndex().isSIGNIOnField()) return;
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().anyLRIG()).get();
            
            if(!isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down(getCardIndex()))
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex sourceCardIndex = getAbility().getSourceCardIndex();
            
            CardIndex target = sourceCardIndex.getIndexedInstance().playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            sourceCardIndex.getIndexedInstance().trash(target);
        }
    }
}
