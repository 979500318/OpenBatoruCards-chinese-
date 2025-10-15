package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class PIECE_X_GeneralStudentCouncil extends Card {

    public PIECE_X_GeneralStudentCouncil()
    {
        setImageSets("WXDi-CP02-005");

        setOriginalName("連邦生徒会");
        setAltNames("レンポウセイトカイ Renpouseitokai");
        setDescription("jp",
                "あなたのデッキの上からカードを３枚見る。その中から＜ブルアカ＞のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。この方法で公開した生徒との絆を獲得する。"
        );

        setName("en", "General Student Council");
        setDescription("en",
                "Look at the top three cards of your deck. Reveal a <<Blue Archive>> SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order. Start a relationship with the student revealed this way. "
        );
        
        setName("en_fan", "General Student Council");
        setDescription("en_fan",
                "Look at the top 3 cards of your deck. Reveal 1 <<Blue Archive>> SIGNI from among them, add it to your hand, and put the rest on the bottom of your deck in any order. Gain a bond with the student revealed this way."
        );

		setName("zh_simplified", "联邦学生会");
        setDescription("zh_simplified", 
                "从你的牌组上面看3张牌。从中把<<ブルアカ>>精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。获得与这个方法公开的学生的羁绊。（那名学生的[羁绊]能力变为有效）\n"
        );

        setType(CardType.PIECE);
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

            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            look(3);

            CardIndex cardIndexChosen = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromLooked()).get();
            reveal(cardIndexChosen);
            addToHand(cardIndexChosen);

            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }

            if(cardIndexChosen != null) gainBond(cardIndexChosen.getImageSet());
        }
    }
}
