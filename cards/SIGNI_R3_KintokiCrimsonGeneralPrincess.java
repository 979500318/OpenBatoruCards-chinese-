package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityHarmony;

public final class SIGNI_R3_KintokiCrimsonGeneralPrincess extends Card {
    
    public SIGNI_R3_KintokiCrimsonGeneralPrincess()
    {
        setImageSets("WXDi-P04-034");
        
        setOriginalName("紅将姫　キントキ");
        setAltNames("コウショウキキントキ Koushouki Kintoki");
        setDescription("jp",
                "=H 黒のルリグ１体\n\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの場に[[ソウル]]があり対戦相手のエナゾーンにカードが２枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びそれをトラッシュに置く。\n" +
                "@E %R %K：ターン終了時まで、このシグニは[[アサシン]]を得る。"
        );
        
        setName("en", "Kintoki, Crimson General Queen");
        setDescription("en",
                "=H One black LRIG \n" +
                "@U: At the beginning of your attack phase, if there is a [[Soul]] on your field and there are two or more cards in your opponent's Ener Zone, your opponent chooses a card from their Ener Zone and puts it into their trash.\n" +
                "@E %R %K: This SIGNI gains [[Assassin]] until end of turn."
        );
        
        setName("en_fan", "Kintoki, Crimson General Princess");
        setDescription("en_fan",
                "=H 1 black LRIG\n\n" +
                "@U: At the beginning of your attack phase, if there is a [[Soul]] on your field and there are 2 or more cards in your opponent's ener zone, your opponent puts 1 card choice from their ener zone into the trash.\n" +
                "@E %R %K: Until end of turn, this SIGNI gains [[Assassin]]."
        );
        
		setName("zh_simplified", "红将姬 金时");
        setDescription("zh_simplified", 
                "=H黑色的分身1只（当这只精灵出场时，如果不把你的竖直状态的黑色的分身1只横置，那么将此牌横置）\n" +
                "@U :你的攻击阶段开始时，你的场上有[[灵魂]]且对战对手的能量区的牌在2张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@E %R%K:直到回合结束时为止，这只精灵得到[[暗杀]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
            
            registerStockAbility(new StockAbilityHarmony(1, new TargetFilter().withColor(CardColor.BLACK)));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().withUnderType(CardUnderType.ATTACHED_SOUL).getValidTargetsCount() > 0 && getEnerCount(getOpponent()) >= 2)
            {
                CardIndex target = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(target);
            }
        }
        
        private void onEnterEff()
        {
            attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
        }
    }
}
