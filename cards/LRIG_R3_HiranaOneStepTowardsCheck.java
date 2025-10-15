package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.cost.TrashCost;

public final class LRIG_R3_HiranaOneStepTowardsCheck extends Card {

    public LRIG_R3_HiranaOneStepTowardsCheck()
    {
        setImageSets("WXDi-P13-007", "WXDi-P13-007U");

        setOriginalName("王手の一歩　ヒラナ");
        setAltNames("オウテノイッポヒラナ Oute no Ippo Hirana");
        setDescription("jp",
                "@E：カードを１枚引き【エナチャージ１】をする。\n" +
                "@A $T1 @[エナゾーンから#Sのカード２枚をトラッシュに置く]@：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A @[エクシード４]@：手札をすべて捨てる。この方法で捨てた#Sのカード１枚につき対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。このターン、次にこのルリグがアタックしたとき、そのアタックの間、対戦相手は【ガード】ができない。"
        );

        setName("en", "Hirana, a Step Towards the Check");
        setDescription("en",
                "@E: Draw a card and [[Ener Charge 1]].\n@A $T1 @[Put two #S SIGNI from your Ener Zone into your trash]@: Vanish target SIGNI on your opponent's field with power 10000 or less.\n@A @[Exceed 4]@: Discard your hand. Your opponent chooses a card from their Ener Zone and puts it into their trash for each #S cards you discarded this way. When this LRIG attacks next this turn, your opponent cannot [[Guard]] during that attack."
        );
        
        setName("en_fan", "Hirana, One Step Towards Check");
        setDescription("en_fan",
                "@E: Draw 1 card and [[Ener Charge 1]].\n" +
                "@A $T1 @[Put 2 #S @[Dissona]@ cards from your ener zone into the trash]@: Target 1 of your opponent's SIGNI with power 10000 or less, and banish it.\n" +
                "@A @[Exceed 4]@: Discard all cards from your hand. For each #S @[Dissona]@ card discarded this way, your opponent chooses 1 card from their ener zone, and puts it into the trash. This turn, the next time this LRIG attacks, during that attack, your opponent can't [[Guard]]."
        );

		setName("zh_simplified", "王手的一步 平和");
        setDescription("zh_simplified", 
                "@E :抽1张牌并[[能量填充1]]。\n" +
                "@A $T1 从能量区把#S的牌2张放置到废弃区:对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n" +
                "@A @[超越 4]@手牌全部舍弃。依据这个方法舍弃的#S的牌的数量，每有1张对战对手就从自己的能量区选1张牌放置到废弃区。这个回合，当下一次这只分身攻击时，那次攻击期间，对战对手不能[[防御]]。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setLRIGType(CardLRIGType.HIRANA);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);

            ActionAbility act1 = registerActionAbility(new TrashCost(2, new TargetFilter().dissona().fromEner()), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            registerActionAbility(new ExceedCost(4), this::onActionEff2);
        }

        private void onEnterEff()
        {
            draw(1);
            enerCharge(1);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }

        private void onActionEff2()
        {
            DataTable<CardIndex> dataDiscarded = discard(getCardsInHand(getOwner()));
            if(dataDiscarded.get() != null)
            {
                int count = (int)dataDiscarded.stream().filter(c -> c.getIndexedInstance().isState(CardStateFlag.IS_DISSONA)).count();
                
                DataTable<CardIndex> data = playerTargetCard(getOpponent(), Math.min(count, getEnerCount(getOpponent())), new TargetFilter(TargetHint.BURN).own().fromEner());
                trash(data);
            }
            
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_GUARD, getOpponent(), record, data -> {
                if(data.getSourceCardIndex() != getCardIndex()) return RuleCheckState.IGNORE;
                
                record.forceExpire();
                
                return RuleCheckState.BLOCK;
            });
        }
    }
}

