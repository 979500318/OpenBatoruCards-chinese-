package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class LRIGA_W1_MCLIONStandUp extends Card {
    
    public LRIGA_W1_MCLIONStandUp()
    {
        setImageSets("WXDi-P02-018");
        
        setOriginalName("MC.LION-STANDUP");
        setAltNames("エムシーリオンスタンドアップ Emu Shii Rion Sutando Appu");
        setDescription("jp",
                "@E：あなたのレベルの１のシグニ１体を対象とし、ターン終了時まで、それは@>@U $T1：このシグニがバトルによって対戦相手のシグニをバニッシュしたとき、このシグニをアップする。@@を得る。"
        );
        
        setName("en", "MC LION - STANDUP");
        setDescription("en",
                "@E: Target level one SIGNI on your field gains@>@U $T1: When this SIGNI vanishes a SIGNI on your opponent's field through battle, up this SIGNI.@@until end of turn."
        );
        
        setName("en_fan", "MC.LION - STANDUP");
        setDescription("en_fan",
                "@E: Target 1 of your level 1 SIGNI, and until end of turn, it gains:" +
                "@>@U $T1: When this SIGNI banishes an opponent's SIGNI in battle, up this SIGNI."
        );
        
		setName("zh_simplified", "MC.LION-STANDUP");
        setDescription("zh_simplified", 
                "@E :你的等级1的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U $T1 :当这只精灵因为战斗把对战对手的精灵破坏时，这只精灵竖直。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withLevel(1)).get();
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.BANISH, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceAbility() == null &&
                   getEvent().getSourceCardIndex() == getAbility().getSourceCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            up();
        }
    }
}
