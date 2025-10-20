package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_K3_CodeAncientsSteampunk extends Card {
    
    public SIGNI_K3_CodeAncientsSteampunk()
    {
        setImageSets("WXDi-P04-042");
        
        setOriginalName("コードアンシエンツ　スチームパンク");
        setAltNames("コードアンシエンツスチームパンク Koodo Anshientsu Suchiimupanku");
        setDescription("jp",
                "@U $T1：あなたのターンの間、あなたのルリグ１体の下からカード１枚が移動したとき、対戦相手のシグニを１体を対象とし、%Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－8000する。\n\n" +
                "@A @[アップ状態のレベル２のルリグ２体をダウンする]@：このカードをトラッシュから場に出す。"
        );
        
        setName("en", "Steampunk, Code: Ancients");
        setDescription("en",
                "@U $T1: During your turn, when a card underneath your LRIG moves, you may pay %X. If you do, target SIGNI on your opponent's field gets --8000 power until end of turn.\n\n" +
                "@A @[Down two upped level two LRIG]@: Put this card from your trash onto your field."
        );
        
        setName("en_fan", "Code Ancients Steampunk");
        setDescription("en_fan",
                "@U $T1: During your turn, when a card under 1 of your LRIGs moves, target 1 of your opponent's SIGNI, and you may pay %X. If you do, until end of turn, it gets --8000 power.\n\n" +
                "@A @[Down 2 of your upped level 2 LRIGs]@: Put this card from your trash onto the field."
        );
        
		setName("zh_simplified", "古神代号 蒸汽朋克");
        setDescription("zh_simplified", 
                "@U $T1 :你的回合期间，当从你的分身1只的下面把1张牌移动时，对战对手的精灵1只作为对象，可以支付%X。这样做的场合，直到回合结束时为止，其的力量-8000。\n" +
                "@A 竖直状态的等级2的分身2只横置:这张牌从废弃区出场。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE_UNDER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act = registerActionAbility(new DownCost(2, new TargetFilter().own().anyLRIG().withLevel(2).upped()), this::onActionEff);
            act.setCondition(this::onActionCondition);
            act.setActiveLocation(CardLocation.TRASH);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardLocation.isLRIG(caller.getLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                gainPower(target, -8000, ChronoDuration.turnEnd());
            }
        }
        
        private ConditionState onActionCondition()
        {
            return isPlayable() ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH)
            {
                putOnField(getCardIndex());
            }
        }
    }
}
