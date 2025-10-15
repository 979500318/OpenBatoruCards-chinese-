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
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W2_NovaMemoriaNaturalStar extends Card {
    
    public SIGNI_W2_NovaMemoriaNaturalStar()
    {
        setImageSets("WXDi-P08-053", "WXDi-P08-053P", "SPDi01-77");
        
        setOriginalName("羅星　ノヴァ//メモリア");
        setAltNames("ラセイノヴァメモリア Rasei Nova Memoria");
        setDescription("jp",
                "@U：対戦相手のアタックフェイズ開始時、対戦相手のレベル２以下のシグニを１体まで対象とし、このシグニを場から手札に戻してよい。そうした場合、ターン終了時まで、それは@>@C：アタックできない。@@を得る。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );
        
        setName("en", "Nova//Memoria, Natural Planet");
        setDescription("en",
                "@U: At the beginning of your opponent's attack phase, you may return this SIGNI on your field to its owner's hand. If you do, up to one target level two or less SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn." +
                "~#Target SIGNI on your opponent's field gains@>@C@#: This SIGNI cannot attack.@@@@until end of turn. Draw a card."
        );
        
        setName("en_fan", "Nova//Memoria, Natural Star");
        setDescription("en_fan",
                "@U: At the beginning of your opponent's attack phase, you may return this SIGNI from your field to your hand. If you do, target 1 of your opponent's level 2 or lower SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );
        
		setName("zh_simplified", "罗星 超//回忆");
        setDescription("zh_simplified", 
                "@U :对战对手的攻击阶段开始时，对战对手的等级2以下的精灵1只最多作为对象，可以把这只精灵从场上返回手牌。这样做的场合，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
            if(getCardIndex().isSIGNIOnField() && playerChoiceActivate() && addToHand(getCardIndex()))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI().withLevel(0,2)).get();
                if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
