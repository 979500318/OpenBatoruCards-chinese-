package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

import java.util.OptionalDouble;

public final class SIGNI_W1_ShimikoEndou extends Card {

    public SIGNI_W1_ShimikoEndou()
    {
        setImageSets("WX25-CP1-051");

        setOriginalName("円堂シミコ");
        setAltNames("エンドウシミコ Endou Shimiko");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたの他の＜ブルアカ＞のシグニのうち最もパワーの低いシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは[[シャドウ（レベル２以下）]]を得る。" +
                "~{{C $TP：[[シャドウ（レベル２以下）]]"
        );

        setName("en", "Endou Shimiko");

        setName("en_fan", "Shimiko Endou");
        setDescription("en",
                "@U: At the end of your turn, target 1 of your other <<Blue Archive>> SIGNI with the lowest power, and until the end of your opponent's next turn, it gains [[Shadow (level 2 or lower)]]." +
                "~{{C $TP: [[Shadow (level 2 or lower)]]."
        );

		setName("zh_simplified", "圆堂志美子");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，你的其他的<<ブルアカ>>精灵中力量最低的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到[[暗影（等级2以下）]]。\n" +
                "~{{C$TP :[[暗影（等级2以下）]]@@\n" +
                "（$TP :的能力，只有在对战对手的回合期间变为有效）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(3000);

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

            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            TargetFilter filter = new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex());
            OptionalDouble minPower = filter.getExportedData().stream().mapToDouble(c -> ((CardIndex)c).getIndexedInstance().getPower().getValue()).min();
            
            if(minPower.isPresent())
            {
                CardIndex target = playerTargetCard(filter.withPower(minPower.getAsDouble())).get();
                attachAbility(target, new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return (CardType.isSIGNI(cardIndexSource.getCardReference().getType()) || CardType.isLRIG(cardIndexSource.getCardReference().getType())) &&
                    cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
