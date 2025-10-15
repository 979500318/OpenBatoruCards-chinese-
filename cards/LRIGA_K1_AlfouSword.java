package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K1_AlfouSword extends Card {

    public LRIGA_K1_AlfouSword()
    {
        setImageSets("WXDi-P15-037");

        setOriginalName("アルフォウソード");
        setAltNames("Arufou Soodo");
        setDescription("jp",
                "@E：対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Alfou Sword");
        setDescription("en",
                "@E: Vanish target level two or less SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Alfou Sword");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 2 or lower SIGNI, and banish it."
        );

		setName("zh_simplified", "阿尔芙刀剑");
        setDescription("zh_simplified", 
                "@E :对战对手的等级2以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ALFOU);
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

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
            banish(target);
        }
    }
}
