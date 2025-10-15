package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbility;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_MariIochi extends Card {

    public SIGNI_W1_MariIochi()
    {
        setImageSets("WXDi-CP02-067");

        setOriginalName("伊落マリー");
        setAltNames("イオチマリー Iochi Marii");
        setDescription("jp",
                "@C：対戦相手のターンの間、あなたの場に他の＜ブルアカ＞のシグニがあるかぎり、このシグニは[[シャドウ（レベル１）]]を得る。\n" +
                "@U：あなたのターン終了時、あなたの他の＜ブルアカ＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは[[シャドウ（レベル１）]]を得る。" +
                "~{{E @[手札から＜ブルアカ＞のカードを２枚捨てる]@：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Iochi Mari");
        setDescription("en",
                "@C: During your opponent's turn, as long as there is another <<Blue Archive>> SIGNI on your field, this SIGNI gains [[Shadow -- Level one]].\n@U: At the end of your turn, another target <<Blue Archive>> SIGNI on your field gains [[Shadow -- Level one]] until the end of your opponent's next end phase.~{{E @[Discard two <<Blue Archive>> cards]@: Add target SIGNI with a #G from your trash to your hand."
        );
        
        setName("en_fan", "Mari Iochi");
        setDescription("en_fan",
                "@C: During your opponent's turn, as long as there is another <<Blue Archive>> SIGNI on your field, this SIGNI gains [[Shadow (level 1)]].\n" +
                "@U: At the end of your turn, target 1 of your other <<Blue Archive>> SIGNI, and until the end of your opponent's next turn, it gains [[Shadow (level 1)]]." +
                "~{{E @[Discard 2 <<Blue Archive>> cards from your hand]@: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "伊落玛丽");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，你的场上有其他的<<ブルアカ>>精灵时，这只精灵得到[[暗影（等级1）]]。\n" +
                "@U :你的回合结束时，你的其他的<<ブルアカ>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到[[暗影（等级1）]]。\n" +
                "~{{E从手牌把<<ブルアカ>>牌2张舍弃从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            EnterAbility enter = registerEnterAbility(new DiscardCost(2, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff);
            enter.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getLevel().getValue() == 1 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex())).get();
            if(target != null) attachAbility(target, new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }
    }
}
