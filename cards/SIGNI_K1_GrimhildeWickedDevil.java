package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_K1_GrimhildeWickedDevil extends Card {

    public SIGNI_K1_GrimhildeWickedDevil()
    {
        setImageSets("WX24-P3-087");

        setOriginalName("凶魔　グリムヒルド");
        setAltNames("キョウマグリムヒルド Kyouma Gurimuhirude");
        setDescription("jp",
                "@U $TO $T1：あなたの＜悪魔＞のシグニの効果によってデッキからカード１枚がトラッシュに置かれたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Grimhilde, Wicked Devil");
        setDescription("en",
                "@U $TO $T1: When a card is put from your deck into the trash by the effect of a <<Devil>> SIGNI, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "凶魔 格莉希尔德");
        setDescription("zh_simplified", 
                "@U $TO $T1 :当因为你的<<悪魔>>精灵的效果从牌组把1张牌放置到废弃区时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && caller.isEffectivelyAtLocation(CardLocation.DECK_MAIN) &&
                   getEvent().getSourceAbility() != null && getEvent().getSourceCost() == null && isOwnCard(getEvent().getSource()) &&
                   CardType.isSIGNI(getEvent().getSource().getCardReference().getType()) && getEvent().getSource().getSIGNIClass().matches(CardSIGNIClass.DEVIL) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
    }
}
