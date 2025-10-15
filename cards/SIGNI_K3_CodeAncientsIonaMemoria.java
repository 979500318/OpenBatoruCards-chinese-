package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.AttackModifierFlag;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.PrintedValue;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AttackModifier;

public final class SIGNI_K3_CodeAncientsIonaMemoria extends Card {
    
    public SIGNI_K3_CodeAncientsIonaMemoria()
    {
        setImageSets("WXDi-P06-042", "WXDi-P06-042P", "SPDi02-14");
        
        setOriginalName("コードアンシエンツ イオナ//メモリア");
        setAltNames("コードアンシエンツイオナメモリア Koodo Anshientsu Iona Memoria");
        setDescription("jp",
                "@C：このシグニの正面のシグニは可能ならばアタックしなければならない。\n" +
                "@U：このシグニがアタックしたとき、表記されているパワーと異なるパワーの対戦相手のシグニ１体を対象とし、%K %Kを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "@E：このシグニがトラッシュから場に出た場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－1000する。"
        );
        
        setName("en", "Iona//Memoria, Code: Ancients");
        setDescription("en",
                "@C: The SIGNI in front of this SIGNI must attack if able. \n" +
                "@U: Whenever this SIGNI attacks, you may pay %K %K. If you do, vanish target SIGNI on your opponent's field whose power is different than its originally listed power.\n" +
                "@E: If this SIGNI entered the field from the trash, target SIGNI on your opponent's field gets --1000 power until end of turn."
        );
        
        setName("en_fan", "Code Ancients Iona//Memoria");
        setDescription("en_fan",
                "@C: The SIGNI in front of this SIGNI must attack if able.\n" +
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power different from its printed power, and you may pay %K %K. If you do, banish it.\n" +
                "@E: If this SIGNI entered the field from your trash, target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power."
        );
        
		setName("zh_simplified", "古神代号 伊绪奈//回忆");
        setDescription("zh_simplified", 
                "@C :如果这只精灵的正面的精灵能攻击，则必须攻击。（必须在其他的精灵攻击前攻击）\n" +
                "@U :当这只精灵攻击时，与正面记载的力量不同力量的对战对手的精灵1只作为对象，可以支付%K %K。这样做的场合，将其破坏。\n" +
                "@E :这只精灵从废弃区出场的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-1000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
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
            
            registerConstantAbility(this::onConstEffSharedCond, new TargetFilter().OP().SIGNI(), new AttackModifier(AttackModifierFlag.FORCE_ATTACK));
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onConstEffSharedCond(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getOppositeSIGNI() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPrintedPower(PrintedValue.LOWER_THAN_CURRENT, PrintedValue.HIGHER_THAN_CURRENT)).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 2)))
            {
                banish(target);
            }
        }
        
        private void onEnterEff()
        {
            if(getCardIndex().getOldLocation() == CardLocation.TRASH)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -1000, ChronoDuration.turnEnd());
            }
        }
    }
}
