package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W3_LaalaManakaPriParaIdol extends Card {

    public SIGNI_W3_LaalaManakaPriParaIdol()
    {
        setImageSets("WXDi-P10-033");
        setLinkedImageSets("WXDi-P10-048","WXDi-P10-045");

        setOriginalName("プリパラアイドル　真中らぁら");
        setAltNames("プリパラアイドルマナカラァラ Puripara Aidoru Manaka Raara");
        setDescription("jp",
                "@C：対戦相手のターンの間、《プリパラアイドル　真中らぁら》以外のあなたの＜プリパラ＞のシグニのパワーを＋2000する。\n" +
                "@E %X %X：あなたのデッキの上からカードを５枚公開し、それらのカードをシャッフルしてデッキの一番下に置く。その後、対戦相手のシグニ１体を対象とし、以下から１つを選ぶ。\n" +
                "$$1その中に＜プリパラ＞のシグニが１枚以上ある場合、それを手札に戻す。\n" +
                "$$2その中に《プリパラアイドル　南みれぃ》と《プリパラアイドル　北条そふぃ》がある場合、それをトラッシュに置く。"
        );

        setName("en", "Manaka Laala, Pripara Idol");
        setDescription("en",
                "@C: During your opponent's turn, <<Pripara>> SIGNI on your field other than \"Manaka Laala, Pripara Idol\" get +2000 power.\n" +
                "@E %X %X: Reveal the top five cards of your deck and put them on the bottom of your deck in a random order. Then, choose one of the following.\n" +
                "$$1 Return target SIGNI on your opponent's field to its owner's hand if there are one or more <<Pripara>> SIGNI among the revealed cards.\n" +
                "$$2 Put target SIGNI on your opponent's field into its owner's trash if \"Minami Mirei, Pripara Idol\" and \"Hojo Sophy, Pripara Idol\" are among the revealed cards."
        );
        
        setName("en_fan", "Laala Manaka, PriPara Idol");
        setDescription("en_fan",
                "@C: During your opponent's turn, all of your <<PriPara>> SIGNI other than \"Laala Manaka, PriPara Idol\" get +2000 power.\n" +
                "@E %X %X: Reveal the top 5 cards of your deck, and shuffle them and put them on the bottom of your deck. Then, target 1 of your opponent's SIGNI, and @[@|choose 1 of the following:|@]@\n" +
                "$$1 If there were 1 or more <<PriPara>> SIGNI among them, return it to their hand.\n" +
                "$$2 If \"Mirei Minami, PriPara Idol\" and \"Sophy Hojo, PriPara Idol\" were among them, put it into the trash."
        );

		setName("zh_simplified", "美妙天堂偶像 真中啦啦");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，《プリパラアイドル　真中らぁら》以外的你的<<プリパラ>>精灵的力量+2000。\n" +
                "@E %X %X:从你的牌组上面把5张牌公开，这些牌洗切放置到牌组最下面。然后，对战对手的精灵1只作为对象，从以下选1种。\n" +
                "$$1 其中有<<プリパラ>>精灵在1张以上的场合，将其返回手牌。\n" +
                "$$2 其中有《プリパラアイドル　南みれぃ》和《プリパラアイドル　北条そふぃ》的场合，将其放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PRIPARA).except("プリパラアイドル　真中らぁら"),
                new PowerModifier(2000)
            );

            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            int countRevealed = reveal(5);

            DataTable<CardIndex> data = getCardsInRevealed(getOwner());
            boolean cond1 = data.get() != null && data.stream().anyMatch(cardIndex -> cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.PRIPARA));
            boolean cond2 = data.get() != null && data.stream().anyMatch(cardIndex -> cardIndex.getIndexedInstance().getName().getValue().contains("プリパラアイドル　南みれぃ")) &&
                                                  data.stream().anyMatch(cardIndex -> cardIndex.getIndexedInstance().getName().getValue().contains("プリパラアイドル　北条そふぃ"));
            
            if(countRevealed > 0)
            {
                forEachCardInRevealed(cardIndex -> {
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                });
                shuffleDeck(countRevealed, DeckPosition.BOTTOM);
            }
            
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI()).get();
            
            if(target != null)
            {
                if(playerChoiceMode() == 1)
                {
                    if(cond1) addToHand(target);
                } else {
                    if(cond2) trash(target);
                }
            }
        }
    }
}
