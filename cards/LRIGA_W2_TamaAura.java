package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class LRIGA_W2_TamaAura extends Card {
    
    public LRIGA_W2_TamaAura()
    {
        setImageSets("WXDi-P08-026");
        
        setOriginalName("タマ・おーら");
        setAltNames("タマオーラ Tama Oora");
        setDescription("jp",
                "@E：あなたのルリグ１体を対象とし、ターン終了時まで、それは@>@U $T1：このルリグがアタックしたとき、このルリグをアップし、ターン終了時まで、このルリグは能力を失う。@@を得る。"
        );
        
        setName("en", "Tama Aura");
        setDescription("en",
                "@E: Target LRIG on your field gains@>@U $T1: When this LRIG attacks, up it and it loses its abilities until end of turn.@@until end of turn."
        );
        
        setName("en_fan", "Tama Aura");
        setDescription("en_fan",
                "@E: Target your LRIG, and until end of turn, it gains:" +
                "@>@U $T1: When this LRIG attacks, up this LRIG, and until end of turn, it loses its abilities."
        );
        
		setName("zh_simplified", "小玉·圣气");
        setDescription("zh_simplified", 
                "@E :你的分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U $T1 :当这只分身攻击时，这只分身竖直，直到回合结束时为止，这只分身的能力失去。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAMA);
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
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().anyLRIG()).get();
            
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex source = getAbility().getSourceCardIndex();
            source.getIndexedInstance().up();
            source.getIndexedInstance().disableAllAbilities(source, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
    }
}
