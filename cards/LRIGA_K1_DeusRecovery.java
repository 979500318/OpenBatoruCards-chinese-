package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K1_DeusRecovery extends Card {
    
    public LRIGA_K1_DeusRecovery()
    {
        setImageSets("WXDi-P04-022");
        
        setOriginalName("デウスリカバリ");
        setAltNames("Deusu Rikabari");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを２枚トラッシュに置く。その後、あなたのトラッシュから赤と黒のシグニをそれぞれ１枚まで対象とし、それらを手札に加える。\n" +
                "@U：このカードがエクシードのコストとしてルリグトラッシュに置かれたとき、あなたのトラッシュから赤か黒のシグニ１枚を対象とし、手札を１枚捨ててもよい。そうした場合、それを手札に加える。"
        );
        
        setName("en", "Deus Recovery");
        setDescription("en",
                "@E: Put the top two cards of your deck into your trash. Then, add up to one target red SIGNI and one target black SIGNI from your trash to your hand.\n\n" +
                "@U: When this card is put into the LRIG Trash as an Exceed cost, you may discard a card. If you do, add target red or black SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Deus Recovery");
        setDescription("en_fan",
                "@E: Put the top 2 cards of your deck into the trash. Then, target up to 1 red and black SIGNI each from your trash, and add them to your hand.\n" +
                "@U: When this card is put into your LRIG trash for an @[Exceed]@ cost, target 1 red or black SIGNI from your trash, and you may discard 1 card from your hand. If you do, add it to your hand."
        );
        
		setName("zh_simplified", "迪乌斯复原");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把2张牌放置到废弃区。然后，从你的废弃区把红色和黑色的精灵各1张最多作为对象，将这些加入手牌。\n" +
                "@U :当这张牌作为超越的费用放置到分身废弃区时，从你的废弃区把红色或黑色的精灵1张作为对象，可以把手牌1张舍弃。这样做的场合，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.DEUS);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
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
            
            registerAutoAbility(GameEventId.EXCEED, this::onAutoEff);
        }
        
        private void onEnterEff()
        {
            millDeck(2);
            
            DataTable<CardIndex> data = new DataTable<>();
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.RED).fromTrash()).get();
            if(target != null) data.add(target);
            
            target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).except(data).fromTrash()).get();
            if(target != null) data.add(target);
            
            addToHand(data);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.RED, CardColor.BLACK).fromTrash()).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                addToHand(target);
            }
        }
    }
}
