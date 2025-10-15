package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class LRIGA_B2_MikomikoByebye extends Card {
    
    public LRIGA_B2_MikomikoByebye()
    {
        setImageSets("WXDi-P07-026");
        
        setOriginalName("みこみこ☆ばいばい");
        setAltNames("ミコミコバイバイ Mikomiko Baibai Mikomiko Byebye");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "Mikomiko☆Bye - Bye");
        setDescription("en",
                "@U: At the beginning of your attack phase, your opponent discards a card."
        );
        
        setName("en_fan", "Mikomiko☆Byebye");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "美琴琴☆啵啵");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MIKOMIKO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setCost(Cost.colorless(1));
        setColor(CardColor.BLUE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            discard(getOpponent(), 1);
        }
    }
}
