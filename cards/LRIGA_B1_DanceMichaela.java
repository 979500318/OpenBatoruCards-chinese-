package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_B1_DanceMichaela extends Card {

    public LRIGA_B1_DanceMichaela()
    {
        setImageSets("WXDi-P16-043");

        setOriginalName("舞・ミカエラ");
        setAltNames("マイミカエラ Mai Mikaera");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：対戦相手の手札を見て１枚選び、捨てさせる。"
        );

        setName("en", "Michaela, Dance");
        setDescription("en",
                "@E @[Discard a card]@: Look at your opponent's hand and choose a card. Your opponent discards it."
        );
        
        setName("en_fan", "Dance Michaela");
        setDescription("en_fan",
                "@E @[Discard 1 card from your hand]@: Look at your opponent's hand, choose 1 card from it, and discard it."
        );

		setName("zh_simplified", "舞·米卡伊来");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:看对战对手的手牌选1张，舍弃。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MICHAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.BLUE);
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

            registerEnterAbility(new DiscardCost(1), this::onEnterEff);
        }

        private void onEnterEff()
        {
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.DISCARD).OP().fromRevealed()).get();
            discard(cardIndex);
            
            addToHand(getCardsInRevealed(getOpponent()));
        }
    }
}
