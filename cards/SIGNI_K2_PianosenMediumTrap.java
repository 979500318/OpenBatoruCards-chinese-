package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_K2_PianosenMediumTrap extends Card {

    public SIGNI_K2_PianosenMediumTrap()
    {
        setImageSets("WXK01-107");

        setOriginalName("中罠　ピアノセン");
        setAltNames("チュウビンピアノセン Chuubin Pianosen");
        setDescription("jp",
                "@E：あなたのデッキの一番上のカードをトラッシュに置く。その後、それがレベルが偶数のシグニの場合、%Kを支払ってもよい。そうした場合、対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Pianosen, Medium Trap");
        setDescription("en",
                "@E: Put the top card of your deck into the trash. Then, if it is a SIGNI with an even level, target 1 of your opponent's level 2 or lower SIGNI, and you may pay %K. If you do, banish it."
        );

		setName("zh_simplified", "中罠 钢琴线");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面的牌放置到废弃区。然后，其是等级在偶数的精灵的场合，可以支付%K。这样做的场合，对战对手的等级2以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(2);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = millDeck(1).get();
            
            if(cardIndex != null && CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) && cardIndex.getIndexedInstance().getLevelByRef() % 2 == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
                {
                    banish(target);
                }
            }
        }
    }
}
