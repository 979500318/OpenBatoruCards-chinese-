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
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class LRIG_R3_YuzukiThreeIncandescentDance extends Card {

    public LRIG_R3_YuzukiThreeIncandescentDance()
    {
        setImageSets("WX24-P2-018", "WX24-P2-018U");

        setOriginalName("熾炎舞　遊月・参");
        setAltNames("シエンブユヅキサン Shienbu Yuzuki San");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの＜龍獣＞のシグニ１体を対象とし、%Rを支払ってもよい。そうした場合、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、対戦相手が%X %X %Xを支払わないかぎり、ターン終了時まで、このシグニは【アサシン】を得る。@@を得る。\n" +
                "@A $G1 @[@|真直ぐな心|@]@ %R0：以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手は自分のエナゾーンからカードを３枚選びトラッシュに置く。\n" +
                "$$2あなたのシグニ１体を対象とし、ターン終了時まで、それは【ダブルクラッシュ】を得る。"
        );

        setName("en", "Yuzuki Three, Incandescent Dance");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your <<Dragon Beast>> SIGNI, and you may pay %R. If you do, until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, until end of turn, this SIGNI gains [[Assassin]] unless your opponent pays %X %X %X.@@" +
                "@A $G1 @[@|Straightforward Heart|@]@ %R0: @[@|Choose 1 of the following:|@]@\n" +
                "$$1 Your opponent chooses 3 cards from their ener zone, and puts them into the trash.\n" +
                "$$2 Target 1 of your SIGNI, and until end of turn, it gains [[Double Crush]]."
        );

		setName("zh_simplified", "炽炎舞 游月·叁");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的<<龍獣>>精灵1只作为对象，可以支付%R。这样做的场合，直到回合结束时为止，其得到\n" +
                "@>@U 当这只精灵攻击时，如果对战对手不把%X %X %X:支付，那么直到回合结束时为止，这只精灵得到[[暗杀]]。@@\n" +
                "@A $G1 直率的心%R0:从以下的2种选1种。\n" +
                "$$1 对战对手从自己的能量区选3张牌放置到废弃区。\n" +
                "$$2 你的精灵1只作为对象，直到回合结束时为止，其得到[[双重击溃]]。\n"
        );

        setLRIGType(CardLRIGType.YUZUKI);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);

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
            
            ActionAbility action = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff);
            action.setUseLimit(UseLimit.GAME, 1);
            action.setName("Straightforward Heart");
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.DRAGON_BEAST)).get();
            
            if(target != null && payEner(Cost.color(CardColor.RED, 1)))
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            if(!payEner(getOpponent(), Cost.colorless(3)))
            {
                attachAbility(getAbility().getSourceCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
        }

        private void onActionEff()
        {
            if(playerChoiceMode() == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(getOpponent(), Math.min(3, getEnerCount(getOpponent())), new TargetFilter(TargetHint.BURN).own().fromEner());
                trash(data);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
                if(target != null) attachAbility(target, new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
            }
        }
    }
}
