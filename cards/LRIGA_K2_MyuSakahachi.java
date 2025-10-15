package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_MyuSakahachi extends Card {

    public LRIGA_K2_MyuSakahachi()
    {
        setImageSets("WXDi-P13-042");

        setOriginalName("ミュウ　－　サカハチ");
        setAltNames("ミュウサカハチ Myuu Sakahachi");
        setDescription("jp",
                "@E：あなたのトラッシュからシグニ１枚を対象とし、それを能力を持たないシグニとして場に出す。ターン終了時、それを場からトラッシュに置く。\n" +
                "@E %K %X %X %X %X：あなたのトラッシュからシグニを２枚まで対象とし、それらを能力を持たないシグニとして場に出す。ターン終了時、それらを場からトラッシュに置く。"
        );

        setName("en", "Myu - Large Map");
        setDescription("en",
                "@E: Put target SIGNI from your trash onto your field as SIGNI with no abilities. At end of turn, put it on your field into its owner's trash.\n@E %K %X %X %X %X: Put up to two target SIGNI from your trash onto your field as SIGNI with no abilities. At end of turn, put them on your field into their owner's trash."
        );
        
        setName("en_fan", "Myu - Sakahachi");
        setDescription("en_fan",
                "@E: Target 1 SIGNI from your trash, and put it on the field as a SIGNI with no abilities. At the end of the turn, put it into the trash.\n" +
                "@E %K %X %X %X %X: Target up to 2 SIGNI from your trash, and put them onto the field as SIGNI with no abilities. At the end of the turn, put them into the trash."
        );

		setName("zh_simplified", "缪-布网蜘蛱蝶");
        setDescription("zh_simplified", 
                "@E :从你的废弃区把精灵1张作为对象，将其作为不持有能力的精灵出场。回合结束时，将其从场上放置到废弃区。\n" +
                "@E %K%X %X %X %X:从你的废弃区把精灵2张最多作为对象，将这些作为不持有能力的精灵出场。回合结束时，将这些从场上放置到废弃区。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MYU);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(4)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().playable().fromTrash()).get();
            
            if(putOnField(target, Enter.NO_ABILITIES))
            {
                int instanceId = target.getIndexedInstance().getInstanceId();
                callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                    if(target.isSIGNIOnField() && target.getIndexedInstance().getInstanceId() == instanceId) trash(target);
                });
            }
        }
        private void onEnterEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().playable().fromTrash());
            
            if(putOnField(data, Enter.NO_ABILITIES) > 0)
            {
                callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                    DataTable<CardIndex> data2 = new TargetFilter().own().SIGNI().match(data).getExportedData();
                    trash(data2);
                });
            }
        }
    }
}
