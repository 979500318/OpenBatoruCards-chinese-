package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W2_MaChaoHolyGeneral extends Card {
    
    public SIGNI_W2_MaChaoHolyGeneral()
    {
        setImageSets("WXDi-P01-049");
        
        setOriginalName("聖将　バチョウ");
        setAltNames("セイショウバチョウ Seishou Bachou");
        setDescription("jp",
                "@C：あなたのライフクロスが２枚以下であるかぎり、このシグニのパワーは＋3000され、対戦相手のターンの間、このシグニは[[シャドウ（シグニ）]]を得る。\n" +
                "@E %W %W %X %X %X：対戦相手のシグニ１体を対象とし、それをトラッシュに置く。"
        );
        
        setName("en", "Ma Chao, Blessed General");
        setDescription("en",
                "@C: As long as you have two or less cards in your Life Cloth, this SIGNI gets +3000 power and during your opponent's turn, gains [[Shadow — SIGNI]].\n" +
                "@E %W %W %X %X %X: Put target SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "Ma Chao, Holy General");
        setDescription("en_fan",
                "@C: As long as you have 2 or less life cloth, this SIGNI gets +3000 power, and during your opponent's turn, this SIGNI gains [[Shadow (SIGNI)]].\n" +
                "@E %W %W %X %X %X: Target 1 of your opponent's SIGNI, and put it into the trash."
        );
        
		setName("zh_simplified", "圣将 马超");
        setDescription("zh_simplified", 
                "@C :你的生命护甲在2张以下时，这只精灵的力量+3000，对战对手的回合期间，这只精灵得到[[暗影（精灵）]]。\n" +
                "@E %W %W%X %X %X:对战对手的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(7000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000),new AbilityGainModifier(this::onConstEffModCond, this::onConstEffModGetSample));
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 2) + Cost.colorless(3)), this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getLifeClothCount(getOwner()) <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private ConditionState onConstEffModCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            trash(target);
        }
    }
}
