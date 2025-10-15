package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G3_GaiaVerdantAngelPrincess extends Card {
    
    public SIGNI_G3_GaiaVerdantAngelPrincess()
    {
        setImageSets("WXDi-P05-040");
        
        setOriginalName("翠天姫　ガイア");
        setAltNames("スイテンキガイア Suitenki Gaia");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に＜天使＞のシグニが３体以上ある場合、[[エナチャージ１]]をする。\n" +
                "@U：あなたのターン終了時、あなたのエナゾーンから＜天使＞のシグニを１枚まで対象とし、それを手札に加える。\n" +
                "@U：あなたのターン終了時、あなたのトラッシュから＜天使＞のシグニ１枚を対象とし、%X %Xを支払ってもよい。そうした場合、それを場に出す。それの@E能力は発動しない。"
        );
        
        setName("en", "Gaia, Jade Angel Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are three or more <<Angel>> SIGNI on your field, [[Ener Charge 1]].\n" +
                "@U: At the end of your turn, add up to one target <<Angel>> SIGNI from your Ener Zone to your hand.\n" +
                "@U: At the end of your turn, you may pay %X %X. If you do, put target <<Angel>> SIGNI from your trash onto your field. The @E abilities of SIGNI put onto your field this way do not activate."
        );
        
        setName("en_fan", "Gaia, Verdant Angel Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 3 or more <<Angel>> SIGNI on your field, [[Ener Charge 1]].\n" +
                "@U: At the end of your turn, target up to 1 <<Angel>> SIGNI from your ener zone, and add it to your hand.\n" +
                "@U: At the end of your turn, you may pay %X %X. If you do, target 1 <<Angel>> SIGNI from your trash, and put it onto the field. Its @E abilities don't activate."
        );
        
		setName("zh_simplified", "翠天姬 盖亚");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的<<天使>>精灵在3只以上的场合，[[能量填充1]]。\n" +
                "@U :你的回合结束时，从你的能量区把<<天使>>精灵1张最多作为对象，将其加入手牌。\n" +
                "@U :你的回合结束时，从你的废弃区把<<天使>>精灵1张作为对象，可以支付%X %X。这样做的场合，将其出场。其的@E能力不能发动。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            
            AutoAbility auto3 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff3);
            auto3.setCondition(this::onAutoEff3Cond);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).getValidTargetsCount() >= 3)
            {
                enerCharge(1);
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromEner()).get();
            addToHand(target);
        }
        
        private ConditionState onAutoEff3Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff3(CardIndex caller)
        {
            if(payEner(Cost.colorless(2)))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash().playable()).get();
                
                if(target != null)
                {
                    putOnField(target, Enter.DONT_ACTIVATE);
                }
            }
        }
    }
}
