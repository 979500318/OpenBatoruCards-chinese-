package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R3_KagutsuchiCrimsonAngel extends Card {
    
    public SIGNI_R3_KagutsuchiCrimsonAngel()
    {
        setImageSets("WXDi-D03-015");
        
        setOriginalName("紅天　カグツチ");
        setAltNames("コウテンカグツチ Kouten Kagutsuchi");
        setDescription("jp",
                "@C：あなたのターンの間、あなたのシグニのパワーを＋2000する。\n" +
                "@E @[手札を２枚捨てる]@：あなたのトラッシュからレベル３のシグニ１枚を対象とし、それを場に出す。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "Kagutsuchi, Crimson Angel");
        setDescription("en",
                "@C: During your turn, SIGNI on your field get +2000 power.\n" +
                "@E @[Discard two cards]@: Put target level three SIGNI from your trash onto your field." +
                "~#Add target SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Kagutsuchi, Crimson Angel");
        setDescription("en_fan",
                "@C: During your turn, your SIGNI get +2000 power.\n" +
                "@E @[Discard 2 cards from your hand]@: Target 1 level 3 SIGNI from your trash, and put it onto the field." +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );
        
		setName("zh_simplified", "红天 迦具土");
        setDescription("zh_simplified", 
                "@C :你的回合期间，你的精灵的力量+2000。\n" +
                "@E 手牌2张舍弃:从你的废弃区把等级3的精灵1张作为对象，将其出场。" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerConstantAbility(this::onConstEffSharedCond, new TargetFilter().own().SIGNI(), new PowerModifier(2000));
            
            registerEnterAbility(new DiscardCost(2), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffSharedCond(CardIndex cardIndex)
        {
            return isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(3).playable().fromTrash()).get();
            putOnField(target);
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
