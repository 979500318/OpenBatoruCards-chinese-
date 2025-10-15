package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B3_AmabieAzureAngelPrincess extends Card {
    
    public SIGNI_B3_AmabieAzureAngelPrincess()
    {
        setImageSets("WXDi-P02-040");
        
        setOriginalName("蒼天姫　アマビエ");
        setAltNames("ソウテンキアマビエ Soutenki Amabie");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に＜天使＞のシグニが３体以上ある場合、カードを１枚引く。\n" +
                "@U：：このシグニがアタックしたとき、あなたのトラッシュに＜天使＞のシグニが１５枚以上ある場合、対戦相手のルリグ１体を対象とし、%B %Xを支払ってもよい。そうした場合、それを凍結する。" +
                "~#：あなたのトラッシュから、対象のレベル２の＜天使＞のシグニ１枚を手札に加えて対象のレベル１の＜天使＞のシグニ１枚を場に出す。"
        );
        
        setName("en", "Amabie, Azure Angel Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are three or more <<Angel>> SIGNI on your field, draw a card.\n" +
                "@U: Whenever this SIGNI attacks, if there are fifteen or more <<Angel>> SIGNI in your trash, you may pay %B %X. If you do, freeze target LRIG on your opponent's field." +
                "~#Add target level two <<Angel>> SIGNI from your trash to your hand. Put target level one <<Angel>> SIGNI from your trash onto your field."
        );
        
        setName("en_fan", "Amabie, Azure Angel Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 3 or more <<Angel>> SIGNI on your field, draw 1 card.\n" +
                "@U: Whenever this SIGNI attacks, if there are 15 or more <<Angel>> SIGNI in your trash, target 1 of your opponent's LRIGs, and you may pay %B %X. If you do, freeze it." +
                "~#From your trash, add 1 target level 2 <<Angel>> SIGNI to your hand and put 1 target level 1 <<Angel>> SIGNI onto the field."
        );
        
		setName("zh_simplified", "苍天姬 阿玛比埃");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的<<天使>>精灵在3只以上的场合，抽1张牌。\n" +
                "@U :当这只精灵攻击时，你的废弃区的<<天使>>精灵在15张以上的场合，对战对手的分身1只作为对象，可以支付%B%X。这样做的场合，将其冻结。" +
                "~#从你的废弃区把，对象的等级2的<<天使>>精灵1张加入手牌，对象的等级1的<<天使>>精灵1张出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).getValidTargetsCount() >= 3)
            {
                draw(1);
            }
        }
        
        private void onAutoEff2()
        {
            if(new TargetFilter().own().SIGNI().fromTrash().withClass(CardSIGNIClass.ANGEL).getValidTargetsCount() >= 15)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1)))
                {
                    freeze(target);
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withLevel(2).withClass(CardSIGNIClass.ANGEL).fromTrash()).get();
            addToHand(target);
            
            target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withLevel(1).withClass(CardSIGNIClass.ANGEL).playable().fromTrash()).get();
            putOnField(target);
        }
    }
}
