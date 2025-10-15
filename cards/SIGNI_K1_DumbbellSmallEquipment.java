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
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K1_DumbbellSmallEquipment extends Card {
    
    public SIGNI_K1_DumbbellSmallEquipment()
    {
        setImageSets("WXDi-P08-076");
        
        setOriginalName("小装　テツアレイ");
        setAltNames("ショウソウテツアレイ Shousou Tetsuarei");
        setDescription("jp",
                "@C：あなたの覚醒状態のシグニのパワーを＋3000する。\n" +
                "@U：あなたのターン終了時、このシグニは覚醒する。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );
        
        setName("en", "Dumbbell, Lightly Armed");
        setDescription("en",
                "@C: Awakened SIGNI on your field get +3000 power.\n" +
                "@U: At the end of your turn, this SIGNI is awakened. " +
                "~#Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Dumbbell, Small Equipment");
        setDescription("en_fan",
                "@C: All of your awakened SIGNI get +3000 power.\n" +
                "@U: At the end of your turn, this SIGNI awakens." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );
        
		setName("zh_simplified", "小装 哑铃");
        setDescription("zh_simplified", 
                "@C :你的觉醒状态的精灵的力量+3000。\n" +
                "@U :你的回合结束时，这只精灵觉醒。（精灵觉醒后在场上保持觉醒状态）" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new TargetFilter().own().SIGNI().withState(CardStateFlag.AWAKENED), new PowerModifier(3000));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
