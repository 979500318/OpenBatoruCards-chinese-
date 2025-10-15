package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K3_IzoWickedGeneral extends Card {
    
    public SIGNI_K3_IzoWickedGeneral()
    {
        setImageSets("WXDi-P03-087");
        
        setOriginalName("凶将　イゾウ");
        setAltNames("キョウショウイゾウ Kyoushou Izou");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。\n" +
                "@A @[手札を２枚捨てる]@：このカードをトラッシュから中央のシグニゾーンに出す。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "Izo, Doomed General");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --2000 power until end of turn.\n\n" +
                "@A @[Discard two cards]@: Put this card from your trash into your center SIGNI Zone. " +
                "~#Add target SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Izo, Wicked General");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power.\n" +
                "@A @[Discard 2 cards from your hand]@: Put this card from your trash onto your center SIGNI zone." +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );
        
		setName("zh_simplified", "凶将 冈田以藏");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n" +
                "@A 手牌2张舍弃:这张牌从废弃区在中央的精灵区出场。（这个能力只有在这张牌在废弃区的场合才能使用）" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new DiscardCost(2), this::onActionEff);
            act.setActiveLocation(CardLocation.TRASH);
            act.setCondition(this::onActionCondition);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
        
        private ConditionState onActionCondition()
        {
            return isPlayable() && new TargetFilter().own().SIGNI().fromLocation(CardLocation.SIGNI_CENTER).getValidTargetsCount() == 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH)
            {
                putOnField(getCardIndex(), CardLocation.SIGNI_CENTER);
            }
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
