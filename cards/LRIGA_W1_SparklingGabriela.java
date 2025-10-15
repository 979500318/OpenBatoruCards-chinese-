package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_SparklingGabriela extends Card {

    public LRIGA_W1_SparklingGabriela()
    {
        setImageSets("WXDi-P16-033");

        setOriginalName("閃々！！ガブリエラ");
        setAltNames("センセンガブリエラ Sensen Gaburiera");
        setDescription("jp",
                "@E：対戦相手のパワー5000以下のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Gabriela, Dazzling!!");
        setDescription("en",
                "@E: Put target SIGNI on your opponent's field with power 5000 or less into its owner's trash."
        );
        
        setName("en_fan", "Sparkling!! Gabriela");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 5000 or less, and put it into the trash."
        );

		setName("zh_simplified", "闪闪！！哲布伊来");
        setDescription("zh_simplified", 
                "@E :对战对手的力量5000以下的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.GABRIELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().withPower(0,5000)).get();
            trash(target);
        }
    }
}
