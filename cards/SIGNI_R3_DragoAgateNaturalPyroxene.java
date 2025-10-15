package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_R3_DragoAgateNaturalPyroxene extends Card {

    public SIGNI_R3_DragoAgateNaturalPyroxene()
    {
        setImageSets("WX24-P2-051");

        setOriginalName("羅輝石　ドラゴアゲート");
        setAltNames("ラキセキドラゴアゲート Rakiseki Dorago Agaato");
        setDescription("jp",
                "@U $T1：コストか効果によってあなたが#Gを持たないカードを１枚捨てたとき、そのカードをトラッシュからエナゾーンに置く。\n" +
                "@U：あなたのアタックフェイズ開始時、%R %R %Rを支払ってもよい。そうした場合、ターン終了時まで、このシグニは【アサシン】を得る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Drago Agate, Natural Pyroxene");
        setDescription("en",
                "@U $T1: When you discard a card without #G @[Guard]@ due to a cost or an effect, put that card from your trash into the ener zone.\n" +
                "@U: At the beginning of your attack phase, you may pay %R %R %R. If you do, until end of turn, this SIGNI gains [[Assassin]]." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "罗辉石 龙纹玛瑙");
        setDescription("zh_simplified", 
                "@U $T1 当因为费用或效果你把不持有#G的牌1张舍弃时，那张牌从废弃区放置到能量区。\n" +
                "@U :你的攻击阶段开始时，可以支付%R %R %R。这样做的场合，直到回合结束时为止，这只精灵得到[[暗杀]]。（这只精灵攻击，不与正面的精灵进行战斗，给予对战对手伤害）" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            auto1.enableEventSourceSelection();
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && getEvent().getSourceAbility() != null && !caller.getIndexedInstance().isState(CardStateFlag.CAN_GUARD) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(caller.getLocation() == CardLocation.TRASH)
            {
                putInEner(caller);
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(payEner(Cost.color(CardColor.RED, 3)))
            {
                attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                draw(1);
            }
        }
    }
}
