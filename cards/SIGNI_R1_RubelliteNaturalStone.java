package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventTarget;

public final class SIGNI_R1_RubelliteNaturalStone extends Card {
    
    public SIGNI_R1_RubelliteNaturalStone()
    {
        setImageSets("WXDi-P03-056");
        
        setOriginalName("羅石　ルベライト");
        setAltNames("ラセキルベライト Raseki Ruberaito");
        setDescription("jp",
                "@U $T1：このシグニが対戦相手のシグニの、能力か効果の対象になったとき、その対戦相手のシグニをバニッシュする。"
        );
        
        setName("en", "Rubellite, Natural Crystal");
        setDescription("en",
                "@U $T1: When this SIGNI becomes the target of an ability or effect of your opponent's SIGNI, vanish that SIGNI."
        );
        
        setName("en_fan", "Rubellite, Natural Stone");
        setDescription("en_fan",
                "@U $T1: When this SIGNI is targeted by the ability or effect of your opponent's SIGNI, banish that opponent's SIGNI."
        );
        
		setName("zh_simplified", "罗石 红碧玺");
        setDescription("zh_simplified", 
                "@U $T1 :当这只精灵被作为对战对手的精灵的，能力或效果的对象时，那只对战对手的精灵破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.TARGET, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getEvent().getSourceAbility() != null &&
                   !isOwnCard(getEvent().getSourceCardIndex()) && CardType.isSIGNI(getEvent().getSourceCardIndex().getCardReference().getType()) &&
                   EventTarget.getDataSourceTargetRole() != getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            banish(getEvent().getSourceCardIndex());
        }
    }
}
