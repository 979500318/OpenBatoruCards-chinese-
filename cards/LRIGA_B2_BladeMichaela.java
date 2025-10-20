package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_B2_BladeMichaela extends Card {

    public LRIGA_B2_BladeMichaela()
    {
        setImageSets("WXDi-P16-046");

        setOriginalName("刃・ミカエラ");
        setAltNames("ヤイバミカエラ Yaiba Mikaera");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "@E @[手札を２枚捨てる]@：あなたのライフクロスの枚数が対戦相手より少ない場合、対戦相手のシグニ１体を対象とし、それをダウンする。"
        );

        setName("en", "Michaela, Blade");
        setDescription("en",
                "@E: Down up to two target SIGNI on your opponent's field.\n@E @[Discard two cards]@: If you have fewer Life Cloth than your opponent, down target SIGNI on your opponent's field.\n"
        );
        
        setName("en_fan", "Blade Michaela");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "@E @[Discard 2 cards from your hand]@: If your life cloth is less than your opponent, target 1 of your opponent's SIGNI, and down it."
        );

		setName("zh_simplified", "刃·米卡伊来");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵2只最多作为对象，将这些横置。\n" +
                "@E 手牌2张舍弃:你的生命护甲的张数比对战对手少的场合，对战对手的精灵1只作为对象，将其横置。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MICHAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(4));
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
            registerEnterAbility(new DiscardCost(2), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
        private void onEnterEff2()
        {
            if(getLifeClothCount(getOwner()) < getLifeClothCount(getOpponent()))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
                down(target);
            }
        }
    }
}
