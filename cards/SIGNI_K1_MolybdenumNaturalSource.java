package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_K1_MolybdenumNaturalSource extends Card {
    
    public SIGNI_K1_MolybdenumNaturalSource()
    {
        setImageSets("WXDi-P02-083");
        
        setOriginalName("羅原　Ｍｏ");
        setAltNames("ラゲンモリブデン Ragen Moribuden");
        setDescription("jp",
                "@U：対戦相手のレベル２以下のシグニ１体がこのシグニの正面のシグニゾーンに出たとき、このシグニを場からトラッシュに置いてもよい。そうした場合、正面にあったそのシグニをトラッシュに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、%Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－8000する。"
        );
        
        setName("en", "Mo, Natural Element");
        setDescription("en",
                "@U: Whenever a level two or less SIGNI enters the SIGNI Zone in front of this SIGNI, you may put this SIGNI on your field into its owner's trash. If you do, put the SIGNI that entered the SIGNI Zone into its owner's trash." +
                "~#You may pay %K. If you do, target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Molybdenum, Natural Source");
        setDescription("en_fan",
                "@U: Whenever 1 of your opponent's level 2 or lower SIGNI enters the SIGNI zone in front of this SIGNI, you may put this SIGNI from the field into the trash. If you do, put that SIGNI in front into the trash." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it gets --8000 power."
        );
        
		setName("zh_simplified", "罗原 Mo");
        setDescription("zh_simplified", 
                "@U :当对战对手的等级2以下的精灵1只在这只精灵的正面的精灵区出场时，可以把这只精灵从场上放置到废弃区。这样做的场合，正面原有的那只精灵放置到废弃区。" +
                "~#对战对手的精灵1只作为对象，可以支付%K。这样做的场合，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) &&
                    caller.getIndexedInstance().getLevel().getValue() <= 2 &&
                    CardLocation.getOppositeSIGNILocation(caller.getLocation()) == getCardIndex().getLocation() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getCardIndex().isSIGNIOnField() && playerChoiceActivate() && trash(getCardIndex()))
            {
                trash(caller);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(1, new TargetFilter().OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
            {
                gainPower(target, -8000, ChronoDuration.turnEnd());
            }
        }
    }
}
