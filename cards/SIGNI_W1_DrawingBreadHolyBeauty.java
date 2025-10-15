package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_DrawingBreadHolyBeauty extends Card {

    public SIGNI_W1_DrawingBreadHolyBeauty()
    {
        setImageSets("WX24-P3-061");

        setOriginalName("聖美　デッサンパン");
        setAltNames("セイビ デッサンパン Seibi Dessanpan");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたの他の＜美巧＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは[[シャドウ（レベル２以下のシグニ）]]を得る。\n" +
                "@A #D：次の対戦相手のターン終了時まで、このシグニは[[シャドウ（レベル２以下のシグニ）]]を得る。"
        );

        setName("en", "Drawing Bread, Holy Beauty");
        setDescription("en",
                "@U: At the end of your turn, target 1 of your other <<Beautiful Technique>> SIGNI, and until the end of your opponent's next turn, it gains [[Shadow (level 2 or lower SIGNI)]].\n" +
                "@A #D: Until the end of your opponent's next turn, this SIGNI gains [[Shadow (level 2 or lower SIGNI)]]."
        );

		setName("zh_simplified", "圣美 炭笔面包擦");
        setDescription("zh_simplified", 
                "@U 你的回合结束时，你的其他的&lt;美巧&gt;精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到[[暗影（等级2以下的精灵）]]。\n" +
                "@A #D:直到下一个对战对手的回合结束时为止，这只精灵得到[[暗影（等级2以下的精灵）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        // Contributed by NebelTal
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerActionAbility(new DownCost(), this::onActionEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.BEAUTIFUL_TECHNIQUE).except(getCardIndex())).get();
            if(target != null) attachAbility(target, new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onActionEff()
        {
            attachAbility(getCardIndex(), new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
