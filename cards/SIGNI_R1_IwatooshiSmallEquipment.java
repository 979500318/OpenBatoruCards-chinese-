package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R1_IwatooshiSmallEquipment extends Card {
    
    public SIGNI_R1_IwatooshiSmallEquipment()
    {
        setImageSets("WXDi-P04-054", "SPDi01-67");
        
        setOriginalName("小装　イワトオシ");
        setAltNames("ショウソウイワトオシ Shousou Iwatooshi");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、%Xを支払ってもよい。そうした場合、それをトラッシュに置く。"
        );
        
        setName("en", "Iwatooshi, Lightly Armed");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may pay %X. If you do, put target card from your opponent's Ener Zone that does not share a color with your opponent's center LRIG into their trash."
        );
        
        setName("en_fan", "Iwatooshi, Small Equipment");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and you may pay %X. If you do, put it into the trash."
        );
        
		setName("zh_simplified", "小装 岩融");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，可以支付%X。这样做的场合，将其放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(1);
        setPower(2000);
        
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor()))).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                trash(target);
            }
        }
    }
}
