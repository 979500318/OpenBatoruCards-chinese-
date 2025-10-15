package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_K2_AlfouSplash extends Card {

    public LRIGA_K2_AlfouSplash()
    {
        setImageSets("WXDi-P15-040");

        setOriginalName("アルフォウスプラッシュ");
        setAltNames("Arufou Supurassu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E @[手札を２枚捨てる]@：各プレイヤーは自分のデッキの上からカードを８枚トラッシュに置く。"
        );

        setName("en", "Alfou Splash");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field.\n@E @[Discard two cards]@: Each player puts the top eight cards of their deck into their trash.\n"
        );
        
        setName("en_fan", "Alfou Splash");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@E @[Discard 2 cards from your hand]@: Each player puts the top 8 cards of their deck into the trash."
        );

		setName("zh_simplified", "阿尔芙飞溅");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E 手牌2张舍弃:各玩家从自己的牌组上面把8张牌放置到废弃区。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(2));
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }

        private void onEnterEff2()
        {
            millDeck(8);
            millDeck(getOpponent(), 8);
        }
    }
}
