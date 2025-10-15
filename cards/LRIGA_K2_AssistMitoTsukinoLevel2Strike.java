package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_AssistMitoTsukinoLevel2Strike extends Card {

    public LRIGA_K2_AssistMitoTsukinoLevel2Strike()
    {
        setImageSets("WXDi-CP01-022");

        setOriginalName("【アシスト】月ノ美兎　レベル２【殴打】");
        setAltNames("アシストツキノミトレベルニオウダ Ashisuto Tsukino Mito Reberu Ni Ouda Assist Mito Assist Tsukino");
        setDescription("jp",
                "@E：対戦相手のレベル１のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %K %X %X %X %X %X：対戦相手のレベル３のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "[Assist] Mito, Level 2 [Strike]");
        setDescription("en",
                "@E: Vanish target level one SIGNI on your opponent's field.\n@E %K %X %X %X %X %X: Vanish target level three SIGNI on your opponent's field.\n"
        );
        
        setName("en_fan", "[Assist] Mito Tsukino Level 2 [Strike]");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 1 SIGNI, and banish it.\n" +
                "@E %K %X %X %X %X %X: Target 1 of your opponent's level 3 SIGNI, and banish it."
        );

		setName("zh_simplified", "【支援】月之美兔 等级2【殴打】");
        setDescription("zh_simplified", 
                "@E :对战对手的等级1的精灵1只作为对象，将其破坏。\n" +
                "@E %K%X %X %X %X %X:对战对手的等级3的精灵1只作为对象，将其破坏。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MITO);
        setColor(CardColor.BLACK);
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(5)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(1)).get();
            banish(target);
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(3)).get();
            banish(target);
        }
    }
}
