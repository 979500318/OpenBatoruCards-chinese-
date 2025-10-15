package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_DeusLimited extends Card {
    
    public LRIGA_K2_DeusLimited()
    {
        setImageSets("WXDi-P04-023");
        
        setOriginalName("デウスリミテッド");
        setAltNames("Deusu Rimiteddo");
        setDescription("jp",
                "@E：このターン、対戦相手はシグニで合計一度しかアタックできない。\n" +
                "@E %X %X：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Deus Limited");
        setDescription("en",
                "@E: Your opponent can only attack with SIGNI once this turn.\n" +
                "@E %X %X: Add target SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "Deus Limited");
        setDescription("en_fan",
                "@E: This turn, your opponent can only attack with a total of 1 SIGNI.\n" +
                "@E %X %X: Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "迪乌斯界限");
        setDescription("zh_simplified", 
                "@E :这个回合，对战对手的精灵只能合计一次攻击。\n" +
                "@E %X %X从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.DEUS);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_ATTACK, getOpponent(), ChronoDuration.turnEnd(), data -> {
                return CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) &&
                       GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ATTACK && event.getCaller().getOwner() == data.getPlayerRole() &&
                        CardType.isSIGNI(event.getCaller().getCardReference().getType())) > 0 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
            });
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            addToHand(target);
        }
    }
}
