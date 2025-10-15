package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_W2_HanakoUrawaSwimsuit extends Card {

    public LRIGA_W2_HanakoUrawaSwimsuit()
    {
        setImageSets("WXDi-CP02-027");

        setOriginalName("浦和ハナコ(水着)");
        setAltNames("ウラワハナコミズギ Urawa Hanako Mizugi");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@E %W %X %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。" +
                "~{{E：あなたのデッキの上からカードを７枚見る。その中から＜ブルアカ＞のカード１枚を公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );

        setName("en", "Urawa Hanako (Swimsuit)");
        setDescription("en",
                "@E: Return target SIGNI on your opponent's field to its owner's hand.\n@E %W %X %X: Target SIGNI on your opponent's field loses its abilities until end of turn.~{{E: Look at the top seven cards of your deck. Reveal a <<Blue Archive>> card from among them and add it to your hand. Put the rest on the bottom of your deck in a random order."
        );
        
        setName("en_fan", "Hanako Urawa (Swimsuit)");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and return it to their hand.\n" +
                "@E %W %X %X: Target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities." +
                "~{{E: Look at the top 7 cards of your deck. Reveal 1 <<Blue Archive>> card from among them, add it to your hand, and shuffle the rest and put them on the bottom of your deck."
        );

		setName("zh_simplified", "浦和花子(泳装)");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "@E %W%X %X:对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。\n" +
                "~{{E:从你的牌组上面看7张牌。从中把<<ブルアカ>>牌1张公开加入手牌，剩下的洗切放置到牌组最下面。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAKEUP_WORK_CLUB);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(2));
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(2)), this::onEnterEff2);

            EnterAbility enter3 = registerEnterAbility(this::onEnterEff3);
            enter3.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(target);
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
            disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }

        private void onEnterEff3()
        {
            look(7);

            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);

            int countReturned = returnToDeck(getCardsInLooked(getOwner()), DeckPosition.BOTTOM);
            shuffleDeck(countReturned, DeckPosition.BOTTOM);
        }
    }
}

