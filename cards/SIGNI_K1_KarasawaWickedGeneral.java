package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_K1_KarasawaWickedGeneral extends Card {
    
    public SIGNI_K1_KarasawaWickedGeneral()
    {
        setImageSets("WXDi-P06-078");
        
        setOriginalName("凶将　カラサワ");
        setAltNames("キョウショウカラサワ Kyoushou Karasawa");
        setDescription("jp",
                "@U $T1：あなたのターンの間、あなたがエクシードのコストを支払ったとき、対戦相手のシグニ１体を対象とし、%Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－5000する。"
        );
        
        setName("en", "Karasawa, Doomed General");
        setDescription("en",
                "@U $T1: During your turn, when you pay an Exceed cost, you may pay %K. If you do, target SIGNI on your opponent's field gets --5000 power until end of turn."
        );
        
        setName("en_fan", "Karasawa, Wicked General");
        setDescription("en_fan",
                "@U $T1: During your turn, when you pay an @[Exceed]@ cost, target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it gets --5000 power."
        );
        
		setName("zh_simplified", "凶将 唐泽玄蕃");
        setDescription("zh_simplified", 
                "@U $T1 :你的回合期间，当你把超越的费用支付时，对战对手的精灵1只作为对象，可以支付%K。这样做的场合，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.EXCEED, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && getEvent().getSourceCost() != null && getEvent().isAtOnce(1) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
            {
                gainPower(target, -5000, ChronoDuration.turnEnd());
            }
        }
    }
}
