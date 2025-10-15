package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B2_AnahazeWaterPhantom extends Card {
    
    public SIGNI_B2_AnahazeWaterPhantom()
    {
        setImageSets("WXDi-P05-066", "SPDi38-26");
        
        setOriginalName("幻水　アナハゼ");
        setAltNames("ゲンスイアナハゼ Gensui Anahaze");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの手札が７枚以上ある場合、対戦相手のレベル２以下のシグニ１体を対象とし、手札から青のカードを２枚捨ててもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Anahaze, Phantom Aquatic Beast");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have seven or more cards in your hand, you may discard two blue cards. If you do, vanish target level two or less SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Anahaze, Water Phantom");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 7 or more cards in your hand, target 1 of your opponent's level 2 or lower SIGNI, and you may discard 2 blue cards from your hand. If you do, banish it."
        );
        
		setName("zh_simplified", "幻水 虾虎鱼");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的手牌在7张以上的场合，对战对手的等级2以下的精灵1只作为对象，可以从手牌把蓝色的牌2张舍弃。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
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
            if(getHandCount(getOwner()) >= 7)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
                
                if(target != null && discard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter().withColor(CardColor.BLUE)).size() == 2)
                {
                    banish(target);
                }
            }
        }
    }
}
