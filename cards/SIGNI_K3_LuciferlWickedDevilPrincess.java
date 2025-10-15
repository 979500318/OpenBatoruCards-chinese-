package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K3_LuciferlWickedDevilPrincess extends Card {
    
    public SIGNI_K3_LuciferlWickedDevilPrincess()
    {
        setImageSets("WXDi-P06-041");
        
        setOriginalName("凶魔姫　ルシファル");
        setAltNames("キョウマキルシファル Kyoumaki Rushifaru");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたの場にいる黒のルリグ１体につき－2000する。この効果によってそれのパワーが０以下になった場合、対戦相手のデッキの上からカードを２枚トラッシュに置く。\n" +
                "@A $T1 @[手札を２枚捨てる]@：あなたのトラッシュから黒のシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Lucifer, Doomed Evil Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, target SIGNI on your opponent's field gets --2000 power for each black LRIG on your field until end of turn. If its power becomes to 0 or less by this effect, put the top two cards of your opponent's deck into their trash.\n" +
                "@A $T1 @[Discard two cards]@: Add target black SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Luciferl, Wicked Devil Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power for each black LRIG on your field. If its power becomes 0 or less by this effect, put the top 2 cards of your opponent's deck into the trash.\n" +
                "@A $T1 @[Discard 2 cards from your hand]@: Target 1 black SIGNI from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "凶魔姬 路西法");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的场上的黑色的分身的数量，每有1只就-2000。因为这个效果将其的力量在0以下的场合，从对战对手的牌组上面把2张牌放置到废弃区。\n" +
                "@A $T1 手牌2张舍弃:从你的废弃区把黑色的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new DiscardCost(2), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                gainPower(target, -2000 * new TargetFilter().own().anyLRIG().withColor(CardColor.BLACK).getValidTargetsCount(), ChronoDuration.turnEnd());
                
                if(target.getIndexedInstance().getPower().getValue() <= 0)
                {
                    millDeck(getOpponent(), 2);
                }
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash()).get();
            addToHand(target);
        }
    }
}
