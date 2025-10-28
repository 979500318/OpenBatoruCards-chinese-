package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class ARTS_W_BubblesBomber extends Card {

    public ARTS_W_BubblesBomber()
    {
        setImageSets("WX25-P1-001", "WX25-P1-001U");

        setOriginalName("バブルス・ボマー");
        setAltNames("バブルスボマー Baburusu Bomaa");
        setDescription("jp",
                "&E４枚以上@0あなたのセンタールリグ１体を対象とし、ターン終了時まで、それは以下の能力を得る。" +
                "@>@A @[エクシード１]@：あなたのデッキの上からカードを５枚見る。その中からカードを２枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。\n" +
                "@A @[エクシード１]@：対戦相手のパワー12000以下のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@A @[エクシード２]@：ターン終了時まで、対戦相手のすべてのシグニは能力を失う。その後、対戦相手のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Bubbles Bomber");
        setDescription("en",
                "&E4 or more@0 Target your center LRIG, and until end of turn, it gains:" +
                "@>@A @[Exceed 1]@: Look at the top 5 cards of your deck. Add up to 2 cards from among them to your hand, and put the rest on the bottom of your deck in any order.\n" +
                "@A @[Exceed 1]@: Target 1 of your opponent's SIGNI with power 12000 or less, and return it to their hand.\n" +
                "@A @[Exceed 2]@: Until end of turn, all of your opponent's SIGNI lose their abilities. Then, target 1 of your opponent's SIGNI, and put it into the trash."
        );

        setName("es", "Burbujas Bombarderas");
        setDescription("es",
                "@E4 o mas@0 Selecciona tu LRIG central y hasta el final del turno, esta gana:" +
                "@>@A @[Exceed 1]@: Mira 5 cartas del tope de tu mazo y añade hasta 2 cartas de entre ellas a tu mano, pon el resto en el fondo del mazo en cualquier orden.\n" +
                "@A @[Exceed 1]@: Selecciona 1 SIGNI oponente con 12000 o menos poder y devuelvela a la mano.\n" +
                "@A @[Exceed 2]@: Hasta el final del turno, todas las SIGNI oponente pierden sus habilidades. Entonces, selecciona 1 SIGNI oponente y ponla en la basura."
        );

        setName("zh_simplified", "泡泡·轰炸");
        setDescription("zh_simplified", 
                "&E4张以上@0你的核心分身1只作为对象，直到回合结束时为止，其得到以下的能力。" +
                "@>@A @[超越 1]@:从你的牌组上面看5张牌。从中把牌2张最多加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "@A @[超越 1]@:对战对手的力量12000以下的精灵1只作为对象，将其返回手牌。\n" +
                "@A @[超越 2]@:直到回合结束时为止，对战对手的全部的精灵的能力失去。然后，对战对手的精灵1只作为对象，将其放置到废弃区。@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
            arts.setRecollect(4);
        }

        private ConditionState onARTSEffCond()
        {
            return arts.isRecollectFulfilled() ? ConditionState.OK : ConditionState.WARN;
        }
        private void onARTSEff()
        {
            if(arts.isRecollectFulfilled())
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().LRIG()).get();

                ActionAbility attachedAct1 = new ActionAbility(new ExceedCost(1), this::onAttachedActionEff1);
                attachAbility(target, attachedAct1, ChronoDuration.turnEnd());

                ActionAbility attachedAct2 = new ActionAbility(new ExceedCost(1), this::onAttachedActionEff2);
                attachedAct2.setNestedDescriptionOffset(1);
                attachAbility(target, attachedAct2, ChronoDuration.turnEnd());

                ActionAbility attachedAct3 = new ActionAbility(new ExceedCost(2), this::onAttachedActionEff3);
                attachedAct3.setNestedDescriptionOffset(2);
                attachAbility(target, attachedAct3, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedActionEff1()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromLooked());
            getAbility().getSourceCardIndex().getIndexedInstance().addToHand(data);
            
            getAbility().getSourceCardIndex().getIndexedInstance().returnToDeckOrdered(CardLocation.LOOKED, DeckPosition.BOTTOM);
        }
        private void onAttachedActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,12000)).get();
            getAbility().getSourceCardIndex().getIndexedInstance().addToHand(target);
        }
        private void onAttachedActionEff3()
        {
            disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            getAbility().getSourceCardIndex().getIndexedInstance().trash(target);
        }
    }
}

