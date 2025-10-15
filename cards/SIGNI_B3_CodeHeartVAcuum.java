package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class SIGNI_B3_CodeHeartVAcuum extends Card {

    public SIGNI_B3_CodeHeartVAcuum()
    {
        setImageSets("WX24-P1-044");
        setLinkedImageSets("WX24-P1-013");

        setOriginalName("コードハート　Vキューム");
        setAltNames("コードハートブイキューム Koodo Haato Bui Kyuumu Vacuum");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《ロストコード・ピルルク》がいる場合、カードを１枚引くか、対戦相手は手札を１枚捨てる。\n" +
                "@A $T1 @[エナゾーンから＜電機＞のシグニを１枚以上トラッシュに置く]@：あなたの手札からコストの合計が「この方法でトラッシュに置いたカードの枚数＋１」以下のスペル１枚をコストを支払わずに使用する。"
        );

        setName("en", "Code Heart V Acuum");
        setDescription("en",
                "@U: At the beginning of your attack phase, if your LRIG is \"Lost Code Piruluk\", draw 1 card or your opponent discards 1 card from their hand.\n" +
                "@A $T1 @[Put 1 or more <<Electric Machine>> SIGNI from your ener zone into the trash]@: Use 1 spell from your hand with a total cost equal to or less than \"the number of cards put into the trash this way + 1\" without paying its cost."
        );

		setName("zh_simplified", "爱心代号 真空吸尘器");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《ロストコード・ピルルク》的场合，抽1张牌或，对战对手把手牌1张舍弃。\n" +
                "@A $T1 从能量区把<<電機>>精灵1张以上放置到废弃区:从你的手牌把费用的合计在\n" +
                "@>:这个方法放置到废弃区的牌的张数+1@@\n" +
                "以下的魔法1张不把费用支付，使用。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(12000);

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

            ActionAbility act = registerActionAbility(new TrashCost(1,AbilityConst.MAX_UNLIMITED, new TargetFilter().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE).fromEner()), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("ロストコード・ピルルク"))
            {
                if(playerChoiceAction(ActionHint.DRAW, ActionHint.DISCARD) == 1)
                {
                    draw(1);
                } else {
                    discard(getOpponent(), 1);
                }
            }
        }
        
        private void onActionEff()
        {
            int count = getAbility().getCostPaidData().size();
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ACTIVATE).own().spell().withCost(0,count+1).fromHand()).get();
            if(cardIndex != null) use(cardIndex, new CostModifier(() -> new EnerCost(Cost.colorless(0)), ModifierMode.SET));
        }
    }
}
