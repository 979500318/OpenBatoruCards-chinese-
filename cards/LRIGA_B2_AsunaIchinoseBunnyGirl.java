package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_AsunaIchinoseBunnyGirl extends Card {

    public LRIGA_B2_AsunaIchinoseBunnyGirl()
    {
        setImageSets("WXDi-CP02-034");

        setOriginalName("一之瀬アスナ(バニーガール)");
        setAltNames("イチノセアスナバニーガール Ichinose Asuna Banii Gaaru");
        setDescription("jp",
                "@E：対戦相手の手札を２枚見ないで選び、捨てさせる。\n" +
                "@E %X：ターン終了時まで、対戦相手のすべてのシグニのパワーを－3000する。" +
                "~{{E @[手札から＜ブルアカ＞のカードを１枚捨てる]@：あなたのデッキの上からカードを７枚見る。その中から＜ブルアカ＞のカード１枚を公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );

        setName("en", "Ichinose Asuna (Bunny)");
        setDescription("en",
                "@E: Your opponent discards two cards at random.\n@E %X: All SIGNI on your opponent's field get --3000 power until end of turn.\n~{{E @[Discard a <<Blue Archive>> card]@: Draw two cards."
        );
        
        setName("en_fan", "Asuna Ichinose (Bunny Girl)");
        setDescription("en_fan",
                "@E: Choose 2 cards from your opponent's hand without looking, and your opponent discards them.\n" +
                "@E %X: Until end of turn, all of your opponent's SIGNI get --3000 power." +
                "~{{E @[Discard 1 <<Blue Archive>> card from your hand]@: Draw 2 cards."
        );

		setName("zh_simplified", "一之濑明日奈(兔女郎)");
        setDescription("zh_simplified", 
                "@E :不看对战对手的手牌选2张，舍弃。\n" +
                "@E %X:直到回合结束时为止，对战对手的全部的精灵的力量-3000。\n" +
                "（@E能力和~{{E能力的:的左侧是费用。你能选择不把费用支付，而不发动）@@\n" +
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff2);

            EnterAbility enter3 = registerEnterAbility(new DiscardCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff3);
            enter3.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerChoiceHand(2);
            discard(data);
        }

        private void onEnterEff2()
        {
            gainPower(getSIGNIOnField(getOpponent()), -3000, ChronoDuration.turnEnd());
        }

        private void onEnterEff3()
        {
            draw(2);
        }
    }
}

