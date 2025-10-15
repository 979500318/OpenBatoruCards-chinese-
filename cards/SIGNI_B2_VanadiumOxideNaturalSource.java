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

public final class SIGNI_B2_VanadiumOxideNaturalSource extends Card {
    
    public SIGNI_B2_VanadiumOxideNaturalSource()
    {
        setImageSets("WXDi-P08-066");
        
        setOriginalName("羅原　ＶＯ");
        setAltNames("ラゲンサンカバナジウム Ragen Sanka Banajiumu VO");
        setDescription("jp",
                "@U：あなたのターン終了時、対戦相手のシグニ１体を対象とし、それらバニッシュする。" +
                "~#：あなたの手札が４枚以上ある場合、対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "VO, Natural Element");
        setDescription("en",
                "@U: At the end of your turn, vanish target SIGNI on your opponent's field." +
                "~#If you have four or more cards in your hand, vanish target upped SIGNI on your opponent's field. "
        );
        
        setName("en_fan", "Vanadium Oxide, Natural Source");
        setDescription("en_fan",
                "@U: At the end of your turn, target 1 of your opponent's SIGNI, and banish it." +
                "~#If there are 4 or more cards in your hand, target 1 of your opponent's upped SIGNI, and banish it."
        );
        
		setName("zh_simplified", "罗原 VO");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，对战对手的精灵1只作为对象，将其破坏。" +
                "~#你的手牌在4张以上的场合，对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(2);
        setPower(8000);
        
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
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        
        private void onLifeBurstEff()
        {
            if(getHandCount(getOwner()) >= 4)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            }
        }
    }
}
