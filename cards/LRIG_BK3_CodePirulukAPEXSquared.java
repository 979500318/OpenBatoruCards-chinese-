package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class LRIG_BK3_CodePirulukAPEXSquared extends Card {

    public LRIG_BK3_CodePirulukAPEXSquared()
    {
        setImageSets("WX25-P2-034", "WX25-P2-034U");

        setOriginalName("コード・ピルルク・APEX²");
        setAltNames("コードピルルクアペクススクエアド Koodo Piruruku Apekusu Sukueado Squared");
        setDescription("jp",
                "=G 青かつ黒のルリグ\n" +
                "@U $TO $T1：あなたがスペルを使用したとき、あなたの場に＜電機＞のシグニがある場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。\n" +
                "@A $G1 @[@|カタルシス|@]@ %B0：あなたのトラッシュからコストの合計が２以下のスペル１枚を対象とし、それをコストを支払わずに使用する。"
        );

        setName("en", "Code Piruluk APEX²");
        setDescription("en",
                "=G Blue and black LRIG\n" +
                "@U $TO $T1: When you use a spell, if there is a <<Electric Machine>> SIGNI on your field, target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power.\n" +
                "@A $G1 @[@|Catharsis|@]@ %B0: Target 1 spell with a total cost of 2 or less from your trash, and use it without paying its cost."
        );

		setName("zh_simplified", "代号·皮璐璐可·APEX²");
        setDescription("zh_simplified", 
                "[[成长]]蓝色且黑色的分身\n" +
                "@U $TO $T1 :当你把魔法使用时，你的场上有<<電機>>精灵的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n" +
                "@A $G1 净罪%B0:从你的废弃区把费用的合计在2以下的魔法1张作为对象，将其不把费用支付，使用。\n"
        );

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE, CardColor.BLACK);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.color(CardColor.BLACK, 1));
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

            setUseCondition(UseCondition.GROW, new TargetFilter().LRIG().withColor(CardColor.BLUE).withColor(CardColor.BLACK));

            AutoAbility auto = registerAutoAbility(GameEventId.USE_SPELL, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Catharsis");
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -8000, ChronoDuration.turnEnd());
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ACTIVATE).own().spell().withCost(0,2).fromTrash()).get();
            if(target != null) use(target, new CostModifier(() -> new EnerCost(Cost.colorless(0)), ModifierMode.SET));
        }
    }
}

