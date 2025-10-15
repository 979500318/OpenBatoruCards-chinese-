package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventMove;

import java.util.List;
import java.util.stream.Stream;

public final class SIGNI_BK3_CodeHeartCMera extends Card {

    public SIGNI_BK3_CodeHeartCMera()
    {
        setImageSets("WX25-P2-063", "WX25-P2-063U");
        setLinkedImageSets("WX25-P2-034");

        setOriginalName("コードハート　Cメラ");
        setAltNames("コードハートシーメラ Koodo Haato Shii Mera Cmera");
        setDescription("jp",
                "@U $TP $T1：グロウフェイズ以外で対戦相手の効果によってカードが１枚以上対戦相手の手札に移動したとき、対戦相手は手札を１枚捨てる。\n" +
                "@A $T1 %B %B：あなたの場に《コード・ピルルク・APEX²》がいる場合、対戦相手のシグニ１体を対象とし、あなたのトラッシュからそれぞれレベルの異なる＜電機＞のシグニ３枚を好きな順番でデッキの一番下に置く。そうした場合、それをバニッシュする。"
        );

        setName("en", "Code Heart C Mera");
        setDescription("en",
                "@U $TP $T1: When 1 or more cards are moved to your opponent's hand due to one of your opponent's effects, except during the grow phase, your opponent discards 1 card from their hand.\n" +
                "@A $T1 %B %K: If your LRIG is \"Code Piruluk APEX²\", target 1 of your opponent's SIGNI, and put 3 <<Electric Machine>> SIGNI with different levels from your trash on the bottom of your deck in any order. If you do, banish it."
        );

		setName("zh_simplified", "爱心代号 相机");
        setDescription("zh_simplified", 
                "@U $TP $T1 :当在成长阶段以外因为对战对手的效果把牌1张以上往对战对手的手牌移动时，对战对手把手牌1张舍弃。\n" +
                "@A $T1 %B%K:你的场上有《コード・ピルルク・APEX²》的场合，对战对手的精灵1只作为对象，从你的废弃区把等级不同的<<電機>>精灵3张任意顺序放置到牌组最下面。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.BLACK);
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

            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            auto.getFlags().addValue(AbilityFlag.ACTIVE_ONCE_PER_EFFECT);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 1) + Cost.color(CardColor.BLACK, 1)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnTurn() && !isOwnCard(caller) && EventMove.getDataMoveLocation() == CardLocation.HAND &&
                   getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSource()) && getEvent().isAtOnce(1) &&
                   getCurrentPhase() != GamePhase.GROW ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }

        private ConditionState onActionEffCond()
        {
            return canConditionBeFulfilled(new TargetFilter(TargetHint.BOTTOM).own().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE).fromTrash().getExportedData().stream()) ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("コード・ピルルク・APEX²"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                
                if(target != null)
                {
                    TargetFilter filter = new TargetFilter(TargetHint.BOTTOM).own().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE).fromTrash();
                    if(canConditionBeFulfilled(filter.getExportedData().stream()))
                    {
                        DataTable<CardIndex> data = playerTargetCard(3, filter, this::onActionEffTargetCond);
                        
                        if(returnToDeck(data, DeckPosition.BOTTOM) == 3)
                        {
                            banish(target);
                        }
                    }
                }
            }
        }
        private boolean onActionEffTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 3 && canConditionBeFulfilled(listPickedCards.stream());
        }
        private boolean canConditionBeFulfilled(Stream<? super CardIndex> stream)
        {
            return stream.mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).distinct().count() == 3;
        }
    }
}
