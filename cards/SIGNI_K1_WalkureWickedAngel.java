package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K1_WalkureWickedAngel extends Card {
    
    public SIGNI_K1_WalkureWickedAngel()
    {
        setImageSets("WXDi-P05-078");
        setLinkedImageSets("WXDi-P02-045");
        
        setOriginalName("凶天　ワルキューレ");
        setAltNames("キョウテンワルキューレ Kyouten Warukyuure Walkure");
        setDescription("jp",
                "@U $T1：あなたの《凶天姫　ヴァルキリー》１体がアタックしたとき、対戦相手のシグニ１体を対象とし、手札から＜天使＞のシグニを好きな枚数捨てる。ターン終了時まで、それのパワーをこの方法で捨てたカード１枚につき－4000する。\n" +
                "@A %K #D：あなたのトラッシュから黒の＜天使＞のシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Walkure, Doomed Angel");
        setDescription("en",
                "@U $T1: When a \"Valkyrie, Doomed Angel Queen\" on your field attacks, discard any number of <<Angel>> SIGNI. Target SIGNI on your opponent's field gets --4000 power for each card discarded this way until end of turn.\n" +
                "@A %K #D: Add target black <<Angel>> SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Walküre, Wicked Angel");
        setDescription("en_fan",
                "@U $T1: When 1 of your \"Valkyrie, Wicked Angel Princess\" attacks, target 1 of your opponent's SIGNI, and may discard any number of <<Angel>> SIGNI from your hand. If you do, until end of turn, it gets --4000 power for each card discarded this way.\n" +
                "@A %K #D: Target 1 black <<Angel>> SIGNI from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "凶天 瓦尔基丽");
        setDescription("zh_simplified", 
                "@U $T1 :当你的《凶天姫　ヴァルキリー》1只攻击时，对战对手的精灵1只作为对象，从手牌把<<天使>>精灵任意张数舍弃。直到回合结束时为止，其的力量依据这个方法舍弃的牌的数量，每有1张就-4000。\n" +
                "@A %K横置:从你的废弃区把黑色的<<天使>>精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.BLACK, 1)), new DownCost()), this::onActionEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().getName().getValue().contains("凶天姫　ヴァルキリー") ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = discard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter().SIGNI().withClass(CardSIGNIClass.ANGEL));
                
                if(data.get() != null)
                {
                    gainPower(target, -4000 * data.size(), ChronoDuration.turnEnd());
                }
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).withClass(CardSIGNIClass.ANGEL).fromTrash()).get();
            addToHand(target);
        }
    }
}
