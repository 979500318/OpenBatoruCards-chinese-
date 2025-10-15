package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class SIGNI_R1_CodeRideSegway extends Card {

    public SIGNI_R1_CodeRideSegway()
    {
        setImageSets("WXK01-049");

        setOriginalName("コードライド　セグエイ");
        setAltNames("コードライドセグエイ Koodo Raido Seguei");
        setDescription("jp",
                "@E %R：あなたのドライブ状態のシグニ１体を対象とし、ターン終了時まで、それは【ダブルクラッシュ】を得る。 " +
                "~#：カードを１枚引く。"
        );

        setName("en", "Code Ride Segway");
        setDescription("en",
                "@E %R: Target 1 of your SIGNI in the drive state, and until end of turn, it gains [[Double Crush]]." +
                "~#Draw 1 card."
        );

		setName("zh_simplified", "骑乘代号 电动平衡车");
        setDescription("zh_simplified", 
                "@E %R:你的驾驶状态的精灵1只作为对象，直到回合结束时为止，其得到[[双重击溃]]。\n" +
                "（持有[[双重击溃]]的精灵因为攻击给予伤害的场合不把生命护甲1张击溃而是把生命护\n" +
                "甲2张击溃）" +
                "~#抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().drive()).get();
            if(target != null) attachAbility(target, new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
        }
    }
}
