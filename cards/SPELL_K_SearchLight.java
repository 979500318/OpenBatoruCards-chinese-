package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class SPELL_K_SearchLight extends Card {
    
    public SPELL_K_SearchLight()
    {
        setImageSets("WXDi-P02-089");
        
        setOriginalName("サーチ・ライト");
        setAltNames("サーチライト Saachi Raito");
        setDescription("jp",
                "各プレイヤーは自分のデッキの上からカードを３枚トラッシュに置く。その後、あなたのトラッシュにカードが２５枚以上ある場合、あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Search Light");
        setDescription("en",
                "Each player puts the top three cards of their deck into their trash. Then, if you have twenty five or more cards in your trash, add target SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "Search Light");
        setDescription("en_fan",
                "Each player puts the top 3 cards of their deck into the trash. Then, if there are 25 or more cards in your trash, target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "寻找·光芒");
        setDescription("zh_simplified", 
                "各玩家从自己的牌组上面把3张牌放置到废弃区。然后，你的废弃区的牌在25张以上的场合，从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerSpellAbility(this::onSpellEff);
        }
        
        private void onSpellEff()
        {
            millDeck(3);
            millDeck(getOpponent(), 3);
            
            if(getTrashCount(getOwner()) >= 25)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
                addToHand(target);
            }
        }
    }
}
