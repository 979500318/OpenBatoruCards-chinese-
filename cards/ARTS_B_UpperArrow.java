package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;

public final class ARTS_B_UpperArrow extends Card {

    public ARTS_B_UpperArrow()
    {
        setImageSets("WX25-P1-040");

        setOriginalName("アッパー・アロー");
        setAltNames("Appaa Aroo");
        setDescription("jp",
                "対戦相手のパワー12000以下のシグニ１体を対象とし、対戦相手が手札を３枚捨てないかぎり、それをバニッシュする。"
        );

        setName("en", "Upper Arrow");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 12000 or less, and banish it unless your opponent discards 3 cards from their hand."
        );

		setName("zh_simplified", "高地·矢箭");
        setDescription("zh_simplified", 
                "对战对手的力量12000以下的精灵1只作为对象，如果对战对手不把手牌3张舍弃，那么将其破坏。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && discard(getOpponent(), 0,3, ChoiceLogic.BOOLEAN).size() != 3)
            {
                banish(target);
            }
        }
    }
}

