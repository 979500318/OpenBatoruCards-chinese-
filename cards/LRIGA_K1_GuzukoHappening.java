package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K1_GuzukoHappening extends Card {

    public LRIGA_K1_GuzukoHappening()
    {
        setImageSets("WXDi-P14-035");

        setOriginalName("グズ子～ハプニング～");
        setAltNames("グズコハプニング Guzuko Hapaningu");
        setDescription("jp",
                "@E：対戦相手のレベル１のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E：#C #Cを得る。"
        );

        setName("en", "Guzuko ~Incident~");
        setDescription("en",
                "@E: Vanish target level one SIGNI on your opponent's field.\n@E: Gain #C #C."
        );
        
        setName("en_fan", "Guzuko~Happening~");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 1 SIGNI, and banish it.\n" +
                "@E: Gain #C #C."
        );

		setName("zh_simplified", "迟钝子～乍现～");
        setDescription("zh_simplified", 
                "@E :对战对手的等级1的精灵1只作为对象，将其破坏。\n" +
                "@E :得到#C #C。\n" +
                "（玩家保留币的上限是5个）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.GUZUKO);
        setColor(CardColor.BLACK);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

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
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(1)).get();
            banish(target);
        }

        private void onEnterEff2()
        {
            gainCoins(2);
        }
    }
}
