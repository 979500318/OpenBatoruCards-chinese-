package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_KarinKakudateBunnyGirl extends Card {

    public LRIGA_B2_KarinKakudateBunnyGirl()
    {
        setImageSets("WXDi-CP02-036");

        setOriginalName("角楯カリン(バニーガール)");
        setAltNames("カクダテカリンバニーガール Kakudate Karin Banii Gaaru");
        setDescription("jp",
                "@E %X %X %X %X %X %X：対戦相手のすべてのシグニをデッキの一番下に置く。" +
                "~{{E @[手札から＜ブルアカ＞のカードを１枚捨てる]@：あなたのデッキの上からカードを７枚見る。その中から＜ブルアカ＞のカード１枚を公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );

        setName("en", "Kakudate Karin (Bunny)");
        setDescription("en",
                "@E %X %X %X %X %X %X: Put all SIGNI on your opponent's field on the bottom of their owner's deck. ~{{E @[Discard a <<Blue Archive>> card]@: Draw two cards."
        );
        
        setName("en_fan", "Karin Kakudate (Bunny Girl)");
        setDescription("en_fan",
                "@E %X %X %X %X %X %X: Put all of your opponent's SIGNI on the bottom of their deck." +
                "~{{E @[Discard 1 <<Blue Archive>> card from your hand]@: Draw 2 cards."
        );

		setName("zh_simplified", "角楯花凛(兔女郎)");
        setDescription("zh_simplified", 
                "@E %X %X %X %X %X %X:对战对手的全部的精灵放置到牌组最下面。\n" +
                "~{{E从手牌把<<ブルアカ>>牌1张舍弃:抽2张牌。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.CC);
        setColor(CardColor.BLUE);
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

            registerEnterAbility(new EnerCost(Cost.colorless(6)), this::onEnterEff1);

            EnterAbility enter2 = registerEnterAbility(new DiscardCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff2);
            enter2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(getOpponent(), getSIGNICount(getOpponent()),getSIGNICount(getOpponent()), ChoiceLogic.DEFAULT, new TargetFilter(TargetHint.BOTTOM).own().SIGNI(), null, false);
            returnToDeck(data, DeckPosition.BOTTOM);
        }

        private void onEnterEff2()
        {
            draw(2);
        }
    }
}

