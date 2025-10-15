package open.batoru.data.cards;

import open.batoru.core.Deck;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_W2_DonaGlittering extends Card {

    public LRIGA_W2_DonaGlittering()
    {
        setImageSets("WXDi-P09-030");

        setOriginalName("ドーナ『輝いてる！』");
        setAltNames("ドーナ");
        setDescription("jp",
                "@E：あなたの手札からシグニ１枚を場に出す。そのシグニの@E能力は発動しない。\n" +
                "@E %W %X：あなたの手札からシグニ１枚を場に出す。そのシグニの@E能力は発動しない。\n" +
                "@E %X %X %X：あなたのデッキの上からカード５枚を見る。その中からシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Dona \"Glittering!\"");
        setDescription("en",
                "@E: Put a SIGNI from your hand onto your field. The @E abilities of SIGNI put onto your field this way do not activate.\n" +
                "@E %W %X: Put a SIGNI from your hand onto your field. The @E abilities of SIGNI put onto your field this way do not activate.\n" +
                "@E %X %X %X: Look at the top five cards of your deck. Reveal up to two SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Dona \"Glittering!\"");
        setDescription("en_fan",
                "@E: Put 1 SIGNI from your hand onto the field. Its @E abilities don't activate.\n" +
                "@E %W %X: Put 1 SIGNI from your hand onto the field. Its @E abilities don't activate.\n" +
                "@E %X %X %X: Look at the top 5 cards of your deck. Reveal up to 2 SIGNI from among them, and add them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "多娜『闪亮！』");
        setDescription("zh_simplified", 
                "@E 从你的手牌把精灵1张出场。那只精灵的@E能力不能发动。\n" +
                "@E %W%X从你的手牌把精灵1张出场。那只精灵的@E能力不能发动。\n" +
                "@E %X %X %X:从你的牌组上面看5张牌。从中把精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.DONA);
        setColor(CardColor.WHITE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)), this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(3)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromHand().playable()).get();
            putOnField(target, AbilityConst.Enter.DONT_ACTIVATE);
        }

        private void onEnterEff2()
        {
            look(5);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked());
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, Deck.DeckPosition.BOTTOM);
            }
        }
    }
}
