package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_R3_HanayoThreeProfuseBloomOfFlourishingFlowers extends Card {

    public LRIG_R3_HanayoThreeProfuseBloomOfFlourishingFlowers()
    {
        setImageSets("WX24-P1-012", "WX24-P1-012U");

        setOriginalName("閃花繚乱　花代・参");
        setAltNames("センカリョウランハナヨサン Senkaryouran Hanayo San");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、あなたの場に＜宝石＞のシグニがある場合、%R %Xを支払ってもよい。そうした場合、そのアタックの間、対戦相手は【ガード】ができない。\n" +
                "@A $G1 @[@|アンビション|@]@ %R0：カードを４枚引く。次のあなたのアタックフェイズ開始時、手札をすべて捨て、この方法で捨てたカード１枚につき【エナチャージ１】をする。"
        );

        setName("en", "Hanayo Three, Profuse Bloom of Flourishing Flowers");
        setDescription("en",
                "@U: Whenever this LRIG attacks, if there is a <<Gem>> SIGNI on your field, you may pay %R %X. If you do, your opponent can't [[Guard]] during that attack.\n" +
                "@A @[@|Ambition|@]@ $G1 %R0: Draw 4 cards. At the beginning of your next attack phase, discard all cards in your hand, then [[Ener Charge 1]] for each card discarded this way."
        );

		setName("zh_simplified", "闪花缭乱 花代·叁");
        setDescription("zh_simplified", 
                "@U :当这只分身攻击时，你的场上有<<宝石>>精灵的场合，可以支付%R%X。这样做的场合，那次攻击期间，对战对手不能[[防御]]。\n" +
                "@A $G1 热望%R0:抽4张牌。下一个你的攻击阶段开始时，手牌全部舍弃，依据这个方法舍弃的牌的数量，每有1张就[[能量填充1]]。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Ambition");
        }

        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.GEM).getValidTargetsCount() > 0 &&
               payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
            {
                ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
                addPlayerRuleCheck(PlayerRuleCheckType.CAN_GUARD, getOpponent(), record, data -> {
                    record.forceExpire();
                    return RuleCheckState.BLOCK;
                });
            }
        }

        private void onActionEff()
        {
            draw(4);
            
            callDelayedEffect(ChronoDuration.nextPhase(getOwner(), GamePhase.ATTACK_PRE), () -> {
                DataTable<CardIndex> dataDiscarded = discard(getCardsInHand(getOwner()));
                if(dataDiscarded.get() != null) enerCharge(dataDiscarded.size());
            });
        }
    }
}
