package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.events.GameEventAccumulator;

public final class ARTS_G_WealthProsperityAndArrogantSplendor extends Card {

    public ARTS_G_WealthProsperityAndArrogantSplendor()
    {
        setImageSets("WX24-P3-007", "WX24-P3-007U");

        setOriginalName("栄耀栄華");
        setAltNames("リペアーウインド Ripeaa Uindo Repair Wind");
        setDescription("jp",
                "あなたのレベル２以上のセンタールリグ１体を対象とし、次のあなたのエナフェイズ終了時まで、それのリミットを＋１し、それは以下の能力を得る。" +
                "@>@U：対戦相手の効果１つによってあなたのトラッシュにカードが合計１枚以上置かれたとき、その効果によってあなたのトラッシュに置かれたカードの中からカードを１枚まで対象とし、それを手札に加えるかエナゾーンに置く。\n" +
                "@A $T1 #D：【エナチャージ２】をする。その後、あなたのエナゾーンからカードを１枚まで対象とし、それを手札に加える。"
        );

        setName("en", "Wealth, Prosperity, and Arrogant Splendor");
        setDescription("en",
                "Target your level 2 or higher center LRIG, and until the end of your next ener phase, it gets +1 limit, and it gains:" +
                "@>@U: When 1 or more cards are put into your trash by one of your opponent's effects, target up to 1 card that was put into the trash this way, and add it to your hand or put it into the ener zone.\n" +
                "@A $T1 #D: [[Ener Charge 2]]. Then, target up to 1 card from your ener zone, and add it to your hand."
        );

        setName("zh_simplified", "荣耀荣华");
        setDescription("zh_simplified", 
                "你的等级2以上的核心分身1只作为对象，直到下一个你的充能阶段结束时为止，其的界限+1，其得到以下的能力。" +
                "@>@U :当因为对战对手的效果1个往你的废弃区把牌合计1张以上放置时，从因为那个效果往你的废弃区放置的牌中把牌1张最多作为对象，将其加入手牌或放置到能量区。\n" +
                "@A $T1 #D:[[能量填充2]]。然后，从你的能量区把牌1张最多作为对象，将其加入手牌。@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final GameEventAccumulator accumulator = new GameEventAccumulator();
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
        }
        
        private ConditionState onARTSEffCond()
        {
            return new TargetFilter().own().LRIG().withLevel(2,0).getValidTargetsCount() == 0 ? ConditionState.WARN : ConditionState.OK;
        }
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().LRIG().withLevel(2,0)).get();
            if(target == null) return;
            
            gainValue(target, target.getIndexedInstance().getLimit(),1d, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
            
            AutoAbility attachedAuto = new AutoAbility(GameEventId.TRASH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.getFlags().addValue(AbilityFlag.ACTIVE_ONCE_PER_EFFECT);
            attachAbility(target, attachedAuto, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
            
            ActionAbility attachedAct = new ActionAbility(new DownCost(), this::onAttachedActionEff);
            attachedAct.setUseLimit(UseLimit.TURN, 1);
            attachedAct.setNestedDescriptionOffset(1);
            attachAbility(target, attachedAct, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            if(isOwnCard(caller) && getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()))
            {
                accumulator.process(getEvent());
                if(getEvent().isAtOnce(1)) return ConditionState.OK;
            }
            
            return ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = accumulator.getData(getEvent());
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter().own().fromTrash().match(data)).get();
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.ENER) == 1)
                {
                    addToHand(target);
                } else {
                    putInEner(target);
                }
            }
            
            accumulator.reset(data);
        }
        private void onAttachedActionEff()
        {
            enerCharge(2);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromEner()).get();
            addToHand(target);
        }
    }
}


