package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.events.EventTarget;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B2_ReiMemoriaNaturalStone extends Card {
    
    public SIGNI_B2_ReiMemoriaNaturalStone()
    {
        setImageSets("WXDi-P08-065", "WXDi-P08-065P", "SPDi01-79");
        
        setOriginalName("羅石　レイ//メモリア");
        setAltNames("ラセキレイメモリア Raseki Rei Memoria");
        setDescription("jp",
                "@C：あなたの他の、赤のシグニと＜宝石＞のシグニのパワーを＋2000する。\n" +
                "@U $T1：あなたの他の＜宝石＞のシグニ１体が対戦相手のシグニの、@E能力か@E能力の効果の対象になったとき、対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "Rei//Memoria, Natural Crystal");
        setDescription("en",
                "@C: Other red SIGNI and <<Jewel>> SIGNI on your field get +2000 power.\n" +
                "@U $T1: When a <<Jewel>> SIGNI on your field becomes the target of an @E ability or @E effect of your opponent's SIGNI, your opponent discards a card."
        );
        
        setName("en_fan", "Rei//Memoria, Natural Stone");
        setDescription("en_fan",
                "@C: Your other red SIGNI and <<Gem>> SIGNI get +2000 power.\n" +
                "@U $T1: When 1 of your <<Gem>> SIGNI is targeted by the @E ability or the effect of the @E ability of your opponent's SIGNI, your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "罗石 令//回忆");
        setDescription("zh_simplified", 
                "@C :你的其他的，红色的精灵和<<宝石>>精灵的力量+2000。\n" +
                "@U $T1 当你的<<宝石>>精灵1只被作为对战对手的精灵的，@E能力或@E能力的效果的对象时，对战对手把手牌1张舍弃。\n"
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
            
            registerConstantAbility(new TargetFilter().own().SIGNI().or(new TargetFilter().withColor(CardColor.RED), new TargetFilter().withClass(CardSIGNIClass.GEM)).except(cardId), new PowerModifier(2000));
            
            AutoAbility auto = registerAutoAbility(GameEventId.TARGET, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                   getEvent().getSourceAbility() instanceof EnterAbility && CardType.isSIGNI(getEvent().getSourceCardIndex().getCardReference().getType()) &&
                   isOwnCard(caller) && CardLocation.isSIGNI(caller.getLocation()) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.GEM) &&
                   EventTarget.getDataSourceTargetRole() != caller.getIndexedInstance().getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }
    }
}
