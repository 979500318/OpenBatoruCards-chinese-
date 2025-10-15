package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R1_HeihachiroCrimsonGeneral extends Card {
    
    public SIGNI_R1_HeihachiroCrimsonGeneral()
    {
        setImageSets("WXDi-P05-054");
        
        setOriginalName("紅将　ヘイハチロウ");
        setAltNames("コウショウヘイハチロウ Koushou Heihachirou");
        setDescription("jp",
                "@C：このシグニが覚醒状態であるかぎり、このシグニのパワーは＋5000される。\n" +
                "@U：このシグニがバトルによって対戦相手のシグニ１体をバニッシュしたとき、このシグニは覚醒する。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Heihachiro, Crimson General");
        setDescription("en",
                "@C: As long as this SIGNI is awakened, it gets +5000 power.\n" +
                "@U: When this SIGNI vanishes a SIGNI on your opponent's field through battle, it is awakened. " +
                "~#Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Heihachirō, Crimson General");
        setDescription("en_fan",
                "@C: As long as this SIGNI is awakened, it gets +5000 power.\n" +
                "@U: When this SIGNI banishes an opponent's SIGNI in battle, this SIGNI awakens." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );
        
		setName("zh_simplified", "红将 东乡平八郎");
        setDescription("zh_simplified", 
                "@C :这只精灵在觉醒状态时，这只精灵的力量+5000。\n" +
                "@U :当这只精灵因为战斗把对战对手的精灵1只破坏时，这只精灵觉醒。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
            
            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return isState(CardStateFlag.AWAKENED) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceCardIndex() == getCardIndex() && getEvent().getSourceAbility() == null && !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!isState(CardStateFlag.AWAKENED)) getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
