package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G2_LegKnotMediumTrap extends Card {

    public SIGNI_G2_LegKnotMediumTrap()
    {
        setImageSets("WX24-P3-085");

        setOriginalName("中罠　アシククリ");
        setAltNames("チュウビンアシククリ Chuubin Ashikukuri");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニと同じシグニゾーンにある【マジックボックス】１つを表向きにしトラッシュに置いてもよい。そのカードが##を持つ場合、ターン終了時まで、このシグニは[[ランサー（パワー5000以下のシグニ）]]を得る。##を持たない場合、このアタックを無効にし、【エナチャージ３】をする。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、《無》を支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Leg Knot, Medium Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may put 1 [[Magic Box]] in the same SIGNI zone as this SIGNI face up into the trash. Then, if that card has a ## @[Life Burst]@, until end of turn, this SIGNI gains [[Lancer (SIGNI with power 5000 or less)]]. If it doesn't have a ## @[Life Burst]@, disable that attack, and [[Ener Charge 3]]." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and you may pay %X. If you do, banish it."
        );

		setName("zh_simplified", "中罠 脚部套索");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，可以把与这只精灵相同精灵区的[[魔术箱]]1个表向并放置到废弃区。那张牌持有##的场合，直到回合结束时为止，这只精灵得到[[枪兵（力量5000以下的精灵）]]。不持有##的场合，这次攻击无效，[[能量填充3]]。" +
                "~#对战对手的力量7000以上的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(2);
        setPower(8000);

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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            DataTable<CardIndex> data = new TargetFilter().own().withUnderType(CardUnderType.ZONE_MAGIC_BOX).fromSafeLocation(getCardIndex().getLocation()).getExportedData();
            if(!data.isEmpty() && playerChoiceActivate() && trash(data) > 0)
            {
                data.get().getIndexedInstance().findLifeBurstAbility().ifPresentOrElse(ability -> {
                    attachAbility(getCardIndex(), new StockAbilityLancer(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
                }, () -> {
                    disableNextAttack(getCardIndex());
                    
                    enerCharge(3);
                });
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getPower().getValue() <= 5000 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
