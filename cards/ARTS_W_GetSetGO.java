package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.Enter;

public final class ARTS_W_GetSetGO extends Card {

    public ARTS_W_GetSetGO()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-002", Mask.VERTICAL+"WX25-CP1-002U");

        setOriginalName("Get Set, GO!");
        setAltNames("ゲットセットゴー Getto Setto Goo");
        setDescription("jp",
                "以下の４つから２つまで選ぶ。\n" +
                "&E４枚以上@0 代わりに３つまで選ぶ。@0" +
                "$$1あなたのデッキの上からカードを７枚見る。その中からカードを２枚まで手札に加え、残りをシャッフルしてデッキの一番下に置く。\n" +
                "$$2あなたの手札から＜ブルアカ＞のシグニ１枚を場に出す。そのシグニの@E能力は発動しない。\n" +
                "$$3対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。\n" +
                "$$4対戦相手のシグニ１体を対象とし、それが能力を持たない場合、手札から＜ブルアカ＞のカードを１枚捨ててもよい。そうした場合、それを手札に戻す。"
        );

        setName("en", "Get Set, GO!");
        setDescription("en",
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "&E4 or more@0 Instead, @[@|choose up to 3 of the following:|@]@ @0" +
                "$$1 Look at the top 7 cards of your deck. Add up to 2 cards from among them to your hand, and shuffle the rest and put them on the bottom of your deck.\n" +
                "$$2 Put 1 <<Blue Archive>> SIGNI from your hand onto the field. Its @E abilities don't activate.\n" +
                "$$3 Target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities.\n" +
                "$$4 Target 1 of your opponent's SIGNI, and if it has no abilities, you may discard 1 <<Blue Archive>> card from your hand. If you do, return it to their hand."
        );

        setName("es", "Get Set, GO!");
        setDescription("es",
                "@[@|Elige hasta 2 de los siguientes:|@]@\n" +
                "&E4 o mas@0 en cambio, @[@|elige hasta 3 de los siguientes:|@]@ @0" +
                "$$1 Mira 7 cartas del tope de tu mazo y añade hasta 2 cartas de entre ellas a tu mano, baraja el resto y ponlas en el fondo de tu mazo.\n" +
                "$$2 Pon 1 SIGNI <<Blue Archive>> de tu mano al campo, sus habilidades @E no son activadas.\n" +
                "$$3 Selecciona 1 SIGNI oponente y hasta el final del turno, pierde sus habilidades.\n" +
                "$$4 Sleecciona 1 SIGNI oponente y si no tiene habilidades, puedes descartar 1 carta <<Blue Archive>>. Si lo haces, devuelve la SIGNI seleccionada a la mano."
        );

        setName("zh_simplified", "Get Set,GO！");
        setDescription("zh_simplified",
                "从以下的4种选2种最多。\n" +
                "&E4张以上@0作为替代，选3种最多。（先选全部的选择项和对象）@0" +
                "$$1 从你的牌组上面看7张牌。从中把牌2张最多加入手牌，剩下的洗切放置到牌组最下面。\n" +
                "$$2 从你的手牌把<<蔚蓝档案>>精灵1张出场。那只精灵的@E能力不能发动。\n" +
                "$$3 对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。\n" +
                "$$4 对战对手的精灵1只作为对象，其不持有能力的场合，可以从手牌把<<蔚蓝档案>>牌1张舍弃。这样做的场合，将其返回手牌。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(2));
        setUseTiming(UseTiming.ATTACK);

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
            arts.setOnModesChosenPre(this::onARTSEffPreModeChoice);
            arts.setModeChoice(0,2);
            arts.setRecollect(4);
        }
        
        private void onARTSEffPreModeChoice()
        {
            arts.setModeChoice(0, arts.isRecollectFulfilled() ? 3 : 2);
        }

        private void onARTSEff()
        {
            int modes = arts.getChosenModes();
            
            if((modes & 1<<0) != 0)
            {
                look(7);
                
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromLooked());
                addToHand(data);
                
                int countReturned = returnToDeck(getCardsInLooked(getOwner()), DeckPosition.BOTTOM);
                shuffleDeck(countReturned, DeckPosition.BOTTOM);
            }
            if((modes & 1<<1) != 0)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromHand().playable()).get();
                putOnField(cardIndex, Enter.DONT_ACTIVATE);
            }
            if((modes & 1<<2) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
                disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
            if((modes & 1<<3) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                
                if(target != null && target.getIndexedInstance().hasNoAbilities() &&
                   discard(0,1, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).get() != null)
                {
                    addToHand(target);
                }
            }
        }
    }
}
