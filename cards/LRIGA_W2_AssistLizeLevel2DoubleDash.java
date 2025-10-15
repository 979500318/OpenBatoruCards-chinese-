package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class LRIGA_W2_AssistLizeLevel2DoubleDash extends Card {
    
    public LRIGA_W2_AssistLizeLevel2DoubleDash()
    {
        setImageSets("WXDi-P00-026");
        
        setOriginalName("【アシスト】リゼ　レベル２’’");
        setAltNames("アシストリゼレベルニダブルダッシュ Ashisuto Rize Reberu Ni Daburu Dasshu Double Dash Assist Lize");
        setDescription("jp",
                "@E：あなたの＜さんばか＞のルリグ１体を対象とし、ターン終了時まで、それは" +
                "@>@U $T1：このルリグがアタックしたとき、このルリグをアップする。@@を得る。"
        );
        
        setName("en", "[Assist] Lize, Level 2''");
        setDescription("en",
                "@E: Target <<Sanbaka>> LRIG on your field gains" +
                "@>@U $T1: When this LRIG attacks, up this LRIG.@@until end of turn."
        );
        
        setName("en_fan", "[Assist] Lize Level 2''");
        setDescription("en_fan",
                "@E: Target 1 of your <<Sanbaka>> LRIGs, and until end of turn, it gains:" +
                "@>@U $T1: When this LRIG attacks, up this LRIG.@@"
        );
        
		setName("zh_simplified", "【支援】莉泽 等级2''");
        setDescription("zh_simplified", 
                "@E :你的<<さんばか>>分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U $T1 :当这只分身攻击时，这只分身竖直。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LIZE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().anyLRIG().withLRIGTeam(CardLRIGTeam.SANBAKA)).get();
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            up();
        }
    }
}
