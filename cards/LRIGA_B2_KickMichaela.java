package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_KickMichaela extends Card {

    public LRIGA_B2_KickMichaela()
    {
        setImageSets("WXDi-P16-045");

        setOriginalName("蹴・ミカエラ");
        setAltNames("シュウミカエラ Shuu Mikaera");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをダウンする。\n" +
                "@E %B %X %X：対戦相手のダウン状態のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Michaela, Kick");
        setDescription("en",
                "@E: Down target SIGNI on your opponent's field.\n@E %B %X %X: Put target downed SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Kick Michaela");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and down it.\n" +
                "@E %B %X %X: Target 1 of your opponent's downed SIGNI, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "蹴·米卡伊来");
        setDescription("zh_simplified", 
                "@E 对战对手的精灵1只作为对象，将其#D。\n" +
                "@E %B%X %X对战对手的#D状态的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MICHAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.BLUE);
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(2)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
            down(target);
        }
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().downed()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
