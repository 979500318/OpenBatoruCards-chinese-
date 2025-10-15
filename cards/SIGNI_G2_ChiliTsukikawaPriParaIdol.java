package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class SIGNI_G2_ChiliTsukikawaPriParaIdol extends Card {

    public SIGNI_G2_ChiliTsukikawaPriParaIdol()
    {
        setImageSets("WXDi-P10-067");

        setOriginalName("プリパラアイドル　月川ちり");
        setAltNames("プリパラアイドルツキカワチリ Puripara Aidoru Tsukikawa Chiri");
        setDescription("jp",
                "@C：あなたの場に他の＜プリパラ＞のシグニがあるかぎり、このシグニのパワーは＋2000される。\n" +
                "@A @[エナゾーンから＜プリパラ＞のシグニ３枚をトラッシュに置く]@：ターン終了時まで、このシグニは【ランサー】を得る。"
        );

        setName("en", "Tsukikawa Chili, Pripara Idol");
        setDescription("en",
                "@C: As long as there is another <<Pripara>> SIGNI on your field, this SIGNI gets +2000 power.\n" +
                "@A @[Put three <<Pripara>> SIGNI from your Ener Zone into your trash]@: This SIGNI gains [[Lancer]] until end of turn."
        );
        
        setName("en_fan", "Chili Tsukikawa, PriPara Idol");
        setDescription("en_fan",
                "@C: As long as there is another <<PriPara>> SIGNI on your field, this SIGNI gets +2000 power.\n" +
                "@A @[Put 3 <<PriPara>> SIGNI from your ener zone into the trash]@: Until end of turn, this SIGNI gains [[Lancer]]."
        );

		setName("zh_simplified", "美妙天堂偶像 月川知里");
        setDescription("zh_simplified", 
                "@C :你的场上有其他的<<プリパラ>>精灵时，这只精灵的力量+2000。\n" +
                "@A 从能量区把<<プリパラ>>精灵3张放置到废弃区:直到回合结束时为止，这只精灵得到[[枪兵]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(2000));

            ActionAbility act = registerActionAbility(new TrashCost(3, new TargetFilter().SIGNI().withClass(CardSIGNIClass.PRIPARA).fromEner()), this::onActionEff);
            act.setCondition(this::onActionEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PRIPARA).except(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onActionEffCond()
        {
            return getCardIndex().getIndexedInstance().getAbilityList().stream().anyMatch(ability ->
                    ability.getSourceStockAbility() instanceof StockAbilityLancer ||
                    ability.getSourceStockAbility() instanceof StockAbilitySLancer) ? ConditionState.WARN : ConditionState.OK;
        }
        private void onActionEff()
        {
            attachAbility(getCardIndex(), new StockAbilityLancer(), ChronoDuration.turnEnd());
        }
    }
}
