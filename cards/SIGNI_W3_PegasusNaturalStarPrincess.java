package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_W3_PegasusNaturalStarPrincess extends Card {
    
    public SIGNI_W3_PegasusNaturalStarPrincess()
    {
        setImageSets("WXDi-P07-040");
        
        setOriginalName("羅星姫　ペガサス");
        setAltNames("ラセイキペガサス Raseiki Pegasasu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にレベル１のシグニがある場合、カードを１枚引く。\n" +
                "@U：このシグニがアタックしたとき、あなたのトラッシュにレベル１のシグニが１０枚以上ある場合、【エナチャージ１】をする。\n" +
                "@A @[エナゾーンからレベル１のシグニ４枚をトラッシュに置く]@：対戦相手のシグニ１体を対象とし、それをトラッシュに置く。"
        );
        
        setName("en", "Pegasus, Natural Planet Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is a level one SIGNI on your field, draw a card.\n" +
                "@U: Whenever this SIGNI attacks, if there are ten or more level one SIGNI in your trash, [[Ener Charge 1]].\n" +
                "@A @[Put four level one SIGNI from your Ener Zone into your trash]@: Put target SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "Pegasus, Natural Star Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there is a level 1 SIGNI on your field, draw 1 card.\n" +
                "@U: Whenever this SIGNI attacks, if there are 10 or more level 1 SIGNI in your trash, [[Ener Charge 1]].\n" +
                "@A @[Put 4 level 1 SIGNI from your ener zone into the trash]@: Target 1 of your opponent's SIGNI, and put it into the trash."
        );
        
		setName("zh_simplified", "罗星姬 飞马座");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有等级1的精灵的场合，抽1张牌。\n" +
                "@U :当这只精灵攻击时，你的废弃区的等级1的精灵在10张以上的场合，[[能量填充1]]。\n" +
                "@A 从能量区把等级1的精灵4张放置到废弃区:对战对手的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            
            registerActionAbility(new TrashCost(4, new TargetFilter().SIGNI().withLevel(1).fromEner()), this::onActionEff);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withLevel(1).getValidTargetsCount() > 0)
            {
                draw(1);
            }
        }
        
        private void onAutoEff2()
        {
            if(new TargetFilter().own().SIGNI().withLevel(1).fromTrash().getValidTargetsCount() >= 10)
            {
                enerCharge(1);
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            trash(target);
        }
    }
}
