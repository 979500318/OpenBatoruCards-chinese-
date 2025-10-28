package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class ARTS_W_AriusSquadOperationStart extends Card {

    public ARTS_W_AriusSquadOperationStart()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-001", Mask.VERTICAL+"WX25-CP1-001U");

        setOriginalName("アリウススクワッド、作戦開始");
        setAltNames("アリウススクワッドサクセンカイシ Ariusu Sukuwaddo Sakusen Kaishi");
        setDescription("jp",
                "あなたのデッキの上からカードを７枚見る。その中から＜ブルアカ＞のカードを２枚まで公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。その後、この方法でカード２枚を手札に加えた場合、対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "&E４枚以上@0追加であなたのルリグ１体を対象とし、ターン終了時まで、それは@>@U $T1：このルリグがアタックしたとき、対戦相手が%X %X %Xを支払わないかぎり、対戦相手にダメージを与える。@@を得る。"
        );

        setName("en", "Arius Squad, Operation Start");
        setDescription("en",
                "Look at the top 7 cards of your deck. Reveal up to 2 <<Blue Archive>> cards from among them, add them to your hand, and shuffle the rest and put them on the bottom of your deck. Then, if you added 2 cards to your hand this way, target 1 of your opponent's SIGNI, and return it to their hand.\n" +
                "&E4 or more@0 Target your LRIG, and until end of turn, it gains:" +
                "@>@U $T1: When this LRIG attacks, damage your opponent unless they pay %X %X %X."
        );

        setName("es", "Escuadron Arius, la operación inicia.");
        setDescription("es",
                "Mira 7 cartas del tope de tu mazo. Revela hasta 2 cartas <<Blue Archive>> de entre ellas y añadelas a tu mano, baraja el resto y ponlo en el fondo de tu mazo. Entonces, si agregaste 2 cartas de esta manera, selecciona 1 SIGNI oponente y devuelvela a la mano.\n" +
                "&E4 o mas@0 Selecciona tu LRIG, hasta el final del turno, esta gana:" +
                "@>@U $T1: Cuando esta LRIG ataque, daña a tu oponente a menos que pague %X %X %X."
        );

        setName("zh_simplified", "阿里乌斯小队，作战开始");
        setDescription("zh_simplified", 
                "从你的牌组上面看7张牌。从中把<<蔚蓝档案>>牌2张最多公开加入手牌，剩下的洗切放置到牌组最下面。然后，这个方法把2张牌加入手牌的场合，对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "&E4张以上@0追加你的分身1只作为对象，直到回合结束时为止，其得到" +
                "@>@U $T1 :当这只分身攻击时，如果对战对手不把%X %X %X支付，那么给予对战对手伤害。@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1));
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
            look(7);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromLooked());
            reveal(data);
            addToHand(data);
            
            int countReturned = returnToDeck(getCardsInLooked(getOwner()), DeckPosition.BOTTOM);
            shuffleDeck(countReturned, DeckPosition.BOTTOM);
            
            if(data.size() == 2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                addToHand(target);
            }
            
            if(getAbility().isRecollectFulfilled())
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().anyLRIG()).get();

                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            if(!payEner(getOpponent(), Cost.colorless(3)))
            {
                getAbility().getSourceCardIndex().getIndexedInstance().damage(getOpponent());
            }
        }
    }
}
