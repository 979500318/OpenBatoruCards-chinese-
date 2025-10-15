package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_G1_CodeAntiCuneiform extends Card {

    public SIGNI_G1_CodeAntiCuneiform()
    {
        setImageSets("WX25-P1-091");

        setOriginalName("コードアンチ　クサビモジ");
        setAltNames("コードアンチクサビモジ Koodo Anchi Kusabimoji");
        setDescription("jp",
                "@E @[手札から＜古代兵器＞のシグニを１枚捨てる]@：あなたのデッキの上からカードを３枚見る。その中からカードを１枚までエナゾーンに置き、カードを１枚までトラッシュに置き、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Code Anti Cuneiform");
        setDescription("en",
                "@E @[Discard 1 <<Ancient Weapon>> SIGNI from your hand]@: Look at the top 3 cards of your deck. Put up to 1 card from among them into the ener zone, put up to 1 card from among them into the trash, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "古兵代号 楔形文字");
        setDescription("zh_simplified", 
                "@E 从手牌把<<古代兵器>>精灵1张舍弃:从你的牌组上面看3张牌。从中把牌1张最多放置到能量区，牌1张最多放置到废弃区，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.ANCIENT_WEAPON)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ENER).own().fromLooked()).get();
            putInEner(cardIndex);

            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().fromLooked()).get();
            trash(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
