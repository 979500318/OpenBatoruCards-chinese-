package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G2_PraireSphagnumWaterPhantom extends Card {
    
    public SIGNI_G2_PraireSphagnumWaterPhantom()
    {
        setImageSets("WXDi-P02-077");
        
        setOriginalName("幻水　オオミズゴケ");
        setAltNames("ゲンスイオオミズゴケ Gensui Oomizugoke");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの手札が６枚以上ある場合、%Gを支払ってもよい。そうした場合、ターン終了時まで、このシグニは[[ランサー]]を得る。"
        );
        
        setName("en", "Palustre, Phantom Aquatic Beast");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have six or more cards in your hand, you may pay %G. If you do, this SIGNI gains [[Lancer]] until end of turn."
        );
        
        setName("en_fan", "Prairie Sphagnum, Water Phantom");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 6 or more cards in your hand, you may pay %G. If you do, until end of turn, this SIGNI gains [[Lancer]]."
        );
        
		setName("zh_simplified", "幻水 泥炭藓");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的手牌在6张以上的场合，可以支付%G。这样做的场合，直到回合结束时为止，这只精灵得到[[枪兵]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getHandCount(getOwner()) >= 6 && payEner(Cost.color(CardColor.GREEN, 1)))
            {
                attachAbility(getCardIndex(), new StockAbilityLancer(), ChronoDuration.turnEnd());
            }
        }
    }
}
