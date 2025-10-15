package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B2_SakiSorai extends Card {

    public SIGNI_B2_SakiSorai()
    {
        setImageSets("WX25-CP1-068");

        setOriginalName("空井サキ");
        setAltNames("ソライサキ Sorai Saki");
        setDescription("jp",
                "@E %B @[手札から＜ブルアカ＞のカードを１枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。" +
                "~{{U：あなたのアタックフェイズ開始時、手札を１枚捨ててもよい。そうした場合、対戦相手の手札を１枚見ないで選び、捨てさせる。@@" +
                "~#カードを３枚引き、手札を１枚捨てる。"
        );

        setName("en", "Sorai Saki");

        setName("en_fan", "Saki Sorai");
        setDescription("en",
                "@E %B @[Discard 1 <<Blue Archive>> card from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power." +
                "~{{U: At the beginning of your attack phase, you may discard 1 card from your hand. If you do, choose 1 card from your opponent's hand without looking, and discard it.@@" +
                "~#Draw 3 cards, and discard 1 card from your hand."
        );

		setName("zh_simplified", "空井咲");
        setDescription("zh_simplified", 
                "@E %B从手牌把<<ブルアカ>>牌1张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n" +
                "~{{U:你的攻击阶段开始时，可以把手牌1张舍弃。这样做的场合，不看对战对手的手牌选1张，舍弃。@@" +
                "~#抽3张牌，手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.BLUE, 1)),
                new DiscardCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE))
            ), this::onEnterEff);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, -8000, ChronoDuration.turnEnd());
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }

        private void onLifeBurstEff()
        {
            draw(3);
            discard(1);
        }
    }
}
