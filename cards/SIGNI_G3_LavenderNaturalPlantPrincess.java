package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.*;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_G3_LavenderNaturalPlantPrincess extends Card {
    
    public SIGNI_G3_LavenderNaturalPlantPrincess()
    {
        setImageSets("WXDi-P06-039");
        
        setOriginalName("羅植姫　ラベンダー");
        setAltNames("ラショクヒメラベンダー Rashokuhime Rabendaa");
        setDescription("jp",
                "@U：あなたのメインフェイズ以外でこのシグニが場を離れたとき、あなたのトラッシュからこのシグニの下にあったシグニ１枚を対象とし、%X %Xを支払ってもよい。%X %Xを支払った場合、それをダウン状態で場に出す。それの@E能力は発動しない。%X %Xを支払わなかった場合、それを手札に加える。\n" +
                "@E：あなたのエナゾーンから#Gを持たないシグニ１枚を対象とし、それをこのシグニの下に置く。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Lavender, Natural Plant Queen");
        setDescription("en",
                "@U: When this SIGNI leaves the field outside of your main phase, you may pay %X %X. If you paid %X %X, put target SIGNI from your trash that was underneath this SIGNI onto your field downed. The @E abilities of SIGNI put onto your field this way do not activate. If you did not pay %X %X, add it to your hand.\n" +
                "@E: Put target SIGNI without a #G from your Ener Zone under this SIGNI." +
                "~#Vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "Lavender, Natural Plant Princess");
        setDescription("en_fan",
                "@U: When this SIGNI leaves the field other than during your main phase, target 1 SIGNI from your trash that was under this SIGNI, and you may pay %X %X. If you paid %X %X, put it onto the field downed. Its @E abilities don't activate. If you did not pay %X %X, add it to your hand.\n" +
                "@E: Target 1 SIGNI without #G @[Guard]@ from your ener zone, and put it under this SIGNI." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );
        
		setName("zh_simplified", "罗植姬 薰衣草");
        setDescription("zh_simplified", 
                "@U 当在你的主要阶段以外把这只精灵离场时，从你的废弃区把这只精灵的下面原有的精灵1张作为对象，可以支付%X %X。支付%X %X的场合，将其以横置状态出场。其的@E能力不能发动。不支付%X %X:的场合，将其加入手牌。\n" +
                "@E 从你的能量区把不持有#G的精灵1张作为对象，将其放置到这只精灵的下面。" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private DataTable<CardIndex> dataUnder;
        private ConditionState onAutoEffCond()
        {
            if((isOwnTurn() && getCurrentPhase() == GamePhase.MAIN) || CardLocation.isSIGNI(EventMove.getDataMoveLocation())) return ConditionState.BAD;
            
            dataUnder = new TargetFilter().own().SIGNI().under(getCardIndex()).getExportedData();
            return ConditionState.OK;
        }
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().fromTrash().match(dataUnder)).get();
            
            if(target != null)
            {
                if(payEner(Cost.colorless(2)))
                {
                    putOnField(target, Enter.DOWNED | Enter.DONT_ACTIVATE);
                } else {
                    addToHand(target);
                }
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.UNDER).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromEner()).get();
            attach(getCardIndex(), target, CardUnderType.UNDER_GENERIC);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
