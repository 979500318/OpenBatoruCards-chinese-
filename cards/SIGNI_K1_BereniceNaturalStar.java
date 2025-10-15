package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K1_BereniceNaturalStar extends Card {
    
    public SIGNI_K1_BereniceNaturalStar()
    {
        setImageSets("WXDi-P07-087");
        
        setOriginalName("羅星　ベレニケ");
        setAltNames("ラセイベレニケ Rasei Berenike");
        setDescription("jp",
                "@C：あなたのトラッシュにカードが１０枚以上あるかぎり、このシグニのパワーは＋4000される。\n" +
                "@U：このシグニがバニッシュされたとき、あなたか対戦相手のデッキの上からカードを２枚トラッシュに置く。" +
                "~#：あなたのトラッシュから#Gを持たないレベル２以下のシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "Berenices, Natural Planet");
        setDescription("en",
                "@C: As long as you have ten or more cards in your trash, this SIGNI gets +4000 power.\n" +
                "@U: When this SIGNI is vanished, put the top two cards of your deck or your opponent's deck into the trash." +
                "~#Add target level two or less SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Berenice, Natural Star");
        setDescription("en_fan",
                "@C: As long as there are 10 or more cards in your trash, this SIGNI gets +4000 power.\n" +
                "@U: When this SIGNI is banished, put the top 2 cards of your or your opponent's deck into the trash." +
                "~#Target 1 level 2 or lower SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );
        
		setName("zh_simplified", "罗星 后发座");
        setDescription("zh_simplified", 
                "@C :你的废弃区的牌在10张以上时，这只精灵的力量+4000。\n" +
                "@U :当这只精灵被破坏时，从你或对战对手的牌组上面把2张牌放置到废弃区。" +
                "~#从你的废弃区把不持有#G的等级2以下的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getTrashCount(getOwner()) >= 10 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onAutoEff()
        {
            millDeck(playerChoiceAction(ActionHint.OWN, ActionHint.OPPONENT) == 1 ? getOwner() : getOpponent(), 2);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().withLevel(0,2).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
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
