package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class LRIGA_G2_SangaShake extends Card {
    
    public LRIGA_G2_SangaShake()
    {
        setImageSets("WXDi-D06-010");
        
        setOriginalName("サンガ／／シェイク");
        setAltNames("サンガシェイク Sanga Sheiku");
        setDescription("jp",
                "@E：ターン終了時まで、このルリグは@>@U $T2：対戦相手のシグニかルリグがアタックしたとき、そのアタックを無効にする。@@を得る。"
        );
        
        setName("en", "Sanga//Shake");
        setDescription("en",
                "@E: This LRIG gains @>@U $T2: Whenever a SIGNI or LRIG on your opponent's field attacks, negate that attack.@@until end of turn."
        );
        
        setName("en_fan", "Sanga//Shake");
        setDescription("en_fan",
                "@E: Until end of turn, this LRIG gains:" +
                "@>@U $T2: Whenever your opponent's SIGNI or LRIG attacks, disable that attack."
        );
        
		setName("zh_simplified", "山河//抖动");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，这只分身得到\n" +
                "@>@U $T2 :当对战对手的精灵或分身攻击时，那次攻击无效。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.SANGA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.GREEN);
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
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.setUseLimit(UseLimit.TURN, 2);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex cardIndex)
        {
            return !isOwnCard(cardIndex) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex cardIndex)
        {
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_BE_ATTACKED, getOwner(), record, data -> {
                record.forceExpire();
                
                return RuleCheckState.BLOCK;
            });
        }
    }
}
