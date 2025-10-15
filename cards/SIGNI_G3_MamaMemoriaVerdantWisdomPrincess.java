package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G3_MamaMemoriaVerdantWisdomPrincess extends Card {
    
    public SIGNI_G3_MamaMemoriaVerdantWisdomPrincess()
    {
        setImageSets("WXDi-P07-045", "WXDi-P07-045P");
        
        setOriginalName("翠英姫　ママ//メモリア");
        setAltNames("スイエイキママメモリア Suieiki Mama Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるシグニのパワーの合計が30000の場合、【エナチャージ２】をする。\n" +
                "@U：このシグニがアタックしたとき、このシグニのパワーが13000の場合、次の対戦相手のターン終了時まで、このシグニは[[シャドウ（パワー10000以下のシグニ）]]を得る。\n" +
                "@A #C：次の対戦相手のターン終了時まで、このシグニのパワーを＋3000する。"
        );
        
        setName("en", "Mama//Memoria, Jade Wisdom Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if the total power of the SIGNI on your field is exactly 30000, [[Ener Charge 2]].\n" +
                "@U: Whenever this SIGNI attacks, if this SIGNI's power is exactly 13000, it gains [[Shadow -- SIGNI with power 10000 or less]] until the end of your opponent's next end phase.\n" +
                "@A #C: This SIGNI gets +3000 power until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Mama//Memoria, Verdant Wisdom Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if the total power of your SIGNI on the field is 30000, [[Ener Charge 2]].\n" +
                "@U: Whenever this SIGNI attacks, if this SIGNI's power is 13000, until the end of your opponent's next turn, this SIGNI gains [[Shadow (SIGNI with power 10000 or less)]].\n" +
                "@A #C: Until the end of your opponent's next turn, this SIGNI gets +3000 power."
        );
        
		setName("zh_simplified", "翠英姬 妈妈//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的精灵的力量的合计在30000的场合，[[能量填充2]]。\n" +
                "@U :当这只精灵攻击时，这只精灵的力量在13000的场合，直到下一个对战对手的回合结束时为止，这只精灵得到[[暗影（力量10000以下的精灵）]]。\n" +
                "@A #C:直到下一个对战对手的回合结束时为止，这只精灵的力量+3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            
            registerActionAbility(new CoinCost(1), this::onActionEff);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().getExportedData().stream().mapToDouble(c -> ((CardIndex)c).getIndexedInstance().getPower().getValue()).sum() == 30000)
            {
                enerCharge(2);
            }
        }
        
        private void onAutoEff2()
        {
            if(getPower().getValue() == 13000)
            {
                attachAbility(getCardIndex(), new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getPower().getValue() <= 10000 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onActionEff()
        {
            gainPower(getCardIndex(), 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
