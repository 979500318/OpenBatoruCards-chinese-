package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class ARTS_K_BlackWish extends Card {

    public ARTS_K_BlackWish()
    {
        setImageSets("WX24-P2-010", "WX24-P2-010U");

        setOriginalName("ブラック・ウィッシュ");
        setAltNames("ブラックウィッシュ Burakku Uishhu");
        setDescription("jp",
                "このアーツを使用する際、あなたのルリグデッキから黒のアーツ１枚をルリグトラッシュに置いてもよい。そうした場合、このアーツの使用コストは%K %K %K減る。\n\n" +
                "対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらは@>@C：あなたの他のシグニ２体を場からトラッシュに置かないかぎりアタックできない。@@を得る。"
        );

        setName("en", "Black Wish");
        setDescription("en",
                "While using this ARTS, you may put 1 black ARTS from your LRIG deck into the LRIG trash. If you do, the use cost of this ARTS is reduced by %K %K %K.\n\n" +
                "Target up to 2 of your opponent's SIGNI, and until end of turn, they gain:" +
                "@>@C: Can't attack unless you put 2 of your other SIGNI from the field into the trash."
        );

        setName("zh_simplified", "漆黑·愿望");
        setDescription("zh_simplified", 
                "这张必杀使用时，可以从你的分身牌组把黑色的必杀1张放置到分身废弃区。这样做的场合，这张必杀的使用费用减%K %K %K。\n" +
                "对战对手的精灵2只最多作为对象，直到回合结束时为止，这些得到" +
                "@>@C :如果不把你的其他的精灵2只从场上放置到废弃区，那么不能攻击。@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 3));
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
            arts.setReductionCost(new TrashCost(new TargetFilter().own().ARTS().withColor(CardColor.BLACK).except(cardId).fromLocation(CardLocation.DECK_LRIG)), Cost.color(CardColor.BLACK, 3));
        }
        
        private void onARTSEff()
        {
            DataTable<CardIndex> targets = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().SIGNI());
            if(targets.get() != null)
            {
                for(int i=0;i<targets.size();i++)
                {
                    CardIndex cardIndex = targets.get(i);
                    ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK,
                        data -> new TrashCost(2, new TargetFilter().SIGNI().except(cardIndex))
                    ));
                    attachAbility(cardIndex, attachedConst, ChronoDuration.turnEnd());
                }
            }
        }
    }
}
