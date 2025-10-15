package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.CostRuleCheck;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class PIECE_R_EntwinedSupremacy extends Card {
    
    public PIECE_R_EntwinedSupremacy()
    {
        setImageSets("WXDi-P06-001");
        
        setOriginalName("一覇一絡");
        setAltNames("イッパヒトカラゲ Ippa Hitokarage");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "あなたのレベル３のルリグ１体を対象とし、ターン終了時まで、それは以下の能力を得る。" +
                "@>@U $T1：このルリグがアタックしたとき、対戦相手のライフクロス１枚をクラッシュする。対戦相手がそのカードのライフバーストを使用することを選んだ場合、%X %Xを支払ってもよい。そうした場合、そのカードのライフバーストは発動しない。"
        );
        
        setName("en", "Entwined Supremacy");
        setDescription("en",
                "\n" +
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "Target level three LRIG on your field gains the following ability until end of turn.\n" +
                "@>@U $T1: When this LRIG attacks, crush one of your opponent's Life Cloth. If your opponent chooses to use the Life Burst of that card, you may pay %X %X. If you do, that card's Life Burst does not activate."
        );
        
        setName("en_fan", "Entwined Supremacy");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "Target 1 of your level 3 LRIG, and until end of turn, it gains:" +
                "@>@U $T1: Whenever this LRIG attacks, crush 1 of your opponent's life cloth. If your opponent chooses to use that card's ## @[Life Burst]@, you may pay %X %X. If you do, that card's ## @[Life Burst]@ doesn't activate."
        );
        
		setName("zh_simplified", "一霸一络");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "你的等级3的分身1只作为对象，直到回合结束时为止，其得到以下的能力。\n" +
                "@>@U $T1 :当这只分身攻击时，对战对手的生命护甲1张击溃。对战对手选把那张牌的生命迸发使用的场合，可以支付%X %X。这样做的场合，那张牌的生命迸发不能发动。@@\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            piece = registerPieceAbility(this::onPieceEffPreTarget, this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            if(CardAbilities.getColorsCount(getLRIGs(getOwner())) < 3) return ConditionState.BAD;
            
            return new TargetFilter().own().LRIG().withLevel(3).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter().own().LRIG().withLevel(3)));
        }
        private void onPieceEff()
        {
            if(piece.getTarget() != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                
                attachAbility(piece.getTarget(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex cardIndexLC = crush(getOpponent()).get();
            
            if(cardIndexLC != null)
            {
                ChronoRecord record = new ChronoRecord(ChronoDuration.nextPhase());
                addPlayerRuleCheck(PlayerRuleCheckType.COST_TO_DISABLE_LB, getOwner(), record, data -> {
                    if(CostRuleCheck.getCardIndex(data) != cardIndexLC) return null;
                    
                    record.forceExpire();
                    
                    return new EnerCost(Cost.colorless(2));
                });
            }
        }
    }
}
