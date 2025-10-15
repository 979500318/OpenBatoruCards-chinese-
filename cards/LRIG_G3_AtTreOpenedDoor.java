package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
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
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.CardDataImageSet.Mask;

import java.util.List;

public final class LRIG_G3_AtTreOpenedDoor extends Card {
    
    public LRIG_G3_AtTreOpenedDoor()
    {
        setImageSets("WXDi-P04-028", Mask.IGNORE+"WXDi-P115");
        
        setOriginalName("開けし扉　アト＝トレ");
        setAltNames("ヒラけしトビラアトトレ Hirakeshi Tobira Ato Tore");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000する。\n" +
                "@E：あなたのトラッシュから#Gを持たないシグニを２枚まで対象とし、それらをエナゾーンに置く。\n" +
                "@A $G1 @[エナゾーンからそれぞれ共通するクラスを持たないシグニ７枚をトラッシュに置く]@：対戦相手のすべてのシグニをエナゾーンに置き、[[エナチャージ２]]をする。"
        );
        
        setName("en", "At =Tre=, the Opened Door");
        setDescription("en",
                "@U: At the beginning of your attack phase, target SIGNI on your field gets +5000 power until end of turn.\n" +
                "@E: Put up to two target SIGNI without a #G from your trash to your Ener Zone.\n" +
                "@A $G1 @[Put seven SIGNI that do not share a class from your Ener Zone into your trash]@: Put all SIGNI on your opponent's field into their owner's Ener Zone and [[Ener Charge 2]]."
        );
        
        setName("en_fan", "At-Tre, Opened Door");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your SIGNI, and until end of turn, it gets +5000 power.\n" +
                "@E: Target up to 2 SIGNI without #G @[Guard]@ from your trash, and put them into the ener zone.\n" +
                "@A $G1 @[Put 7 SIGNI that do not share a common class from the ener zone into the trash]@: Put all of your opponent's SIGNI into the ener zone, and [[Ener Charge 2]]."
        );
        
		setName("zh_simplified", "开启扉 亚特=TRE");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的精灵1只作为对象，直到回合结束时为止，其的力量+5000。\n" +
                "@E 从你的废弃区把不持有#G的精灵2张最多作为对象，将这些放置到能量区。\n" +
                "@A $G1 从能量区把不持有共通类别的精灵7张放置到废弃区:对战对手的全部的精灵放置到能量区，[[能量填充2]]。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AT);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);
        
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
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new TrashCost(7, new TargetFilter().SIGNI().fromEner(), this::onActionEffCostCond), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 5000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ENER).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
            putInEner(data);
        }
        
        private ConditionState onActionEffCond()
        {
            return CardAbilities.getUniqueSIGNIClasses(getCardsInEner(getOwner())).size() >= 7 ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onActionEffCostCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 7 && CardAbilities.getUniqueSIGNIClasses(new DataTable<>(listPickedCards)).size() >= 7;
        }
        private void onActionEff()
        {
            putInEner(getSIGNIOnField(getOpponent()));
            
            enerCharge(2);
        }
    }
}
