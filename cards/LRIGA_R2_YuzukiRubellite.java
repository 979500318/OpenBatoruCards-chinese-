package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.CrushCost;

public final class LRIGA_R2_YuzukiRubellite extends Card {

    public LRIGA_R2_YuzukiRubellite()
    {
        setImageSets("WXDi-P10-027");

        setOriginalName("遊月・ルベライト");
        setAltNames("ユヅキルベライト Yuzuki Ruberaito");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E @[ライフクロス２枚をクラッシュする]@：対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Yuzuki Rubellite");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field.\n" +
                "@E @[Crush two of your Life Cloth]@: Vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Yuzuki Rubellite");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@E @[Crush 2 of your life cloth]@: Target 1 of your opponent's SIGNI, and banish it."
        );

		setName("zh_simplified", "游月·红碧玺");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E 生命护甲2张击溃:对战对手的精灵1只作为对象，将其破坏。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.YUZUKI);
        setColor(CardColor.RED);
        setCost(Cost.colorless(2));
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
            registerEnterAbility(new CrushCost(2), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
    }
}
