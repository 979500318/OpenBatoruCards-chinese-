package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class LRIGA_W2_MCLIONDrop extends Card {
    
    public LRIGA_W2_MCLIONDrop()
    {
        setImageSets("WXDi-P03-018");
        
        setOriginalName("MC.LION-DROP");
        setAltNames("エムシーリオンドロップ Emu Shii Rion Doroppu");
        setDescription("jp",
                "@E：あなたのシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは@>@C：対戦相手のターンの間、バニッシュされない。@@を得る。\n" +
                "@E %X %X：対戦相手のシグニ１体を対象とし、それをトラッシュに置く。"
        );
        
        setName("en", "MC LION - DROP");
        setDescription("en",
                "@E: Target SIGNI on your field gains@>@C: During your opponent's turn, this SIGNI cannot be vanished.@@until the end of your opponent's next end phase.\n" +
                "@E %X %X: Put target SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "MC.LION - DROP");
        setDescription("en_fan",
                "@E: Target 1 of your SIGNI, and until the end of your opponent's next turn, it gains:" +
                "@>@C: During your opponent's turn, this SIGNI can't be banished.@@" +
                "@E %X %X: Target 1 of your opponent's SIGNI, and put it into the trash."
        );
        
		setName("zh_simplified", "MC.LION-DROP");
        setDescription("zh_simplified", 
                "@E :你的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到\n" +
                "@>@C :对战对手的回合期间，不会被破坏。@@\n" +
                "@E %X %X:对战对手的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            
            if(target != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, data -> {
                    return !data.getCardIndex().getIndexedInstance().isOwnTurn() ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
                }));
                
                attachAbility(target, attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            trash(target);
        }
    }
}
