package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K1_AzraelWickedAngel extends Card {
    
    public SIGNI_K1_AzraelWickedAngel()
    {
        setImageSets("WXDi-P01-078");
        
        setOriginalName("凶天　アズライール");
        setAltNames("キョウテンアズライール Kyouten Azuraiiru");
        setDescription("jp",
                "@E %X %X：あなたのトラッシュから＜天使＞のシグニ１枚を対象とし、それを手札に加える。" +
                "~#：あなたのトラッシュから＜天使＞のシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "Azrael, Doomed Angel");
        setDescription("en",
                "@E %X %X: Add target <<Angel>> SIGNI from your trash to your hand." +
                "~#Add target <<Angel>> SIGNI from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Azrael, Wicked Angel");
        setDescription("en_fan",
                "@E %X %X: Target 1 <<Angel>> SIGNI from your trash, and add it to your hand." +
                "~#Target 1 <<Angel>> SIGNI from your trash, and add it to your hand or put it onto the field."
        );
        
		setName("zh_simplified", "凶天 亚兹拉尔");
        setDescription("zh_simplified", 
                "@E %X %X:从你的废弃区把<<天使>>精灵1张作为对象，将其加入手牌。" +
                "~#从你的废弃区把<<天使>>精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash()).get();
            addToHand(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash()).get();
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
