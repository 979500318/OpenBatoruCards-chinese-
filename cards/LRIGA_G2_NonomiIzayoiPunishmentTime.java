package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIGA_G2_NonomiIzayoiPunishmentTime extends Card {

    public LRIGA_G2_NonomiIzayoiPunishmentTime()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WXDi-CP02-042");

        setOriginalName("十六夜ノノミ[お仕置きの時間です～♣]");
        setAltNames("イザヨイノノミオシオキノジカンデスー Izayoi Nonomi Oshioki no Jikan desuu");
        setDescription("jp",
                "@E %X：対戦相手のレベル３以上のすべてのシグニをバニッシュする。" +
                "~{{E：【エナチャージ１】をする。その後、あなたのエナゾーンから＜ブルアカ＞のカードを１枚まで対象とし、それを手札に加える。"
        );

        setName("en", "Izayoi Nonomi [Punishment Time!]");
        setDescription("en",
                "@E %X: Vanish all level three or more SIGNI on your opponent's field.\n~{{E: [[Ener Charge 1]]. Then, add up to one target <<Blue Archive>> card from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Nonomi Izayoi [Punishment Time♣]");
        setDescription("en_fan",
                "@E %X: Banish all of your opponent's level 3 or higher SIGNI." +
                "~{{E: [[Ener Charge 1]]. Then, target up to 1 <<Blue Archive>> card from your ener zone, and add it to your hand."
        );

		setName("zh_simplified", "十六夜野宫[惩罚时间到了～♣]");
        setDescription("zh_simplified", 
                "@E %X:对战对手的等级3以上的全部的精灵破坏。\n" +
                "~{{E:[[能量填充1]]。然后，从你的能量区把<<ブルアカ>>牌1张最多作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.FORECLOSURE_TASK_FORCE);
        setColor(CardColor.GREEN);
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

            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff1);

            EnterAbility enter2 = registerEnterAbility(this::onEnterEff2);
            enter2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            banish(new TargetFilter().OP().SIGNI().withLevel(3,0).getExportedData());
        }

        private void onEnterEff2()
        {
            enerCharge(1);

            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()).get();
            addToHand(target);
        }
    }
}

