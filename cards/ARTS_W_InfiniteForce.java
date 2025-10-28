package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;

public final class ARTS_W_InfiniteForce extends Card {

    public ARTS_W_InfiniteForce()
    {
        setImageSets("WX24-P2-001", "WX24-P2-001U");

        setOriginalName("インフィニット・フォース");
        setAltNames("インフィニットフォース Infinitto Foosu");
        setDescription("jp",
                "あなたのデッキの上からカードを３枚見る。その中からシグニを１枚まで場に出し、残りを好きな順番でデッキの一番下に置く。その後、あなたのシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、このシグニをアップし、ターン終了時まで、このシグニは能力を失う。@@を得る。\n" +
                "&E４枚以上@0追加で対戦相手のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Infinite Force");
        setDescription("en",
                "Look at the top 3 cards of your deck. Put up to 1 SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. Then, target 1 of your SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, up this SIGNI, and until end of turn, it loses its abilities.@@" +
                "&E4 or more@0 Additionally, target 1 of your opponent's SIGNI, and return it to their hand."
        );

        setName("es", "Fuerza Infinita");
        setDescription("es",
                "Mira 3 cartas del tope de tu mazo. Pon hasta 1 SIGNI de entre ellas al campo y pon el resto en el fondo de tu mazo. Entonces, selecciona 1 SIGNI propia y hasta el final del turno, etsa gana:" +
                "@>@U: Siempre que esta SIGNI ataque, endereza esta SIGNI y hasta el final del turno, pierde sus habilidades.@@" +
                "&E4 o mas@0 Adicionalmente, selecciona 1 SIGNI oponente y devuelvela a la mano."
        );

        setName("zh_simplified", "无穷·原力");
        setDescription("zh_simplified", 
                "从你的牌组上面看3张牌。从中把精灵1张最多出场，剩下的任意顺序放置到牌组最下面。然后，你的精灵1只作为对象，直到回合结束时为止，其得到" +
                "@>@U :当这只精灵攻击时，这只精灵竖直，直到回合结束时为止，这只精灵的能力失去。@@" +
                "&E4张以上@0追加对战对手的精灵1只作为对象，将其返回手牌。"
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
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff).setRecollect(4);
        }

        private void onARTSEff()
        {
            look(3);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable()).get();
            putOnField(cardIndex);

            returnToDeckOrdered(CardLocation.LOOKED, DeckPosition.BOTTOM);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }

            if(getAbility().isRecollectFulfilled())
            {
                target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                addToHand(target);
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex source = getAbility().getSourceCardIndex();
            source.getIndexedInstance().up();
            source.getIndexedInstance().disableAllAbilities(source, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
    }
}
