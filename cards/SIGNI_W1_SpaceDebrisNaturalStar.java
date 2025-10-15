package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_W1_SpaceDebrisNaturalStar extends Card {

    public SIGNI_W1_SpaceDebrisNaturalStar()
    {
        setImageSets("WX24-P3-060");

        setOriginalName("羅星　スペースデブリ");
        setAltNames("ラセイスペースデブリ Rasei Supeesu Deburi");
        setDescription("jp",
                "@E @[手札から＜宇宙＞のシグニを１枚捨てる]@：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番上に戻す。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Space Debris, Natural Star");
        setDescription("en",
                "@E @[Discard 1 <<Space>> SIGNI from your hand]@: Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, add it to your hand, and put the rest on the top of your deck in any order." +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "罗星 太空垃圾");
        setDescription("zh_simplified", 
                "@E 从手牌把<<宇宙>>精灵1张舍弃:从你的牌组上面看3张牌。从中把精灵1张公开加入手牌，剩下的任意顺序返回牌组最上面。" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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

            registerEnterAbility(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.SPACE)), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);

            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }

        private void onLifeBurstEff()
        {
            look(3);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromLooked()).get();
            if(reveal(cardIndex))
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(cardIndex);
                } else {
                    putOnField(cardIndex);
                }
            }

            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
