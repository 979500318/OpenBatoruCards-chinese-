package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class ARTS_G_InHighSpirits extends Card {

    public ARTS_G_InHighSpirits()
    {
        setImageSets("WX13-018", "WXK01-027");

        setOriginalName("意気軒昂");
        setAltNames("エールアゲイン Eeru Agein Yell Again");
        setDescription("jp",
                "@[アンコール]@ -- %G %X\n\n" +
                "シグニ１体を対象とし、ターン終了時まで、それのパワーを＋4000する。"
        );

        setName("en", "In High Spirits");
        setDescription("en",
                "@[Encore]@ -- %G %X\n\n" +
                "Target 1 SIGNI, and until end of turn, it gets +4000 power."
        );

		setName("zh_simplified", "意气轩昂");
        setDescription("zh_simplified", 
                "召还—%G%X（可以把召还费用追加支付使用。这样做的场合，此牌追加得到\n" +
                "@>:这张牌返回分身牌组。@@\n" +
                "精灵1只作为对象，直到回合结束时为止，其的力量+4000。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK | UseTiming.SPELLCUTIN);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setEncoreCost(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)));
        }
        
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).SIGNI()).get();
            gainPower(target, 4000, ChronoDuration.turnEnd());
        }
    }
}
