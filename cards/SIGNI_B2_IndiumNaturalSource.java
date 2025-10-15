package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B2_IndiumNaturalSource extends Card {
    
    public SIGNI_B2_IndiumNaturalSource()
    {
        setImageSets("WXDi-P07-073");
        
        setOriginalName("羅原　Ｉｎ");
        setAltNames("ラゲンインジウム Ragen Injiumu");
        setDescription("jp",
                "@U：対戦相手のアタックフェイズ開始時、ターン終了時まで、あなたのセンタールリグは@>@U：対戦相手のターン終了時、あなたのすべてのシグニをバニッシュする。@@を得る。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );
        
        setName("en", "In, Natural Element");
        setDescription("en",
                "@U: At the beginning of your opponent's attack phase, your Center LRIG gains@>@U: At the end of your opponent's turn, vanish all SIGNI on your field.@@until end of turn." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Indium, Natural Source");
        setDescription("en_fan",
                "@U: At the beginning of your opponent's attack phase, until end of turn, your center LRIG gains:" +
                "@>@U: At the end of your opponent's turn, banish all of your SIGNI.@@" +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );
        
		setName("zh_simplified", "罗原 In");
        setDescription("zh_simplified", 
                "@U :对战对手的攻击阶段开始时，直到回合结束时为止，你的核心分身得到\n" +
                "@>@U :对战对手的回合结束时，你的全部的精灵破坏。@@" +
                "~#对战对手的精灵1只作为对象，将其横直并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            attachAbility(getLRIG(getOwner()), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            banish(getSIGNIOnField(getOwner()));
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
