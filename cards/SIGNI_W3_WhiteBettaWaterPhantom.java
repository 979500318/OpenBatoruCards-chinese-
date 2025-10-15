package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_WhiteBettaWaterPhantom extends Card {
    
    public SIGNI_W3_WhiteBettaWaterPhantom()
    {
        setImageSets("WXDi-P02-054");
        
        setOriginalName("幻水　ホワイトベタ");
        setAltNames("ゲンスイホワイトベタ Gensui Howaito Beta");
        setDescription("jp",
                "@C：対戦相手のターンの間、あなたの手札が４枚以上あるかぎり、このシグニのパワーは＋2000され、このシグニは[[シャドウ（シグニ）]]を得る。"
        );
        
        setName("en", "White Betta, Phantom Aquatic Beast");
        setDescription("en",
                "@C: During your opponent's turn, as long as you have four or more cards in your hand, this SIGNI gets +2000 power and gains [[Shadow -- SIGNI]]."
        );
        
        setName("en_fan", "White Betta, Water Phantom");
        setDescription("en_fan",
                "@C: During your opponent's turn, as long as there are 4 or more cards in your hand, this SIGNI gets +2000 power, and [[Shadow (SIGNI)]]."
        );
        
		setName("zh_simplified", "幻水 纯白斗鱼");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，你的手牌在4张以上时，这只精灵的力量+2000，这只精灵得到[[暗影（精灵）]]。（这只精灵不会被对战对手的精灵作为对象）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(2000),new AbilityGainModifier(this::onConstEffModGetSample));
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && getHandCount(getOwner()) >= 4 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
