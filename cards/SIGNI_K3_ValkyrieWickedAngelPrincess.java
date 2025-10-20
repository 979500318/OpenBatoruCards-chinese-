package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.Enter;

public final class SIGNI_K3_ValkyrieWickedAngelPrincess extends Card {
    
    public SIGNI_K3_ValkyrieWickedAngelPrincess()
    {
        setImageSets("WXDi-P02-045");
        
        setOriginalName("凶天姫　ヴァルキリー");
        setAltNames("キョウテンキヴァルキリー Kyoutenki Varukirii");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのトラッシュからレベル１の＜天使＞のシグニ１枚を対象とし、それを手札に加える。\n" +
                "@U：このシグニがバニッシュされたとき、手札から＜天使＞のシグニを２枚捨ててもよい。そうした場合、このシグニをエナゾーンからダウン状態で場に出す。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "Valkyrie, Doomed Angel Queen");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, add target level one <<Angel>> SIGNI from your trash to your hand.\n" +
                "@U: When this SIGNI is vanished, you may discard two <<Angel>> SIGNI. If you do, put this SIGNI from your Ener Zone onto your field downed." +
                "~#Add target SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Valkyrie, Wicked Angel Princess");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, target 1 level 1 <<Angel>> SIGNI from your trash, and add it to your hand.\n" +
                "@U: When this SIGNI is banished, you may discard 2 <<Angel>> SIGNI from your hand. If you do, put this SIGNI from the ener zone onto the field downed." +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );
        
		setName("zh_simplified", "凶天姬 瓦尔基里");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从你的废弃区把等级1的<<天使>>精灵1张作为对象，将其加入手牌。\n" +
                "@U :当这只精灵被破坏时，可以从手牌把<<天使>>精灵2张舍弃。这样做的场合，这张精灵从能量区以横置状态出场。" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withLevel(1).withClass(CardSIGNIClass.ANGEL).fromTrash()).get();
            addToHand(target);
        }
        private void onAutoEff2()
        {
            if(getCardIndex().getLocation() == CardLocation.ENER &&
               discard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter().SIGNI().withClass(CardSIGNIClass.ANGEL)).size() == 2)
            {
                putOnField(getCardIndex(), Enter.DOWNED);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
            if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
            {
                addToHand(target);
            } else {
                putOnField(target);
            }
        }
    }
}
