package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K1_AkariWanibuchiNewYears extends Card {

    public SIGNI_K1_AkariWanibuchiNewYears()
    {
        setImageSets("WX25-CP1-083");
        setLinkedImageSets("WX25-CP1-TK2A");

        setOriginalName("鰐渕アカリ(正月)");
        setAltNames("ワニブチアカリショウガツ Wnibuchi Akari Shougatsu");
        setDescription("jp",
                "@A @[手札から＜ブルアカ＞のカードを１枚捨てる]@：このシグニの下に《給食推進車両》がない場合、クラフトの《給食推進車両》１つをこのシグニの下に置く。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#：対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );

        setName("en", "Wanibuchi Akari (New Year's)");

        setName("en_fan", "Akari Wanibuchi (New Year's)");
        setDescription("en",
                "@A @[Discard 1 <<Blue Archive>> card from your hand]@: If there is no \"School Lunch Promotion Vehicle\" under this SIGNI, put 1 \"School Lunch Promotion Vehicle\" craft under this SIGNI." +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#Target 1 of your opponent's SIGNI, and you may pay %X. If you do, until end of turn, it gets --12000 power."
        );

		setName("zh_simplified", "鳄渊明里(正月)");
        setDescription("zh_simplified", 
                "@A 从手牌把<<ブルアカ>>牌1张舍弃:这只精灵的下面没有《给食推进车辆》的场合，衍生的《给食推进车辆》1只放置到这只精灵的下面。\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#对战对手的精灵1只作为对象，可以支付%X。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act = registerActionAbility(new DiscardCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onActionEff);
            act.setCondition(this::onActionEffCond);

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onActionEffCond()
        {
            return new TargetFilter().own().SIGNI().under(getCardIndex()).withName("給食推進車両").getValidTargetsCount() == 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            if(new TargetFilter().own().SIGNI().under(getCardIndex()).withName("給食推進車両").getValidTargetsCount() == 0)
            {
                CardIndex cardIndex = craft(getLinkedImageSets().get(0));
                
                if(!attach(getCardIndex(), cardIndex, CardUnderType.UNDER_GENERIC))
                {
                    exclude(cardIndex);
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
