package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G1_KyuusoPhantomApparition extends Card {
    
    public SIGNI_G1_KyuusoPhantomApparition()
    {
        setImageSets("WDK03-017");
        
        setOriginalName("幻怪　キュウソ");
        setAltNames("ゲンカイキュウソ Genkai Kyuuso");
        setDescription("jp",
                "@U $T1：あなたがアーツを使用したとき、ターン終了時まで、このシグニは【ランサー】を得る。" +
                "~#：カードを１枚引く。"
        );
        
        setName("en", "Kyuuso, Phantom Apparition");
        setDescription("en",
                "@U $T1: When you use ARTS, until end of turn, this SIGNI gains [[Lancer]]." +
                "~#Draw 1 card."
        );
        
		setName("zh_simplified", "幻怪 旧鼠");
        setDescription("zh_simplified", 
                "@U $T1 :当你把必杀使用时，直到回合结束时为止，这只精灵得到[[枪兵]]。" +
                "~#抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.USE_ARTS, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getCardIndex().isSIGNIOnField())
            {
                attachAbility(getCardIndex(), new StockAbilityLancer(), ChronoDuration.turnEnd());
            }
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
        }
    }
}
