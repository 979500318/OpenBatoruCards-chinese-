package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityHarmony;

public final class SIGNI_R3_PhoenixNaturalStarPrincess extends Card {
    
    public SIGNI_R3_PhoenixNaturalStarPrincess()
    {
        setImageSets("WXDi-P02-038");
        
        setOriginalName("羅星姫　ホウオーザ");
        setAltNames("ラセイキホウオーザ Raseiki Hououza");
        setDescription("jp",
                "=H 白のルリグ１体\n\n" +
                "@U：あなたのアタックフェイズ開始時、このシグニがアップ状態の場合、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。\n" +
                "@U：あなたのターン終了時、このターンにあなたの効果によって対戦相手のエナゾーンからカードが２枚以上トラッシュに置かれていた場合、カードを１枚引く。"
        );
        
        setName("en", "Phoenix, Natural Planet Queen");
        setDescription("en",
                "=H One white LRIG\n\n" +
                "@U: At the beginning of your attack phase, if this SIGNI is upped, put target card from your opponent's Ener Zone that does not share a color with that player's center LRIG into their trash.\n" +
                "@U: At the end of your turn, if two or more cards from your opponent's Ener Zone were put into their trash by your effect this turn, draw a card."
        );
        
        setName("en_fan", "Phoenix, Natural Star Princess");
        setDescription("en_fan",
                "=H 1 white LRIG\n\n" +
                "@U: At the beginning of your attack phase, if this SIGNI is upped, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash.\n" +
                "@U: At the end of your turn, if 2 or more cards were put from your opponent's ener zone into the trash by your effect this turn, draw 1 card."
        );
        
		setName("zh_simplified", "罗星姬 凤凰座");
        setDescription("zh_simplified", 
                "=H白色的分身1只\n" +
                "@U :你的攻击阶段开始时，这只精灵在竖直状态的场合，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。\n" +
                "@U :你的回合结束时，这个回合因为你的效果从对战对手的能量区把牌2张以上放置到废弃区的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor()))).get();
                trash(target);
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.TRASH &&
                event.getSourceAbility() != null && isOwnCard(event.getSource()) &&
                !isOwnCard(event.getCaller()) && event.getCaller().getLocation() == CardLocation.ENER) >= 2)
            {
                draw(1);
            }
        }
    }
}
