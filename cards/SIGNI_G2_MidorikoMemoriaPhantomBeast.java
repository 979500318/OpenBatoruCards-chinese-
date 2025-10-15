package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.PrintedValue;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G2_MidorikoMemoriaPhantomBeast extends Card {
    
    public SIGNI_G2_MidorikoMemoriaPhantomBeast()
    {
        setImageSets("WXDi-P08-072", "WXDi-P08-072P");
        
        setOriginalName("幻獣　緑子//メモリア");
        setAltNames("ゲンジュウミドリコメモリア　Genjuu Midoriko Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、表記されているパワーよりパワーの高いあなたのシグニ１体を対象とし、%G %G %Xを支払ってもよい。そうした場合、ターン終了時まで、それは【ランサー】を得る。\n" +
                "@E @[エクシード３]@：次の対戦相手のターン終了時まで、このシグニのパワーを＋5000する。"
        );
        
        setName("en", "Midoriko//Memoria, Phantom Beast");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may pay %G %G %X. If you do, target SIGNI on your field with power higher than its originally listed power gains [[Lancer]] until end of turn.\n" +
                "@E @[Exceed 3]@: This SIGNI gets +5000 power until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Midoriko//Memoria, Phantom Beast");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your SIGNI with power higher than its printed power, and you may pay %G %G %X. If you do, until end of turn, it gains [[Lancer]].\n" +
                "@E @[Exceed 3]@: Until the end of your opponent's next turn, this SIGNI gets +5000 power."
        );
        
		setName("zh_simplified", "幻兽 绿子//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，比正面记载的力量高的力量的你的精灵1只作为对象，可以支付%G %G%X。这样做的场合，直到回合结束时为止，其得到[[枪兵]]。\n" +
                "@E @[超越 3]@（从你的分身的下面把牌合计3张放置到分身废弃区）:直到下一个对战对手的回合结束时为止，这只精灵的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new ExceedCost(3), this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withPrintedPower(PrintedValue.LOWER_THAN_CURRENT)).get();
            
            if(target != null && payEner(Cost.color(CardColor.GREEN, 2) + Cost.colorless(1)))
            {
                attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            gainPower(getCardIndex(), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
