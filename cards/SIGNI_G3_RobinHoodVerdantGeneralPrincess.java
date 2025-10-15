package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_G3_RobinHoodVerdantGeneralPrincess extends Card {
    
    public SIGNI_G3_RobinHoodVerdantGeneralPrincess()
    {
        setImageSets("WXDi-P01-040");
        
        setOriginalName("翠将姫　ロビンフッド");
        setAltNames("スイショウキロビンフッド Suishouki Robin Fuddo");
        setDescription("jp",
                "@C：あなたの場に他のシグニがないかぎり、このシグニは@>@U：このシグニがアタックしたとき、%G %Xを支払ってもよい。そうした場合、対戦相手のすべてのシグニをエナゾーンに置く。@@を得る。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたのシグニを２体まで対象とし、それらを裏向きにしてもよい。このターン終了時、この方法で裏向きにしたシグニを、同じ場所にシグニがない場合、表向きにする。"
        );
        
        setName("en", "Robin Hood, Jade General Queen");
        setDescription("en",
                "@C: As long as you have no other SIGNI on your field, this SIGNI gains@>@U: Whenever this SIGNI attacks, you may pay %G %X. If you do, put all SIGNI on your opponent's field into their Ener Zone.@@" +
                "@U: At the beginning of your attack phase, you may turn up to two target SIGNI on your field face down. At the end of this turn, if a SIGNI is not in the same position as a SIGNI turned face down this way, turn that SIGNI face up."
        );
        
        setName("en_fan", "Robin Hood, Verdant General Princess");
        setDescription("en_fan",
                "@C: As long as there are no other SIGNI on your field, this SIGNI gains:" +
                "@>@U: Whenever this SIGNI attacks, you may pay %G %X. If you do, put all of your opponent's SIGNI from the field into the ener zone.@@" +
                "@U: At the beginning of your attack phase, target up to 2 of your SIGNI, and you may turn them face down. At the end of this turn, turn the SIGNI turned face down this way face up if there is no SIGNI in the same zone."
        );
        
		setName("zh_simplified", "翠将姬 罗宾汉");
        setDescription("zh_simplified", 
                "@C :如果你的场上没有其他的精灵，那么这只精灵得到\n" +
                "@>@U :当这只精灵攻击时，可以支付%G%X。这样做的场合，对战对手的全部的精灵放置到能量区。@@\n" +
                "@U :你的攻击阶段开始时，你的精灵2只最多作为对象，可以将这些变为里向。这个回合结束时，这个方法里向的精灵在，相同场所没有精灵的场合，变为表向。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return getSIGNICount(getOwner()) == 1 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            if(payEner(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)))
            {
                putInEner(getSIGNIOnField(getOpponent()));
            }
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FLIP).own().SIGNI());
            if(data.get() != null && playerChoiceActivate())
            {
                flip(data, CardFace.BACK);
                
                callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                    flip(data, CardFace.FRONT);
                });
            }
        }
    }
}
