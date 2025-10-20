package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_CodeMazeWekawa extends Card {
    
    public SIGNI_K3_CodeMazeWekawa()
    {
        setImageSets("WXDi-P01-087");
        
        setOriginalName("コードメイズ　ウェカワ");
        setAltNames("コードメイズウェカワ Koodo Meizu Uekawa");
        setDescription("jp",
                "@U：このシグニがコストか効果によって場からトラッシュに置かれたとき、あなたのトラッシュからレベル２以下の黒のシグニ１枚を対象とし、%Xを支払ってもよい。そうした場合、それをダウン状態で場に出す。\n" +
                "@E %X：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それをエナゾーンに置く。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "Kawasaki, Code: Maze");
        setDescription("en",
                "@U: When this SIGNI is put into your trash from the field by a cost or effect, you may pay %X. If you do, put target level two black SIGNI from your trash onto your field downed.\n" +
                "@E %X: Put target card without a #G in your trash into your Ener Zone." +
                "~#Add target SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Code Maze Wekawa");
        setDescription("en_fan",
                "@U: When this SIGNI is put from the field into the trash by a cost or effect, target 1 level 2 or lower black SIGNI from your trash, and you may pay %X. If you do, put it onto the field downed.\n" +
                "@E %X: Target 1 SIGNI without #G @[Guard]@ from your trash, and put it into the ener zone." +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );
        
		setName("zh_simplified", "迷宫代号 电脑九龙城寨");
        setDescription("zh_simplified", 
                "@U :当这只精灵因为费用或效果从场上放置到废弃区时，从你的废弃区把等级2以下的黑色的精灵1张作为对象，可以支付%X。这样做的场合，将其以横置状态出场。\n" +
                "@E %X从你的废弃区把不持有#G的牌1张作为对象，将其放置到能量区。" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getEvent().getSourceAbility() != null && getCardIndex().isSIGNIOnField() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.BLACK).withLevel(0,2).playable().fromTrash()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                putOnField(target, Enter.DOWNED);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            putInEner(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
