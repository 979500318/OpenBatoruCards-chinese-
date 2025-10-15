package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B1_BradamanteAzureGeneral extends Card {
    
    public SIGNI_B1_BradamanteAzureGeneral()
    {
        setImageSets("WXDi-P01-062");
        
        setOriginalName("蒼将　ブラダマンテ");
        setAltNames("ソウショウブラダマンテ Soushou Buradamante");
        setDescription("jp",
                "@E：対戦相手の凍結状態のシグニ１体を対象とし、ターン終了時まで、それは@>@U：ターン終了時、このシグニをバニッシュする。そうした場合、あなたは手札を１枚捨てる。@@を得る。"
        );
        
        setName("en", "Bradamante, Azure General");
        setDescription("en",
                "@E: Target frozen SIGNI on your opponent's field gains@>@U: At end of turn, vanish this SIGNI. If you do, discard a card.@@until end of turn."
        );
        
        setName("en_fan", "Bradamante, Azure General");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's frozen SIGNI, and until end of turn, it gains:" +
                "@>@U: At the end of the turn, banish this SIGNI. If you do, discard 1 card from your hand."
        );
        
		setName("zh_simplified", "苍将 布拉达曼特");
        setDescription("zh_simplified", 
                "@E :对战对手的冻结状态的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U :回合结束时，这只精灵破坏。这样做的场合，你把手牌1张舍弃。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI().withState(CardStateFlag.FROZEN)).get();
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            if(banish(getAbility().getSourceCardIndex()))
            {
                getAbility().getSourceCardIndex().getIndexedInstance().discard(1);
            }
        }
    }
}
