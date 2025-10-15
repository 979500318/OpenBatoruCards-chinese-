package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.VirusCost;

public final class LRIGA_K2_NanashiSelecting extends Card {
    
    public LRIGA_K2_NanashiSelecting()
    {
        setImageSets("WXDi-P07-038");
        
        setOriginalName("ナナシ・ご選択");
        setAltNames("ナナシゴセンタク Nanashi Go Sentaku");
        setDescription("jp",
                "@A $T1 @[対戦相手の場にある【ウィルス】１つを取り除く]@：以下の３つから１つを選ぶ。\n" +
                "$$1あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。\n" +
                "$$2対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。\n" +
                "$$3【エナチャージ２】"
        );
        
        setName("en", "Nanashi Selection");
        setDescription("en",
                "@A $T1 @[Eliminate one [[Virus]] on your opponent's field]@: Choose one of the following.\n" +
                "$$1 Add target SIGNI without a #G from your trash to your hand.\n" +
                "$$2 Target SIGNI on your opponent's field gets --10000 power until end of turn.\n" +
                "$$3 [[Ener Charge 2]]."
        );
        
        setName("en_fan", "Nanashi Selecting");
        setDescription("en_fan",
                "@A $T1 @[Remove 1 [[Virus]] from your opponent's field]@: @[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power.\n" +
                "$$3 [[Ener Charge 2]]"
        );
        
		setName("zh_simplified", "无名·选择");
        setDescription("zh_simplified", 
                "@A $T1 对战对手的场上的[[病毒]]1个移除:从以下的3种选1种。\n" +
                "$$1 从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。\n" +
                "$$2 对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n" +
                "$$3 [[能量填充2]]\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.NANASHI);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act = registerActionAbility(new VirusCost(1), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onActionEff()
        {
            switch(playerChoiceMode())
            {
                case 1<<0:
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
                    addToHand(target);
                    
                    break;
                }
                case 1<<1:
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                    gainPower(target, -10000, ChronoDuration.turnEnd());
                    
                    break;
                }
                case 1<<2:
                {
                    enerCharge(2);
                    
                    break;
                }
            }
        }
    }
}
