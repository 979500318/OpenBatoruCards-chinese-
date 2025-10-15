package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;

public final class ARTS_R_FuelForAFieryPerformance extends Card {

    public ARTS_R_FuelForAFieryPerformance()
    {
        setImageSets("WX25-P2-038");

        setOriginalName("給油火業");
        setAltNames("キョウユカギョウ Kyouyu Kagyou");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、%X %X %X %Xを支払わないかぎり、このシグニをバニッシュする。@@を得る。手札を１枚捨てて、カードを２枚引く。"
        );

        setName("en", "Fuel for a Fiery Performance");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, banish it unless you pay %X %X %X %X.@@" +
                "Discard 1 card from your hand, and draw 2 cards."
        );

		setName("zh_simplified", "给油火业");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U 当这只精灵攻击时，如果不把%X %X %X %X:支付，那么这只精灵破坏。@@\n" +
                "。手牌1张舍弃，抽2张牌。（即使没有手牌舍弃也能抽牌）\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
            
            discard(1);
            draw(2);
        }
        private void onAttachedAutoEff()
        {
            CardIndex cardIndex = getAbility().getSourceCardIndex();
            if(!cardIndex.getIndexedInstance().payEner(Cost.colorless(4)))
            {
                cardIndex.getIndexedInstance().banish(cardIndex);
            }
        }
    }
}
