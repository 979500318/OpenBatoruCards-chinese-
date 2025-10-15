package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventTarget;

public final class SIGNI_B2_ApatiteNaturalStone extends Card {
    
    public SIGNI_B2_ApatiteNaturalStone()
    {
        setImageSets("WXDi-P03-067", "SPDi38-03");
        
        setOriginalName("羅石　アパタイト");
        setAltNames("ラセキアパタイト Raseki Apataito");
        setDescription("jp",
                "@U $T1：このシグニが対戦相手の、能力か効果の対象になったとき、カードを１枚引く。"
        );
        
        setName("en", "Apatite, Natural Crystal");
        setDescription("en",
                "@U $T1: When this SIGNI becomes the target of an ability or effect of your opponent, draw a card."
        );
        
        setName("en_fan", "Apatite, Natural Stone");
        setDescription("en_fan",
                "@U $T1: When this SIGNI is targeted by your opponent's ability or effect, draw 1 card."
        );
        
		setName("zh_simplified", "罗石 磷灰石");
        setDescription("zh_simplified", 
                "@U $T1 :当这只精灵被作为对战对手的，能力或效果的对象时，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.TARGET, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                   EventTarget.getDataSourceTargetRole() != getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            draw(1);
        }
    }
}
