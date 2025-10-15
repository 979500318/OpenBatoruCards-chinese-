package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W2_EelWhiteWaterPhantom extends Card {
    
    public SIGNI_W2_EelWhiteWaterPhantom()
    {
        setImageSets("WXDi-P07-057");
        
        setOriginalName("幻白水　ウナ");
        setAltNames("ゲンパクスイウナ Genpakusui Una");
        setDescription("jp",
                "@C：対戦相手のアタックフェイズの間、あなたの手札が４枚以上あるかぎり、このシグニは【シャドウ】を得る。\n" +
                "@E #C #C：カードを１枚引く。" +
                "~#：ターン終了時まで、対戦相手のすべてのシグニは能力を失う。カードを１枚引く。"
        );
        
        setName("en", "Eel, Phantom Aquatic White Beast");
        setDescription("en",
                "@C: During your opponent's attack phase, as long as you have four or more cards in your hand, this SIGNI gains [[Shadow]]. \n" +
                "@E #C #C: Draw a card." +
                "~#All SIGNI on your opponent's field lose their abilities until end of turn. Draw a card."
        );
        
        setName("en_fan", "Eel, White Water Phantom");
        setDescription("en_fan",
                "@C: During your opponent's attack phase, if there are 4 or more cards in your hand, this SIGNI gains [[Shadow]].\n" +
                "@E #C #C: Draw 1 card." +
                "~#Until end of turn, all of your opponent's SIGNI lose their abilities. Draw 1 card."
        );
        
		setName("zh_simplified", "幻白水 电鳗");
        setDescription("zh_simplified", 
                "@C :对战对手的攻击阶段期间，你的手牌在4张以上时，这只精灵得到[[暗影]]。（这只精灵不会被对战对手作为对象）\n" +
                "@E #C #C:抽1张牌。" +
                "~#直到回合结束时为止，对战对手的全部的精灵的能力失去。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(8000);
        
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
            
            registerEnterAbility(new CoinCost(2), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && GamePhase.isAttackPhase(getCurrentPhase()) &&
                   getHandCount(getOwner()) >= 4 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow());
        }
        
        private void onEnterEff()
        {
            draw(1);
        }
        
        private void onLifeBurstEff()
        {
            disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
