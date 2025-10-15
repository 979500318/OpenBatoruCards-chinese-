package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K3_EclipseNaturalStarPrincess extends Card {
    
    public SIGNI_K3_EclipseNaturalStarPrincess()
    {
        setImageSets("WXDi-P05-042");
        
        setOriginalName("羅星姫　イクリプス");
        setAltNames("ラセイキイクリプス Raseiki Ikuripusu");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。その後、この方法でトラッシュに置かれたすべてのカードがレベル１のシグニの場合、対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－10000する。\n\n" +
                "@U：このカードがデッキからトラッシュに置かれたとき、あなたのトラッシュから#Gを持たないレベル１のシグニ１枚を対象とし、%Xを支払ってもよい。そうした場合、それを手札に加える。"
        );
        
        setName("en", "Eclipse, Natural Planet Queen");
        setDescription("en",
                "@E: Put the top three cards of your deck into your trash. Then, if all cards put into the trash this way are level one SIGNI, you may pay %X. If you do, target SIGNI on your opponent's field gets --10000 power until end of turn.\n\n" +
                "@U: When this card is put into your trash from your deck, you may pay %X. If you do, add target level one SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "Eclipse, Natural Star Princess");
        setDescription("en_fan",
                "@E: Put the top 3 cards of your deck into the trash. Then, if all of the cards put into the trash this way are level 1 SIGNI, target 1 of your opponent's SIGNI, and you may pay %X. If you do, until end of turn, it gets --10000 power.\n\n" +
                "@U: When this card is put from your deck into the trash, target 1 level 1 SIGNI without #G @[Guard]@ from your trash, and you may pay %X. If you do, add it to your hand."
        );
        
		setName("zh_simplified", "罗星姬 月食");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把3张牌放置到废弃区。然后，这个方法放置到废弃区的全部的牌是等级1的精灵的场合，对战对手的精灵1只作为对象，可以支付%X。这样做的场合，直到回合结束时为止，其的力量-10000。\n" +
                "@U 当这张牌从牌组放置到废弃区时，从你的废弃区把不持有#G的等级1的精灵1张作为对象，可以支付%X。这样做的场合，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(12000);
        
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setActiveLocation(CardLocation.DECK_MAIN);
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = millDeck(3);
            
            if(new TargetFilter().own().SIGNI().withLevel(1).fromTrash().match(data).getValidTargetsCount() == data.size())
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.colorless(1)))
                {
                    gainPower(target, -10000, ChronoDuration.turnEnd());
                }
            }
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withLevel(1).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                addToHand(target);
            }
        }
    }
}
