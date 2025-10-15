package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_W2_HifumiAjitaniSwimsuit extends Card {

    public LRIGA_W2_HifumiAjitaniSwimsuit()
    {
        setImageSets("WXDi-CP02-029");
        setLinkedImageSets("WXDi-CP02-TK03B");

        setOriginalName("阿慈谷ヒフミ(水着)");
        setAltNames("アジタニヒフミミズギ Ajitani Hifumi Mizugi");
        setDescription("jp",
                "@E %X %X %X：クラフトの《クルセイダーちゃん》１つを場に出す。" +
                "~{{E：あなたのデッキの上からカードを７枚見る。その中から＜ブルアカ＞のカード１枚を公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );

        setName("en", "Ajitani Hifumi (Swimsuit)");
        setDescription("en",
                "@E %X %X %X: Put a \"Crusadie\" Craft onto your field.\n~{{E: Look at the top seven cards of your deck. Reveal a <<Blue Archive>> card from among them and add it to your hand. Put the rest on the bottom of your deck in a random order."
        );
        
        setName("en_fan", "Hifumi Ajitani (Swimsuit)");
        setDescription("en_fan",
                "@E %X %X %X: Put 1 \"Crusader-chan\" craft onto the field." +
                "~{{E: Look at the top 7 cards of your deck. Reveal 1 <<Blue Archive>> card from among them, add it to your hand, and shuffle the rest and put them on the bottom of your deck."
        );

		setName("zh_simplified", "阿慈谷日富美(泳装)");
        setDescription("zh_simplified", 
                "@E %X %X %X:衍生的《クルセイダーちゃん》1只出场。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n" +
                "~{{E:从你的牌组上面看7张牌。从中把<<ブルアカ>>牌1张公开加入手牌，剩下的洗切放置到牌组最下面。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAKEUP_WORK_CLUB);
        setColor(CardColor.WHITE);
        setLevel(2);
        setLimit(+1);
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

            registerEnterAbility(new EnerCost(Cost.colorless(3)), this::onEnterEff1);

            EnterAbility enter3 = registerEnterAbility(this::onEnterEff2);
            enter3.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            if(new TargetFilter().own().SIGNI().zone().playable().getValidTargetsCount() > 0)
            {
                CardIndex cardIndex = craft(getLinkedImageSets().get(0));

                if(!putOnField(cardIndex))
                {
                    exclude(cardIndex);
                }
            }
        }

        private void onEnterEff2()
        {
            look(7);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);

            int countReturned = returnToDeck(getCardsInLooked(getOwner()), DeckPosition.BOTTOM);
            shuffleDeck(countReturned, DeckPosition.BOTTOM);
        }
    }
}

