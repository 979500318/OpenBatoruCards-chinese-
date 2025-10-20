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
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;

public final class ARTS_B_FrozenGear extends Card {

    public ARTS_B_FrozenGear()
    {
        setImageSets("WX24-P1-006", "WX24-P1-006U");

        setOriginalName("フローズン・ギア");
        setAltNames("フローズンギア Furoozun Gia");
        setDescription("jp",
                "対戦相手のルリグかシグニ１体を対象とし、それをダウンする。\n" +
                "&E４枚以上@0追加で対戦相手のシグニ１体を対象とし、対戦相手が手札を３枚捨てないかぎり、それをダウンする。"
        );

        setName("en", "Frozen Gear");
        setDescription("en",
                "Target 1 of your opponent's LRIG or SIGNI, and down it.\n" +
                "&E4 or more@0 Additionally, target 1 of your opponent's SIGNI, and down it unless your opponent discards 3 cards from their hand."
        );

		setName("zh_simplified", "冰冻·流年");
        setDescription("zh_simplified", 
                "对战对手的分身或精灵1只作为对象，将其横置。\n" +
                "&E4张以上@0追加对战对手的精灵1只作为对象，如果对战对手不把手牌3张舍弃，那么将其横置。\n"
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

            registerARTSAbility(this::onARTSEff).setRecollect(4);
        }
        
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().fromField()).get();
            down(target);

            if(getAbility().isRecollectFulfilled())
            {
                target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
                
                if(target != null && discard(getOpponent(), 0,3, ChoiceLogic.BOOLEAN).size() != 3)
                {
                    down(target);
                }
            }
        }
    }
}

