package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class SIGNI_R2_RingOfFireDiverMediumTrap extends Card {

    public SIGNI_R2_RingOfFireDiverMediumTrap()
    {
        setImageSets("WX24-P3-069");

        setOriginalName("中罠　ヒノワクグリ");
        setAltNames("チュウビンヒノワクグリ Chuubin Hi no Wakuguri");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニと同じシグニゾーンにある【マジックボックス】１つを表向きにしトラッシュに置いてもよい。その後、そのカードが##を持つ場合、対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。##を持たない場合、このアタックを無効にし、あなたのルリグ１体を対象とし、ターン終了時まで、それは@>@C：対戦相手は追加で%X %X %Xを支払わないかぎり【ガード】ができない。@@を得る。"
        );

        setName("en", "Ring of Fire Diver, Medium Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may put 1 [[Magic Box]] in the same SIGNI zone as this SIGNI face up into the trash. Then, if that card has a ## @[Life Burst]@, target 1 of your opponent's SIGNI with power 8000 or less, and banish it. If it doesn't have a ## @[Life Burst]@, disable that attack, target your LRIG, and until end of turn, it gains:" +
                "@>@C: Your opponent can't [[Guard]] unless they pay %X %X %X."
        );

		setName("zh_simplified", "中罠 跳火圈");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，可以把与这只精灵相同精灵区的[[魔术箱]]1个表向并放置到废弃区。然后，那张牌持有##的场合，对战对手的力量8000以下的精灵1只作为对象，将其破坏。不持有##的场合，这次攻击无效，你的分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C 对战对手如果不追加把%X %X %X:支付，那么不能[[防御]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(2);
        setPower(5000);

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
        }
        
        private void onAutoEff()
        {
            DataTable<CardIndex> data = new TargetFilter().own().withUnderType(CardUnderType.ZONE_MAGIC_BOX).fromSafeLocation(getCardIndex().getLocation()).getExportedData();
            if(!data.isEmpty() && playerChoiceActivate() && trash(data) > 0)
            {
                data.get().getIndexedInstance().findLifeBurstAbility().ifPresentOrElse(ability -> {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                    banish(target);
                }, () -> {
                    disableNextAttack(getCardIndex());
                    
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().LRIG()).get();
                    if(target != null)
                    {
                        ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD, TargetFilter.HINT_OWNER_OP,
                            dataRC -> new EnerCost(Cost.colorless(3)))
                        );
                        attachAbility(target, attachedConst, ChronoDuration.turnEnd());
                    }
                });
            }
        }
    }
}
