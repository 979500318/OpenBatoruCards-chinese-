package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityHarmony;

public final class SIGNI_W3_ZhugeLiangHolyGeneralPrincess extends Card {
    
    public SIGNI_W3_ZhugeLiangHolyGeneralPrincess()
    {
        setImageSets("WXDi-P03-035");
        
        setOriginalName("聖将姫　コウメイ");
        setAltNames("セイショウキコウメイ Seishouki Koumei");
        setDescription("jp",
                "=H 緑のルリグ１体\n\n" +
                "@U：あなたの白のルリグ１体がアタックしたとき、あなたのアップ状態のシグニ２体をダウンし%W %Xを支払ってもよい。そうした場合、そのルリグをアップし、ターン終了時まで、そのルリグは能力を失う。\n" +
                "@U：アップ状態のこのシグニがバニッシュされたとき、%W %X %Xを支払ってもよい。そうした場合、このシグニをエナゾーンからダウン状態で場に出す。"
        );
        
        setName("en", "Koumei, Blessed General Queen");
        setDescription("en",
                "=H One green LRIG\n" +
                "@U: Whenever a white LRIG on your field attacks, you may down two upped SIGNI on your field and pay %W %X. If you do, up the LRIG that attacked and it loses its abilities until end of turn.\n" +
                "@U: When this SIGNI is vanished while upped, you may pay %W %X %X. If you do, put this SIGNI from your Ener Zone onto your field downed."
        );
        
        setName("en_fan", "Zhuge Liang, Holy General Princess");
        setDescription("en_fan",
                "=H 1 green LRIG\n\n" +
                "@U: Whenever 1 of your white LRIGs attacks, you may down 2 of your upped SIGNI and pay %W %X. If you do, up that LRIG, and until end of turn, that LRIG loses its abilities.\n" +
                "@U: When this upped SIGNI is banished, you may pay %W %X %X. If you do, put this SIGNI from the ener zone onto the field downed."
        );
        
		setName("zh_simplified", "圣将姬 孔明");
        setDescription("zh_simplified", 
                "=H绿色的分身1只\n" +
                "@U 当你的白色的分身1只攻击时，可以把你的竖直状态的精灵2只#D并支付%W%X。这样做的场合，那只分身竖直，直到回合结束时为止，那只分身的能力失去。\n" +
                "@U :当竖直状态的这只精灵被破坏时，可以支付%W%X %X。这样做的场合，这张精灵从能量区以#D状态出场。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
            
            registerStockAbility(new StockAbilityHarmony(1, new TargetFilter().withColor(CardColor.GREEN)));
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isLRIG(caller.getCardReference().getType()) &&
                   caller.getIndexedInstance().getColor().matches(CardColor.WHITE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(payAll(new DownCost(2, new TargetFilter().SIGNI().upped()), new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1))))
            {
                up(caller);
                disableAllAbilities(caller, AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return !isState(CardStateFlag.DOWNED) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2()
        {
            if(getCardIndex().getLocation() == CardLocation.ENER && payEner(Cost.color(CardColor.WHITE, 1) + Cost.colorless(2)))
            {
                if(getCardIndex().getLocation() == CardLocation.ENER) putOnField(getCardIndex(), Enter.DOWNED);
            }
        }
    }
}
