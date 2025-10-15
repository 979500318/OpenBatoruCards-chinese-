package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_NonomiIzayoiSwimsuit extends Card {

    public LRIGA_G2_NonomiIzayoiSwimsuit()
    {
        setImageSets("WXDi-CP02-043");

        setOriginalName("十六夜ノノミ(水着)");
        setAltNames("イザヨイノノミミズギ Izayoi Nonomi Mizugi");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %G %X %X：対戦相手のシグニ１体を対象とし、このターン終了時、それをバニッシュする。" +
                "~{{E：【エナチャージ１】をする。その後、あなたのエナゾーンから＜ブルアカ＞のカードを１枚まで対象とし、それを手札に加える。"
        );

        setName("en", "Izayoi Nonomi (Swimsuit)");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field.\n@E %G %X %X: Choose target SIGNI on your opponent's field. At the end of this turn, vanish it.\n~{{E: [[Ener Charge 1]]. Then, add up to one target <<Blue Archive>> card from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Nonomi Izayoi (Swimsuit)");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@E %G %X %X: Target 1 of your opponent's SIGNI, and at the end of this turn, banish it." +
                "~{{E: [[Ener Charge 1]]. Then, target up to 1 <<Blue Archive>> card from your ener zone, and add it to your hand."
        );

		setName("zh_simplified", "十六夜野宫(泳装)");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E %G%X %X:对战对手的精灵1只作为对象，这个回合结束时，将其破坏。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n" +
                "~{{E:[[能量填充1]]。然后，从你的能量区把<<ブルアカ>>牌1张最多作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.FORECLOSURE_TASK_FORCE);
        setColor(CardColor.GREEN);
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(2)), this::onEnterEff2);

            EnterAbility enter3 = registerEnterAbility(this::onEnterEff3);
            enter3.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null)
            {
                int instanceId = target.getIndexedInstance().getInstanceId();
                callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                    if(target.isSIGNIOnField() && target.getIndexedInstance().getInstanceId() == instanceId) banish(target);
                });
            }
        }

        private void onEnterEff3()
        {
            enerCharge(1);

            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()).get();
            addToHand(target);
        }
    }
}

