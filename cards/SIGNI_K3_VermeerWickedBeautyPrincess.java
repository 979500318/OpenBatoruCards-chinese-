package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_VermeerWickedBeautyPrincess extends Card {

    public SIGNI_K3_VermeerWickedBeautyPrincess()
    {
        setImageSets("WX25-P1-063");

        setOriginalName("凶美姫　フェルメール");
        setAltNames("キョウビキフェルメール Kyoubiki Ferumeeru");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のセンタールリグと共通する色を持たない対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－7000する。\n" +
                "@E %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは色を失う。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Vermeer, Wicked Beauty Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI that doesn't share a common color with your opponent's center LRIG, and until end of turn, it gets --7000 power.\n" +
                "@E %X: Target 1 of your opponent's SIGNI, and until end of turn, it loses its colors." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "凶美姬 维米尔");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，不持有与对战对手的核心分身共通颜色的对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-7000。\n" +
                "@E %X:对战对手的精灵1只作为对象，直到回合结束时为止，其的颜色失去。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor()))).get();
            gainPower(target, -7000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI()).get();
            
            if(target != null)
            {
                target.getIndexedInstance().getColor().resetValue();
                setBaseValue(target, target.getIndexedInstance().getColor(), CardDataColor.EMPTY_VALUE, ChronoDuration.turnEnd());
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
                gainPower(target, -15000, ChronoDuration.turnEnd());
            } else {
                draw(1);
            }
        }
    }
}
