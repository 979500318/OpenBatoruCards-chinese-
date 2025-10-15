package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_B3_GanNingAzureGeneral extends Card {
    
    public SIGNI_B3_GanNingAzureGeneral()
    {
        setImageSets("WXDi-P00-064");
        
        setOriginalName("蒼将　カンネイ");
        setAltNames("ソウショウカンネイ Soushou Kannei");
        setDescription("jp",
                "@C：あなたの手札が４枚以上あるかぎり、このシグニのパワーは＋3000され、このシグニは[[シャドウ（スペル）]]を得る。\n" +
                "@U：このシグニが対戦相手のシグニ１体をバニッシュしたとき、カードを１枚引く。"
        );
        
        setName("en", "Gan Ning, Azure General");
        setDescription("en",
                "@C: As long as you have four or more cards in your hand, this SIGNI gets +3000 power and it gains [[Shadow -- Spell]].\n" +
                "@U: Whenever this SIGNI vanishes a SIGNI on your opponent's field, draw a card."
        );
        
        setName("en_fan", "Gan Ning, Azure General");
        setDescription("en_fan",
                "@C: As long as you have 4 or more cards in your hand, this SIGNI gets +3000 power and it gains [[Shadow (spell)]].\n" +
                "@U: Whenever this SIGNI banishes 1 of your opponent's SIGNI, draw 1 card."
        );
        
		setName("zh_simplified", "苍将 甘宁");
        setDescription("zh_simplified", 
                "@C :你的手牌在4张以上时，这只精灵的力量+3000，这只精灵得到[[暗影（魔法）]]。\n" +
                "@U :当这只精灵把对战对手的精灵1只破坏时，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000),new AbilityGainModifier(this::onConstEffModGetSample));
            
            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) >= 4 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getCardReference().getType() == CardType.SPELL ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond(CardIndex cardIndex)
        {
            return getEvent().getSourceCardIndex() == getCardIndex() && !isOwnCard(cardIndex) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex cardIndex)
        {
            draw(1);
        }
    }
}
