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
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIGA_W2_HifumiAjitaniHelpMePeroro extends Card {

    public LRIGA_W2_HifumiAjitaniHelpMePeroro()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WXDi-CP02-028");
        setLinkedImageSets("WXDi-CP02-TK01A");

        setOriginalName("阿慈谷ヒフミ[助けて、ペロロ様！]");
        setAltNames("アジタニヒフミタスケテペロロサマ Ajitani Hifumi Tasukete Peroro-san");
        setDescription("jp",
                "@E：クラフトの《ペロロ人形》１つを場に出す。" +
                "~{{E：あなたのデッキの上からカードを７枚見る。その中から＜ブルアカ＞のカード１枚を公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );

        setName("en", "Ajitani Hifumi [Help Me, Peroro!]");
        setDescription("en",
                "@E: Put a \"Peroro plush\" Craft onto your field.~{{E: Look at the top seven cards of your deck. Reveal a <<Blue Archive>> card from among them and add it to your hand. Put the rest on the bottom of your deck in a random order."
        );
        
        setName("en_fan", "Hifumi Ajitani [Help Me, Peroro!]");
        setDescription("en_fan",
                "@E: Put 1 \"Peroro Doll\" craft onto the field." +
                "~{{E: Look at the top 7 cards of your deck. Reveal 1 <<Blue Archive>> card from among them, add it to your hand, and shuffle the rest and put them on the bottom of your deck."
        );

		setName("zh_simplified", "阿慈谷日富美[佩洛洛，帮帮我！]");
        setDescription("zh_simplified", 
                "@E :衍生的《ペロロ人形》1只出场。\n" +
                "~{{E:从你的牌组上面看7张牌。从中把<<ブルアカ>>牌1张公开加入手牌，剩下的洗切放置到牌组最下面。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAKEUP_WORK_CLUB);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);

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

