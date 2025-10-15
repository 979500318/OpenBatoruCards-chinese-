package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G3_TigerRoaringGun extends Card {
    
    public SIGNI_G3_TigerRoaringGun()
    {
        setImageSets("WXDi-P05-041");
        
        setOriginalName("轟砲　ティーガー");
        setAltNames("ゴウホウティーガー Gouhou Tiigaa");
        setDescription("jp",
                "@C：このシグニが覚醒状態であるかぎり、このシグニのパワーは＋1000され、このシグニは[[ランサー]]と[[シャドウ（パワー10000以下のシグニ）]]を得る。\n" +
                "@U：このシグニがバトルによって対戦相手のシグニ１体をバニッシュしたとき、このシグニは覚醒する。"
        );
        
        setName("en", "Tiger, Roaring Cannon");
        setDescription("en",
                "@C: As long as this SIGNI is awakened, it gets +1000 power and gains [[Lancer]] and [[Shadow -- SIGNI with power 10000 or less]]. \n" +
                "@U: When this SIGNI vanishes a SIGNI on your opponent's field through battle, it is awakened. "
        );
        
        setName("en_fan", "Tiger, Roaring Gun");
        setDescription("en_fan",
                "@C: As long as this SIGNI is awakened, this SIGNI gets +1000 power, and this SIGNI gains [[Lancer]], and [[Shadow (SIGNI with power 10000 or less)]].\n" +
                "@U: Whenever this SIGNI banishes an opponent's SIGNI in battle, this SIGNI awakens."
        );
        
		setName("zh_simplified", "轰炮 虎式重型坦克");
        setDescription("zh_simplified", 
                "@C :这只精灵在觉醒状态时，这只精灵的力量+1000，这只精灵得到[[枪兵]]和[[暗影（力量10000以下的精灵）]]。\n" +
                "@U :当这只精灵因为战斗把对战对手的精灵1只破坏时，这只精灵觉醒。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
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
            
            registerConstantAbility(this::onConstEffCond,
                new PowerModifier(1000),
                new AbilityGainModifier(this::onConstEffMod2GetSample),
                new AbilityGainModifier(this::onConstEffMod3GetSample)
            );
            
            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return isState(CardStateFlag.AWAKENED) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffMod2GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityLancer());
        }
        private Ability onConstEffMod3GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getPower().getValue() <= 10000 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceCardIndex() == getCardIndex() && getEvent().getSourceAbility() == null && !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!isState(CardStateFlag.AWAKENED)) getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }
    }
}
