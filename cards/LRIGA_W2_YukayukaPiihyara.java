package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;

public final class LRIGA_W2_YukayukaPiihyara extends Card {

    public LRIGA_W2_YukayukaPiihyara()
    {
        setImageSets("WXDi-P05-025");

        setOriginalName("ゆかゆか☆ぴーひゃら");
        setAltNames("ユカユカピーヒャラ Yukayuka Piihyara");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中からシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。\n" +
                "@E：次のあなたのエナフェイズ終了時まで、あなたのセンタールリグのリミットを＋２する。"
        );

        setName("en", "Yukayuka☆Piihyara");
        setDescription("en",
                "@E: Look at the top five cards of your deck. Reveal up to two SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in any order.\n" +
                "@E: Until the end of your next Ener Phase, increase your Center LRIG's limit by two."
        );
        
        setName("en_fan", "Yukayuka☆Piihyara");
        setDescription("en_fan",
                "@E: Look at the top 5 cards of your deck. Reveal up to 2 SIGNI from among them, and add them to your hand, and return the rest to the bottom of your deck in any order.\n" +
                "@E: Until the end of your next ener phase, increase the limit of your center LRIG by +2."
        );

		setName("zh_simplified", "由香香☆哔呀啦");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看5张牌。从中把精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "@E :直到下一个你的充能阶段结束时为止，你的核心分身的界限+2。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.YUKAYUKA);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.WHITE);
        setLevel(2);
        setLimit(+1);
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            look(5);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked());
            reveal(data);
            addToHand(data);

            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }

        private void onEnterEff2()
        {
            gainValue(getLRIG(getOwner()), getLRIG(getOwner()).getIndexedInstance().getLimit(),2d, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
        }
    }
}
