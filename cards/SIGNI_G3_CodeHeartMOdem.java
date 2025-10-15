package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G3_CodeHeartMOdem extends Card {
    
    public SIGNI_G3_CodeHeartMOdem()
    {
        setImageSets("WXDi-P05-043", "PR-Di031");
        
        setOriginalName("コードハート　Ｍデム");
        setAltNames("コードハートエムデム Koodo Haato Em Dem MDem");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、あなたのトラッシュにあるスペルを２枚までゲームから除外する。ターン終了時まで、それのパワーをこの方法でゲームから除外したスペル１枚につき－3000する。\n" +
                "@U $T1：あなたがスペルを使用したとき、あなたか対戦相手のデッキの上からカードを２枚トラッシュに置く。"
        );
        
        setName("en", "M - Odem, Code: Heart");
        setDescription("en",
                "@U: At the beginning of your attack phase, remove up to two spells in your trash from the game. Target SIGNI on your opponent's field gets --3000 power for each spell removed this way until end of turn.\n" +
                "@U $T1: When you use a spell, put the top two cards of your deck or your opponent's deck into the trash."
        );
        
        setName("en_fan", "Code Heart M Odem");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and exclude up to 2 spells from your trash. Until end of turn, that SIGNI gets --3000 power for each spell excluded this way.\n" +
                "@U $T1: When you use a spell, put the top 2 cards of your or your opponent's deck into the trash."
        );
        
		setName("zh_simplified", "爱心代号 调制解调器");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，你的废弃区的魔法2张最多从游戏除外。直到回合结束时为止，其的力量依据这个方法从游戏除外的魔法的数量，每有1张就-3000。\n" +
                "@U $T1 :当你把魔法使用时，从你或对战对手的牌组上面把2张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.USE_SPELL, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.EXCLUDE).own().spell().fromTrash());
                
                if(data.get() != null)
                {
                    int count = exclude(data);
                    gainPower(target, -3000 * count, ChronoDuration.turnEnd());
                }
            }
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            millDeck(playerChoiceAction(ActionHint.OWN, ActionHint.OPPONENT) == 1 ? getOwner() : getOpponent(), 2);
        }
    }
}
