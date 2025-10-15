package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_DonaImRootingForYou extends Card {

    public LRIGA_W1_DonaImRootingForYou()
    {
        setImageSets("WXDi-P09-028");

        setOriginalName("ドーナ『応援してね！』");
        setAltNames("ドーナオウエンシテネ Doona Ouen Shite ne");
        setDescription("jp",
                "@E：対戦相手のレベル１のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@E：#C #Cを得る。"
        );

        setName("en", "Dona \"Cheer Me On!\"");
        setDescription("en",
                "@E: Return target level one SIGNI on your opponent's field to its owner's hand.\n" +
                "@E: Gain #C #C."
        );
        
        setName("en_fan", "Dona \"I'm Rooting For You\"");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 1 SIGNI, and return it to their hand.\n" +
                "@E: Gain #C #C."
        );

		setName("zh_simplified", "多娜『应援！』");
        setDescription("zh_simplified", 
                "@E :对战对手的等级1的精灵1只作为对象，将其返回手牌。\n" +
                "@E :得到#C #C。\n" +
                "（玩家保留币的上限是5个）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.DONA);
        setColor(CardColor.WHITE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(1)).get();
            addToHand(target);
        }
        
        private void onEnterEff2()
        {
            gainCoins(2);
        }
    }
}
