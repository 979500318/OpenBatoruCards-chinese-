package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class ARTS_R_BurningDesireForFieryTruths extends Card {

    public ARTS_R_BurningDesireForFieryTruths()
    {
        setImageSets("WX24-P2-004", "WX24-P2-004U");

        setOriginalName("熱願炎諦");
        setAltNames("ネツガンエンテイ Netsuganentei");
        setDescription("jp",
                "このアーツを使用する際、あなたのルリグデッキから赤のアーツ１枚をルリグトラッシュに置いてもよい。そうした場合、このアーツの使用コストは%R %R %R減る。\n\n" +
                "対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらは@>@U：このシグニがアタックしたとき、%X %X %X %Xを支払わないかぎり、このシグニをバニッシュする。@@を得る。"
        );

        setName("en", "Burning Desire for Fiery Truths");
        setDescription("en",
                "While using this ARTS, you may put 1 red ARTS from your LRIG deck into the LRIG trash. If you do, the use cost of this ARTS is reduced by %R %R %R.\n\n" +
                "Target up to 2 of your opponent's SIGNI, and until end of turn, they gain:" +
                "@>@U: Whenever this SIGNI attacks, banish this SIGNI unless you pay %X %X %X %X."
        );

		setName("zh_simplified", "热愿炎谛");
        setDescription("zh_simplified", 
                "这张必杀使用时，可以从你的分身牌组把红色的必杀1张放置到分身废弃区。这样做的场合，这张必杀的使用费用减%R %R %R。\n" +
                "对战对手的精灵2只最多作为对象，直到回合结束时为止，这些得到\n" +
                "@>@U 当这只精灵攻击时，如果不把%X %X %X %X:支付，那么这只精灵破坏。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 3));
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

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setReductionCost(new TrashCost(new TargetFilter().own().ARTS().withColor(CardColor.RED).except(getCardIndex()).fromLocation(CardLocation.DECK_LRIG)), Cost.color(CardColor.RED, 3));
        }
        
        private void onARTSEff()
        {
            DataTable<CardIndex> targets = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().SIGNI());
            if(targets.get() != null)
            {
                for(int i=0;i<targets.size();i++)
                {
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                    attachAbility(targets.get(i), attachedAuto, ChronoDuration.turnEnd());
                }
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex source = getAbility().getSourceCardIndex();
            if(!source.getIndexedInstance().payEner(Cost.colorless(4)))
            {
                source.getIndexedInstance().banish(source);
            }
        }
    }
}
