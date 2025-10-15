package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class SIGNI_R3_CodeRideTaxi extends Card {

    public SIGNI_R3_CodeRideTaxi()
    {
        setImageSets("WXK01-074");

        setOriginalName("コードライド　タクシー");
        setAltNames("コードライドタクシー Koodo Raido Takusi");
        setDescription("jp",
                "\\C：【ダブルクラッシュ】\n" +
                "@U：このシグニがドライブ状態になったとき、あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000する。"
        );

        setName("en", "Code Ride Taxi");
        setDescription("en",
                "\\C: [[Double Crush]]\n" +
                "@U: When this SIGNI enters the drive state, target 1 of your SIGNI, and until end of turn, it gets +5000 power."
        );

		setName("zh_simplified", "骑乘代号 出租车");
        setDescription("zh_simplified", 
                "[驾驶]@C :[[双重击溃]]\n" +
                "@U :当这只精灵变为驾驶状态时，你的精灵1只作为对象，直到回合结束时为止，其的力量+5000。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(3);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            Ability cont = registerStockAbility(new StockAbilityDoubleCrush());
            cont.setCondition(this::onStockEffCond);

            registerAutoAbility(GameEventId.DRIVE, this::onAutoEff);
        }
        
        private ConditionState onStockEffCond()
        {
            return isState(CardStateFlag.IN_DRIVE) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 5000, ChronoDuration.turnEnd());
        }
    }
}
