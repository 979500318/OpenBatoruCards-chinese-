package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_R2_CarnivalEnd extends Card {

    public LRIGA_R2_CarnivalEnd()
    {
        setImageSets("WXDi-P11-031");
        setLinkedImageSets("WXDi-P07-TK01-A");

        setOriginalName("カーニバル　－終－");
        setAltNames("カーニバルツイ Kaanibaru Tsui");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それを《サーバント　ＺＥＲＯ》にする。\n" +
                "@E @[手札を２枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それを《サーバント　ＺＥＲＯ》にする。\n" +
                "@E %R %X %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それを《サーバント　ＺＥＲＯ》にする。"
        );

        setName("en", "Carnival -Finality-");
        setDescription("en",
                "@E: Make target SIGNI on your opponent's field \"Servant ZERO\" until end of turn.\n" +
                "@E @[Discard two cards]@: Make target SIGNI on your opponent's field \"Servant ZERO\" until end of turn.\n" +
                "@E %R %X %X: Make target SIGNI on your opponent's field \"Servant ZERO\" until end of turn."
        );
        
        setName("en_fan", "Carnival -End-");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it becomes \"Servant ZERO\".\n" +
                "@E @[Discard 2 cards from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it becomes \"Servant ZERO\".\n" +
                "@E %R %X %X: Target 1 of your opponent's SIGNI, and until end of turn, it becomes \"Servant ZERO\"."
        );

		setName("zh_simplified", "嘉年华 -终-");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其变为《サーバント　ＺＥＲＯ》。\n" +
                "@E 手牌2张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其变为《サーバント　ＺＥＲＯ》。\n" +
                "@E %R%X %X:对战对手的精灵1只作为对象，直到回合结束时为止，其变为《サーバント　ＺＥＲＯ》。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.CARNIVAL);
        setColor(CardColor.RED);
        setCost(Cost.colorless(1));
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

            registerEnterAbility(this::onEnterEff);
            registerEnterAbility(new DiscardCost(2), this::onEnterEff);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(2)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRANSFORM).OP().SIGNI()).get();
            transform(target, getLinkedImageSets().get(0), ChronoDuration.turnEnd());
        }
    }
}
