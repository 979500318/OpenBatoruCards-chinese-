package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class SIGNI_R3_ShubNigguraCrimsonDevilPrincess extends Card {
    
    public SIGNI_R3_ShubNigguraCrimsonDevilPrincess()
    {
        setImageSets("WXDi-P00-036");
        
        setOriginalName("紅魔姫　シュブニグラ");
        setAltNames("コウマキシュブニグラ Koomaki Shubunigura");
        setDescription("jp",
                "=T ＜アンシエント・サプライズ＞\n" +
                "^U：このシグニがアタックしたとき、あなたの場にあるすべてのシグニが赤の場合、対戦相手のパワー１２０００以下のシグニ１体を対象とし、%R %Rを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "@E %R %R %R：ターン終了時まで、このシグニは[[ダブルクラッシュ]]を得る。"
        );
        
        setName("en", "Shub-Niggura, Crimson Evil Queen");
        setDescription("en",
                "=T <<Ancient Surprise>>\n" +
                "^U: Whenever this SIGNI attacks, if all the SIGNI on your field are red, you may pay %R %R. If you do, vanish target SIGNI on your opponent's field with power 12000 or less.\n" +
                "@E %R %R %R: This SIGNI gains [[Double Crush]] until end of turn."
        );
        
        setName("en_fan", "Shub-Niggura, Crimson Devil Princess");
        setDescription("en_fan",
                "=T <<Ancient Surprise>>\n" +
                "^U: Whenever this SIGNI attacks, if all of your SIGNI are red, target 1 of your opponent's SIGNI with power 12000 or less, and you may pay %R %R. If you do, banish that SIGNI.\n" +
                "@E %R %R %R: Until end of turn, this SIGNI gains [[Double Crush]]."
        );
        
		setName("zh_simplified", "红魔姬 莎布尼古拉");
        setDescription("zh_simplified", 
                "=T<<アンシエント・サプライズ>>\n" +
                "^U:当这只精灵攻击时，你的场上全部的精灵是红色的场合，对战对手的力量12000以下的精灵1只作为对象，可以支付%R %R。这样做的场合，将其破坏。\n" +
                "@E %R %R %R:直到回合结束时为止，这只精灵得到[[双重击溃]]。（攻击给予伤害则把生命护甲2张击溃）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 3)), this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withColor(CardColor.RED).getValidTargetsCount() == getSIGNICount(getOwner()))
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
                
                if(cardIndex != null && payEner(Cost.color(CardColor.RED, 2)))
                {
                    banish(cardIndex);
                }
            }
        }
        
        private void onEnterEff()
        {
            attachAbility(getCardIndex(), new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
        }
    }
}
