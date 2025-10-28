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

public final class ARTS_RB_OnesOwnBurningCause extends Card {

    public ARTS_RB_OnesOwnBurningCause()
    {
        setImageSets("WX24-P4-004", "WX24-P4-004U");

        setOriginalName("自己顕火");
        setAltNames("ジコケンカ Jiko Kenka");
        setDescription("jp",
                "カードを３枚引く。その後、対戦相手のシグニ１体を対象とし、それのレベル１につき手札を１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "One's Own Burning Cause");
        setDescription("en",
                "Draw 3 cards. Then, target 1 of your opponent's SIGNI, and you may discard 1 card for each of its levels. If you do, banish it."
        );

        setName("zh_simplified", "自己显火");
        setDescription("zh_simplified", 
                "抽3张牌。然后，对战对手的精灵1只作为对象，可以依据其的等级的数量，每有1级就把手牌1张舍弃。这样做的场合，将其破坏。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED, CardColor.BLUE);
        setCost(Cost.color(CardColor.RED, 1) + Cost.color(CardColor.BLUE, 1));
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
            draw(3);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && discard(0,target.getIndexedInstance().getLevel().getValue(), ChoiceLogic.BOOLEAN).get() != null)
            {
                banish(target);
            }
        }
    }
}
