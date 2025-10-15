package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class SIGNI_R3_CodeAccelGullWing extends Card {
    
    public SIGNI_R3_CodeAccelGullWing()
    {
        setImageSets("WXDi-D07-017");
        
        setOriginalName("コードアクセル　ガルウィング");
        setAltNames("コードアクセルガルウィング Koodo Akuseru Garu Uingu Gull Wing");
        setDescription("jp",
                "=T ＜デウス・エクス・マキナ＞\n" +
                "^U：あなたのアタックフェイズ開始時、対戦相手のエナゾーンにカードが２枚以上ありこのターンにあなたがカードを１枚以上捨てていた場合、対戦相手は自分のエナゾーンからカードを１枚選びトラッシュに置く。\n" +
                "@A %R %R %X：ターン終了時まで、このシグニは[[ダブルクラッシュ]]を得る。"
        );
        
        setName("en", "Gull - Wing, Code: Accel");
        setDescription("en",
                "=T <<Deus Ex Machina>>\n" +
                "^U: At the beginning of your attack phase, if there are two or more cards in your opponent's Ener Zone and you discarded one or more cards this turn, your opponent chooses a card from their Ener Zone and puts it into their trash.\n" +
                "@A %R %R %X: This SIGNI gains [[Double Crush]] until end of turn. "
        );
        
        setName("en_fan", "Code Accel Gull-Wing");
        setDescription("en_fan",
                "=T <<Deus Ex Machina>>\n" +
                "^U: At the beginning of your attack phase, if there are 2 or more cards in your opponent's ener zone and you discarded 1 or more cards this turn, your opponent puts 1 card from their ener zone into the trash.\n" +
                "@A %R %R %X: Until end of turn, this SIGNI gains [[Double Crush]]."
        );
        
		setName("zh_simplified", "加速代号 鸥翼式");
        setDescription("zh_simplified", 
                "=T<<デウス・エクス・マキナ>>\n" +
                "^U:你的攻击阶段开始时，对战对手的能量区的牌在2张以上且这个回合你把牌1张以上舍弃过的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@A %R %R%X:直到回合结束时为止，这只精灵得到[[双重击溃]]。（因为攻击给予伤害，把生命护甲2张击溃）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
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
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 2) + Cost.colorless(1)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA) &&
                   isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getEnerCount(getOpponent()) >= 2 &&
               GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.DISCARD && isOwnCard(event.getCaller())) > 0)
            {
                CardIndex target = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(target);
            }
        }

        private ConditionState onActionEffCond()
        {
            return getCardIndex().getIndexedInstance().getAbilityList().stream().anyMatch(ability -> ability.getSourceStockAbility() instanceof StockAbilityDoubleCrush) ? ConditionState.WARN : ConditionState.OK;
        }
        private void onActionEff()
        {
            attachAbility(getCardIndex(), new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
        }
    }
}
