package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_G1_MamaAttack extends Card {

    public LRIGA_G1_MamaAttack()
    {
        setImageSets("WXDi-P14-030");

        setOriginalName("ママ♥アタック");
        setAltNames("ママアタック Mama Attaku");
        setDescription("jp",
                "@E：対戦相手のレベル２以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Mama ♥ Attack");
        setDescription("en",
                "@E: Vanish target level two or more SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Mama♥Attack");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 2 or higher SIGNI, and banish it."
        );

		setName("zh_simplified", "妈妈♥攻击");
        setDescription("zh_simplified", 
                "@E :对战对手的等级2以上的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAMA);
        setColor(CardColor.GREEN);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(2,0)).get();
            banish(target);
        }
    }
}
