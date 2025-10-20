package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_B1_SharkSpearAzureDevil extends Card {
    
    public SIGNI_B1_SharkSpearAzureDevil()
    {
        setImageSets("WXDi-D05-012", "SPDi01-23");
        
        setOriginalName("蒼魔　シャークスピア");
        setAltNames("ソウマシャークスピア Souma Shaaku Supia");
        setDescription("jp",
                "@A #D：あなたのデッキの上からカードを３枚トラッシュに置く。その後、あなたのトラッシュにカードが２０枚以上ある場合、%X %Xを支払ってもよい。そうした場合、あなたのトラッシュから＜悪魔＞のシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Sharkspeare, Azure Evil");
        setDescription("en",
                "@A #D: Put the top three cards of your deck into your trash. Then, if you have twenty or more cards in your trash, you may pay %X %X. If you do, add target <<Demon>> SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Shark Spear, Azure Devil");
        setDescription("en_fan",
                "@A #D: Put the top 3 cards of your deck into the trash. Then, if there are 20 or more cards in your trash, you may pay %X %X. If you do, target 1 <<Devil>> SIGNI from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "苍魔 夏克丝比");
        setDescription("zh_simplified", 
                "@A 横置:从你的牌组上面把3张牌放置到废弃区。然后，你的废弃区的牌在20张以上的场合，可以支付%X %X。这样做的场合，从你的废弃区把<<悪魔>>精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            
            registerActionAbility(new DownCost(), this::onActionEff);
        }
        
        private void onActionEff()
        {
            millDeck(3);
            
            if(getTrashCount(getOwner()) >= 20 && payEner(Cost.colorless(2)))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromTrash()).get();
                addToHand(target);
            }
        }
    }
}
