package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K2_KagomekaDissonaImitatingPlay extends Card {

    public SIGNI_K2_KagomekaDissonaImitatingPlay()
    {
        setImageSets("WXDi-P12-087");

        setOriginalName("似之遊　カゴメカ//ディソナ");
        setAltNames("ニノユウカゴメカディソナ Ni no Yuu Kagomeka Disona");
        setDescription("jp",
                "@A #D：あなたのデッキの上からカードを３枚トラッシュに置く。\n" +
                "@A %K @[このシグニを場からトラッシュに置く]@：あなたのトラッシュからレベル３の#Sのシグニ１枚を対象とし、それを場に出す。" +
                "~#：あなたのトラッシュから#Sのシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Kagomeka//Dissona, Mimicry Play");
        setDescription("en",
                "@A #D: Put the top three cards of your deck into your trash.\n" +
                "@A %K @[Put this SIGNI on your field into its owner's trash]@: Put target level three #S SIGNI from your trash onto your field." +
                "~#Add target #S SIGNI from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Kagomeka//Dissona, Imitating Play");
        setDescription("en_fan",
                "@A #D: Put the top 3 cards of your deck into the trash.\n" +
                "@A %K @[Put this SIGNI from the field into the trash]@: Target 1 level 3 #S @[Dissona]@ SIGNI from your trash, and put it onto the field." +
                "~#Target 1 #S @[Dissona]@ SIGNI from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "似之游 笼中鸟//失调");
        setDescription("zh_simplified", 
                "@A 横置:从你的牌组上面把3张牌放置到废弃区。\n" +
                "@A %K:这只精灵从场上放置到废弃区从你的废弃区把等级3的#S的精灵1张作为对象，将其出场。" +
                "~#从你的废弃区把#S的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
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
            
            registerActionAbility(new DownCost(), this::onActionEff1);
            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.BLACK, 1)), new TrashCost()), this::onActionEff2);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff1()
        {
            millDeck(3);
        }
        
        private void onActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().dissona().withLevel(3).fromTrash().playable()).get();
            putOnField(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().dissona().fromTrash()).get();
            
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
