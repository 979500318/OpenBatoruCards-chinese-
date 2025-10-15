package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_IntenseGabriela extends Card {

    public LRIGA_W1_IntenseGabriela()
    {
        setImageSets("WXDi-P16-032");

        setOriginalName("凛々！！ガブリエラ");
        setAltNames("リンリンガブリエラ Rinrin Gaburiera");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。その中からレベル１とレベル２とレベル３のシグニをそれぞれ１枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Gabriela, Majestic!!");
        setDescription("en",
                "@E: Look at the top three cards of your deck. Reveal up to one level one SIGNI, one level two SIGNI, and one level three SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Intense!! Gabriela");
        setDescription("en_fan",
                "@E: Look at the top 3 cards of your deck. Reveal up to 1 level 1, up to 1 level 2, and up to 1 level 3 SIGNI from among them, add them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "凛凛！！哲布伊来");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看3张牌。从中把等级1和等级2和等级3的精灵各1张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.GABRIELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.WHITE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            look(3);
            
            DataTable<CardIndex> data = new DataTable<>();
            for(int i=1;i<=3;i++)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().except(data).withLevel(i).fromLooked()).get();
                data.add(cardIndex);
            }
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
