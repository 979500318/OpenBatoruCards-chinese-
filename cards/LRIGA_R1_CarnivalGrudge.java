package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_R1_CarnivalGrudge extends Card {

    public LRIGA_R1_CarnivalGrudge()
    {
        setImageSets("WXDi-P11-030");

        setOriginalName("カーニバル　－怨－");
        setAltNames("カーニバルオン Kaanibaru On");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを４枚見る。その中からシグニを２枚まで場に出し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Carnival -Grudge-");
        setDescription("en",
                "@E: Look at the top four cards of your deck. Put up to two SIGNI from among them onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Carnival -Grudge-");
        setDescription("en_fan",
                "@E: Look at the top 4 cards of your deck. Put up to 2 SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "嘉年华 -怨-");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看4张牌。从中把精灵2张最多出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.CARNIVAL);
        setColor(CardColor.RED);
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
        }

        private void onEnterEff()
        {
            look(4);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable());
            putOnField(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
