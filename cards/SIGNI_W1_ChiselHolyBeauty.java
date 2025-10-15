package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_ChiselHolyBeauty extends Card {
    
    public SIGNI_W1_ChiselHolyBeauty()
    {
        setImageSets("WXDi-P08-049");
        
        setOriginalName("聖美　チョウコクトー");
        setAltNames("セイビチョウコクトー Seibi Choukokutoo");
        setDescription("jp",
                "@C：対戦相手のターンの間、このシグニは[[シャドウ（レベル２のシグニ）]]を得る。\n" +
                "@U：対戦相手のアタックフェイズ開始時、対戦相手のレベル１のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。" +
                "~#：ターン終了時まで、対戦相手のすべてのシグニは能力を失う。カードを１枚引く。"
        );
        
        setName("en", "Chisel, Blessed Beauty");
        setDescription("en",
                "@C: During your opponent's turn, this SIGNI gains [[Shadow -- Level two SIGNI]]. \n" +
                "@U: At the beginning of your opponent's attack phase, target level one SIGNI on your opponent's field loses its abilities until end of turn." +
                "~#All SIGNI on your opponent's field lose their abilities until end of turn. Draw a card."
        );
        
        setName("en_fan", "Chisel, Holy Beauty");
        setDescription("en_fan",
                "@C: During your opponent's turn, this SIGNI gains [[Shadow (level 2 SIGNI)]].\n" +
                "@U: At the beginning of your opponent's attack phase, target of your opponent's level 1 SIGNI, and until end of turn, it loses its abilities." +
                "~#Until end of turn, all of your opponent's SIGNI lose their abilities. Draw 1 card."
        );
        
		setName("zh_simplified", "圣美 凿子");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，这只精灵得到[[暗影（等级2的精灵）]]。\n" +
                "@U :对战对手的攻击阶段开始时，对战对手的等级1的精灵1只作为对象，直到回合结束时为止，其的能力失去。" +
                "~#直到回合结束时为止，对战对手的全部的精灵的能力失去。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() == 2 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI().withLevel(1)).get();
            disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
