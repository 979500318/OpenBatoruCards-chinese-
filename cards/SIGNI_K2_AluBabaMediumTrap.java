package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class SIGNI_K2_AluBabaMediumTrap extends Card {

    public SIGNI_K2_AluBabaMediumTrap()
    {
        setImageSets("WXK01-106");

        setOriginalName("中罠　アルババ");
        setAltNames("チュウビンアルババ Chuubin Arubaba");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを２枚トラッシュに置く。その後、この方法でレベルが偶数のシグニ２枚がトラッシュに置かれた場合、あなたのトラッシュからレベル２以下のシグニ１枚を対象とし、それを場に出す。"
        );

        setName("en", "Alu Baba, Medium Trap");
        setDescription("en",
                "@E: Put the top 2 cards of your deck into the trash. Then, if 2 SIGNI with even levels were put into the trash this way, target 1 level 2 or lower SIGNI from your trash, and put it onto the field."
        );

		setName("zh_simplified", "中罠 黑阿里巴巴");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把2张牌放置到废弃区。然后，这个方法把等级在偶数的精灵2张放置到废弃区的场合，从你的废弃区把等级2以下的精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(2);
        setPower(5000);

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
            DataTable<CardIndex> data = millDeck(2);
            
            if(data.size() == 2 &&
               CardType.isSIGNI(data.get(0).getIndexedInstance().getTypeByRef()) && data.get(0).getIndexedInstance().getLevelByRef() % 2 == 0 &&
               CardType.isSIGNI(data.get(1).getIndexedInstance().getTypeByRef()) && data.get(1).getIndexedInstance().getLevelByRef() % 2 == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(0,2).fromTrash().playable()).get();
                putOnField(target);
            }
        }
    }
}
