package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G3_IbarakiDoujiVerdantDevilPrincess extends Card {
    
    public SIGNI_G3_IbarakiDoujiVerdantDevilPrincess()
    {
        setImageSets("WXDi-P04-040");
        
        setOriginalName("翠魔姫　イバラキドウジ");
        setAltNames("スイマキイバラキドウジ Suimaki Iburaki Douji");
        setDescription("jp",
                "@C：[[ランサー]]\n" +
                "@U：あなたのアタックフェイズ開始時、%X %X %Xを支払わないかぎり、このシグニを場からトラッシュに置く。"
        );
        
        setName("en", "Ibaraki - Douji, Jade Evil Queen");
        setDescription("en",
                "@C: [[Lancer]]\n" +
                "@U: At the beginning of your attack phase, put this SIGNI on your field into its owner's trash unless you pay %X %X %X."
        );
        
        setName("en_fan", "Ibaraki-Douji, Verdant Devil Princess");
        setDescription("en_fan",
                "@C: [[Lancer]]\n" +
                "@U: At the beginning of your attack phase, unless you pay %X %X %X, put this SIGNI from the field into the trash."
        );
        
		setName("zh_simplified", "翠魔姬 茨木童子");
        setDescription("zh_simplified", 
                "@C :[[枪兵]]\n" +
                "@U 你的攻击阶段开始时，如果不把%X %X %X:支付，那么把这只精灵从场上放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
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
            
            registerConstantAbility(new AbilityGainModifier(this::onConstEffModGetSample));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityLancer());
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!payEner(Cost.colorless(3)))
            {
                if(CardLocation.isSIGNI(getCardIndex().getLocation())) trash(getCardIndex());
            }
        }
    }
}
