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

public final class SIGNI_R1_FornaxNaturalStar extends Card {
    
    public SIGNI_R1_FornaxNaturalStar()
    {
        setImageSets("WXDi-P07-061");
        
        setOriginalName("羅星　フォルナックス");
        setAltNames("ラセイフォルナックス Rasei Forunakkusu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、手札からレベル１のシグニを１枚捨ててもよい。そうした場合、それをトラッシュに置く。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Fornax, Natural Planet");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard a level one SIGNI. If you do, put target card from your opponent's Ener Zone that does not share a color with your opponent's Center LRIG into their trash." +
                "~#You may discard a card. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Fornax, Natural Star");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 card from your opponent's ener zone that doesn't share a color with your opponent's center LRIG, and you may discard 1 level 1 SIGNI from your hand. If you do, put it into the trash." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may discard 1 card from your hand. If you do, banish it."
        );
        
		setName("zh_simplified", "罗星 天炉座");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，可以从手牌把等级1的精灵1张舍弃。这样做的场合，将其放置到废弃区。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(2000);
        
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner()).get();
            
            if(target != null && discard(0,1, new TargetFilter().SIGNI().withLevel(1)).get() != null)
            {
                trash(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                banish(target);
            }
        }
    }
}
