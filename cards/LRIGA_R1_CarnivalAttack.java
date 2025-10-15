package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_R1_CarnivalAttack extends Card {

    public LRIGA_R1_CarnivalAttack()
    {
        setImageSets("WXDi-P11-029");

        setOriginalName("カーニバル　－撃－");
        setAltNames("カーニバルゲキ Kaanibaru Geki");
        setDescription("jp",
                "@E：対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Carnival -Attack-");
        setDescription("en",
                "@E: Vanish target level two or less SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Carnival -Attack-");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 2 or lower SIGNI, and banish it."
        );

		setName("zh_simplified", "嘉年华 -击-");
        setDescription("zh_simplified", 
                "@E :对战对手的等级2以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.CARNIVAL);
        setColor(CardColor.RED);
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

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
            banish(target);
        }
    }
}
