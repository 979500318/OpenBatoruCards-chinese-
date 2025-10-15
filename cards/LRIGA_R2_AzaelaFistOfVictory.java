package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_R2_AzaelaFistOfVictory extends Card {

    public LRIGA_R2_AzaelaFistOfVictory()
    {
        setImageSets("WXDi-P16-040");

        setOriginalName("アザエラ「勝利の拳」");
        setAltNames("アザエラショウリノコブシ Azaera Shouri no Kobushi");
        setDescription("jp",
                "@E：対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %R %X %X %X %X：対戦相手のライフクロス１枚をクラッシュする。"
        );

        setName("en", "Azaela \"Fists of Victory\"");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 12000 or less.\n@E %R %X %X %X %X: Crush one of your opponent's Life Cloth.\n"
        );
        
        setName("en_fan", "Azaela [Fist of Victory]");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI power 12000 or less, and banish it.\n" +
                "@E %R %X %X %X %X: Crush 1 of your opponent's life cloth."
        );

		setName("zh_simplified", "阿左伊来「胜利的拳」");
        setDescription("zh_simplified", 
                "@E :对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n" +
                "@E %R%X %X %X %X:对战对手的生命护甲1张击溃。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AZAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.RED);
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(4)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
        private void onEnterEff2()
        {
            crush(getOpponent());
        }
    }
}
