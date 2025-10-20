package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityHarmony;

public final class SIGNI_B3_FireflySquidWaterPhantomDeity extends Card {
    
    public SIGNI_B3_FireflySquidWaterPhantomDeity()
    {
        setImageSets("WXDi-P03-041", "SPDi02-23");
        
        setOriginalName("幻水神　ホタルイカ");
        setAltNames("ゲンスイシンホタルイカ Gensuishin Hotaruika");
        setDescription("jp",
                "=H 白のルリグ１体\n\n" +
                "@U：あなたのアタックフェイズ開始時、カードを１枚引く。\n" +
                "@U $T1：あなたが自分の効果によってカードを１枚以上捨てたとき、対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );
        
        setName("en", "Firefly Squid, Aquatic Phantom Deity");
        setDescription("en",
                "=H One white LRIG \n" +
                "@U: At the beginning of your attack phase, draw a card.\n" +
                "@U $T1: When you discard one or more cards by your effect, your opponent discards a card at random."
        );
        
        setName("en_fan", "Firefly Squid, Water Phantom Deity");
        setDescription("en_fan",
                "=H 1 white LRIG\n\n" +
                "@U: At the beginning of your attack phase, draw 1 card.\n" +
                "@U $T1: When you discard 1 or more cards by your effect, choose 1 card from your opponent's hand without looking, and discard it."
        );
        
		setName("zh_simplified", "幻水神 荧光乌贼");
        setDescription("zh_simplified", 
                "=H白色的分身1只（当这只精灵出场时，如果不把你的竖直状态的白色的分身1只横置，那么将此牌横置）\n" +
                "@U :你的攻击阶段开始时，抽1张牌。\n" +
                "@U $T1 :当你因为自己的效果把牌1张以上舍弃时，不看对战对手的手牌选1张，舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
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
            
            registerStockAbility(new StockAbilityHarmony(1, new TargetFilter().withColor(CardColor.WHITE)));
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            draw(1);
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return isOwnCard(caller) && getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSourceCardIndex()) && getEvent().getSourceCost() == null && getEvent().isAtOnce(1) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
    }
}
