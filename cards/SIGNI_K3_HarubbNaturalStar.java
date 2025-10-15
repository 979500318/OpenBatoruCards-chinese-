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
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_K3_HarubbNaturalStar extends Card {
    
    public SIGNI_K3_HarubbNaturalStar()
    {
        setImageSets("WXDi-P08-080");
        
        setOriginalName("羅星　ハルッブ");
        setAltNames("ラセイハルッブ Rasei Haruppu");
        setDescription("jp",
                "@U：対戦相手のアタックフェイズ開始時、ターン終了時まで、対戦相手のすべてのレベル１のシグニは@>@U：このシグニがアタックしたとき、ターン終了時まで、このシグニのパワーを－8000する。@@を得る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。\n" +
                "$$2あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Habble, Natural Planet");
        setDescription("en",
                "@U: At the beginning of your opponent's attack phase, all level one SIGNI on your opponent's field gain@>@U: Whenever this SIGNI attacks, it gets --8000 power until end of turn.@@until end of turn." +
                "~#Choose one -- \n$$1 Target SIGNI on your opponent's field gets --8000 power until end of turn. \n$$2 Add target SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "Harubb, Natural Star");
        setDescription("en_fan",
                "@U: At the beginning of your opponent's attack phase, all of your opponent's level 1 SIGNI gain:" +
                "@>@U: Whenever this SIGNI attacks, until end of turn, this SIGNI gets --8000 power.@@" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power.\n" +
                "$$2 Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "罗星 哈勃空间望远镜");
        setDescription("zh_simplified", 
                "@U :对战对手的攻击阶段开始时，直到回合结束时为止，对战对手的全部的等级1的精灵得到\n" +
                "@>@U :当这只精灵攻击时，直到回合结束时为止，这只精灵的力量-8000。@@" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n" +
                "$$2 从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            forEachSIGNIOnField(getOpponent(), cardIndex -> {
                if(cardIndex.getIndexedInstance().getLevelByRef() == 1)
                {
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                    attachAbility(cardIndex, attachedAuto, ChronoDuration.turnEnd());
                }
            });
        }
        private void onAttachedAutoEff()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().gainPower(getAbility().getSourceCardIndex(), -8000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -8000, ChronoDuration.turnEnd());
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
                addToHand(target);
            }
        }
    }
}
