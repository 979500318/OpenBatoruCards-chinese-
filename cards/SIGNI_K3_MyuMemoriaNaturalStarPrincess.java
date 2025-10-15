package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_MyuMemoriaNaturalStarPrincess extends Card {
    
    public SIGNI_K3_MyuMemoriaNaturalStarPrincess()
    {
        setImageSets("WXDi-P10-043", "WXDi-P10-043P", "SPDi10-11");
        
        setOriginalName("羅星姫　ミュウ//メモリア");
        setAltNames("ラセイキミュウメモリア Raseiki Myuu Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、レベルがこのシグニの下にあるカードの枚数以下の対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %X：あなたのトラッシュから#Gを持たないレベル１のシグニ１枚を対象とし、それを手札に加える。\n" +
                "@A $T1 %K0：あなたの手札からレベル１のシグニを５枚までこのシグニの下に置く。"
        );
        
        setName("en", "Myu//Memoria, Galactic Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, vanish target SIGNI on your opponent's field with a level less than or equal to the number of cards underneath this SIGNI.\n" +
                "@E %X: Add target level one SIGNI without a #G from your trash to your hand.\n" +
                "@A $T1 %K0: Put up to five level one SIGNI from your hand under this SIGNI. "
        );
        
        setName("en_fan", "Myu//Memoria, Natural Star Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with level equal to or less than the number of cards under this SIGNI, and banish it.\n" +
                "@E %X: Target 1 level 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand.\n" +
                "@A $T1 %K0: Put up to 5 level 1 SIGNI from your hand under this SIGNI."
        );
        
		setName("zh_simplified", "罗星姬 缪//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，等级在这只精灵的下面的牌的张数以下的对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E %X从你的废弃区把不持有#G的等级1的精灵1张作为对象，将其加入手牌。\n" +
                "@A $T1 %K0:从你的手牌把等级1的精灵5张最多放置到这只精灵的下面。（表向放置）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0, new TargetFilter().own().under(getCardIndex()).getValidTargetsCount())).get();
            banish(target);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withLevel(1).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            addToHand(target);
        }
        
        private void onActionEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,5, new TargetFilter(TargetHint.UNDER).own().SIGNI().withLevel(1).fromHand());
            attach(getCardIndex(), data, CardUnderType.UNDER_GENERIC);
        }
    }
}
