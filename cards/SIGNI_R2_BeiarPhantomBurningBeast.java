package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R2_BeiarPhantomBurningBeast extends Card {
    
    public SIGNI_R2_BeiarPhantomBurningBeast()
    {
        setImageSets("WXDi-P06-058");
        
        setOriginalName("幻焼獣　ベイア");
        setAltNames("ゲンショウジュウベイア Genshoujuu Beia");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このシグニのパワーが8000以上の場合、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。"
        );
        
        setName("en", "Beiar, Phantom Burning Beast");
        setDescription("en",
                "@U: At the beginning of your attack phase, if this SIGNI's power is 8000 or more, put target card from your opponent's Ener Zone that does not share a color with your opponent's Center LRIG into their trash."
        );
        
        setName("en_fan", "Beiar, Phantom Burning Beast");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if this SIGNI's power is 8000 or more, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash."
        );
        
		setName("zh_simplified", "幻烧兽 熊");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这只精灵的力量在8000以上的场合，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(2);
        setPower(5000);
        
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
            if(getPower().getValue() >= 8000)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner()).get();
                trash(target);
            }
        }
    }
}
