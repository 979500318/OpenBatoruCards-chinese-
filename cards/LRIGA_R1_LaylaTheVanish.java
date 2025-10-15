package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_R1_LaylaTheVanish extends Card {

    public LRIGA_R1_LaylaTheVanish()
    {
        setImageSets("WXDi-P12-027");

        setOriginalName("レイラ・ザ・バニッシュ");
        setAltNames("レイラザバニッシュ Reira Za Banisshu");
        setDescription("jp",
                "@E：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Layla the Vanish");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 10000 or less."
        );
        
        setName("en_fan", "Layla the Vanish");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );
        
		setName("zh_simplified", "蕾拉·极·破坏");
        setDescription("zh_simplified", 
                "@E :对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LAYLA);
        setColor(CardColor.RED);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
    }
}
