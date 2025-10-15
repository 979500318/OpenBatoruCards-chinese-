package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_RememberOminous extends Card {

    public LRIGA_W1_RememberOminous()
    {
        setImageSets("WXDi-P15-032");

        setOriginalName("リメンバ・オミノス");
        setAltNames("リメンバオミノス Rimenba Ominosu");
        setDescription("jp",
                "@E：対戦相手のレベル１のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Remember Ominous");
        setDescription("en",
                "@E: Put target level one SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "Remember Ominous");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 1 SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "忆·不吉");
        setDescription("zh_simplified", 
                "@E :对战对手的等级1的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.REMEMBER);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().withLevel(1)).get();
            trash(target);
        }
    }
}
