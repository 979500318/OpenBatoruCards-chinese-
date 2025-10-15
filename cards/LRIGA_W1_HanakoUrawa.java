package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_HanakoUrawa extends Card {

    public LRIGA_W1_HanakoUrawa()
    {
        setImageSets("WXDi-CP02-024");

        setOriginalName("浦和ハナコ");
        setAltNames("ウラワハナコ Urawa Hanako");
        setDescription("jp",
                "@E：対戦相手のレベル２以下のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Urawa Hanako");
        setDescription("en",
                "@E: Return target level two or less SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Hanako Urawa");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 2 or lower SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "浦和花子");
        setDescription("zh_simplified", 
                "@E :对战对手的等级2以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAKEUP_WORK_CLUB);
        setColor(CardColor.WHITE);
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

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(0,2)).get();
            addToHand(target);
        }
    }
}

