package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_K2_HanareForbiddenFang extends Card {

    public LRIGA_K2_HanareForbiddenFang()
    {
        setImageSets("WXDi-P15-046");

        setOriginalName("ハナレ//フォービドゥンファング");
        setAltNames("ハナレフォービドゥンファング Hanare Foobidoun Fangu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－12000する。\n" +
                "@E @[手札を３枚捨てる]@：あなたのトラッシュからシグニ１枚を対象とし、それを能力を持たないシグニとして場に出す。ターン終了時、それを場からトラッシュに置く。"
        );

        setName("en", "Hanare//Forbidden Fangs");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --12000 power until end of turn.\n@E @[Discard three cards]@: Put target SIGNI from your trash onto your field as SIGNI with no abilities. At end of turn, put it on your field into its owner's trash.\n"
        );
        
        setName("en_fan", "Hanare//Forbidden Fang");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --12000 power.\n" +
                "@E @[Discard 3 cards from your hand]@: Target 1 SIGNI from your trash, and put it onto the field as a SIGNI with no abilities. At the end of the turn, put it into the trash."
        );

		setName("zh_simplified", "离//禁忌尖牙");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-12000。\n" +
                "@E 手牌3张舍弃:从你的废弃区把精灵1张作为对象，将其作为不持有能力的精灵出场。回合结束时，将其从场上放置到废弃区。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.HANARE);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(1));
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
            registerEnterAbility(new DiscardCost(3), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -12000, ChronoDuration.turnEnd());
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
            
            if(putOnField(target, Enter.NO_ABILITIES))
            {
                int instanceId = target.getIndexedInstance().getInstanceId();
                callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                    if(target.getIndexedInstance() != null && target.getIndexedInstance().getInstanceId() == instanceId) trash(target);
                });
            }
        }
    }
}
