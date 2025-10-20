package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.actions.ActionEnerPay.PaidEnerData;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W2_WhiteFacePaintHolyBeauty extends Card {

    public SIGNI_W2_WhiteFacePaintHolyBeauty()
    {
        setImageSets("SPDi01-128");

        setOriginalName("聖美　シロヌリ");
        setAltNames("セイビシロヌリ Seibi Shironui");
        setDescription("jp",
                "@E：対戦相手のレベル２以下のシグニ１体を対象とし、ターン終了時まで、それを白にする。\n" +
                "@A %X #D：このコストでトラッシュに置いたカードと共通する色を持つ対戦相手のパワー5000以下のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "White Face Paint, Holy Beauty");
        setDescription("en",
                "@E: Target 1 of your opponent's level 2 or lower SIGNI, and until end of turn, it becomes white.\n" +
                "@A %X #D: Target 1 of your opponent's SIGNI with power 5000 or less that shares a common color with the card that was put into the trash for this ability's cost, and return it to their hand."
        );

		setName("zh_simplified", "圣美 白涂");
        setDescription("zh_simplified", 
                "@E :对战对手的等级2以下的精灵1只作为对象，直到回合结束时为止，其变为白色。\n" +
                "@A %X横置:持有与这个费用放置到废弃区的牌共通颜色的对战对手的力量5000以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
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

            registerEnterAbility(this::onEnterEff);
            
            registerActionAbility(new AbilityCostList(new EnerCost(Cost.colorless(1)), new DownCost()), this::onActionEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI()).get();
            if(target != null) gainValue(target, target.getIndexedInstance().getColor(),CardColor.WHITE, ChronoDuration.turnEnd());
        }
        
        private void onActionEff()
        {
            CardDataColor color = ((PaidEnerData)getAbility().getCostPaidData().get()).cardIndexSnapshot().getColor();
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,5000).withColor(color)).get();
            addToHand(target);
        }
    }
}
