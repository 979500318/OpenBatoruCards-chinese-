package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.GameEventAccumulator;

public final class LRIG_K3_AlfouBrideOfBlackThoughts extends Card {

    public LRIG_K3_AlfouBrideOfBlackThoughts()
    {
        setImageSets("WX24-P3-030", "WX24-P3-030U");

        setOriginalName("黒想の花嫁　アルフォウ");
        setAltNames("コクソウノハナヨメアルフォウ Kokusou no Hanayome Arufou");
        setDescription("jp",
                "@U $TO $T1：あなたの＜悪魔＞のシグニの効果１つによってあなたのデッキからカードが１枚以上トラッシュに置かれたとき、その効果によってあなたのトラッシュに置かれたカードの中からカード１枚を対象とし、それを手札に加えるかエナゾーンに置く。\n" +
                "@A $G1 @[@|ジェラシー|@]@ %K0：このターン、あなたの効果１つによってデッキからカードが合計１枚以上トラッシュに置かれたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Alfou, Bride of Black Thoughts");
        setDescription("en",
                "@U $TO $T1: When 1 or more cards are put from your deck into the trash by a one of the effects of your <<Devil>> SIGNI, target 1 of the cards that were put into the trash this way, and add it to your hand or put it into the ener zone.\n" +
                "@A $G1 @[@|Jealousy|@]@ %K0: This turn, when 1 or more cards are put from a deck into the trash by one of your effects, target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "黑想的花嫁 阿尔芙");
        setDescription("zh_simplified", 
                "@U $TO $T1 :当因为你的<<悪魔>>精灵的效果1个从你的牌组把牌1张以上放置到废弃区时，从因为那个效果放置到你的废弃区的牌中把1张牌作为对象，将其加入手牌或放置到能量区。\n" +
                "@A $G1 忌妒%K0:这个回合，当因为你的效果1个从牌组把牌合计1张以上放置到废弃区时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

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

            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            auto.getFlags().addValue(AbilityFlag.ACTIVE_ONCE_PER_EFFECT);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Jealousy");
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            if(isOwnTurn() && isOwnCard(caller) && caller.isEffectivelyAtLocation(CardLocation.DECK_MAIN) &&
               getEvent().getSourceAbility() != null && getEvent().getSourceCost() == null && isOwnCard(getEvent().getSourceCardIndex()) &&
               getEvent().getSourceCardIndex().getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.DEVIL))
            {
                accumulator.process(getEvent());
                if(getEvent().isAtOnce(1)) return ConditionState.OK;
            }
            
            return ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = accumulator.getData(getEvent());
            
            CardIndex target = playerTargetCard(new TargetFilter().own().fromTrash().match(data)).get();
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

        private void onActionEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.TRASH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.getFlags().addValue(AbilityFlag.ACTIVE_ONCE_PER_EFFECT);
            
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() != null && getEvent().getSourceCost() == null && getEvent().isAtOnce(1) &&
                   caller.isEffectivelyAtLocation(CardLocation.DECK_MAIN) && isOwnCard(getEvent().getSource()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());
        }
    }
}


