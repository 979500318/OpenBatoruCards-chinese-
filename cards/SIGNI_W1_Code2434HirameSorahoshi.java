package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_Code2434HirameSorahoshi extends Card {

    public SIGNI_W1_Code2434HirameSorahoshi()
    {
        setImageSets("WXDi-CP01-034");

        setOriginalName("コード２４３４　空星きらめ");
        setAltNames("コードニジサンジソラホシキラメ Koodo Nijisanji Sorahoshi Kirame");
        setDescription("jp",
                "@C：対戦相手のターンの間、あなたの場に他の＜バーチャル＞のシグニがあるかぎり、このシグニのパワーは＋2000され、このシグニは[[シャドウ（レベル１）]]を得る。"
        );

        setName("en", "Sorahoshi Kirame, Code 2434");
        setDescription("en",
                "@C: During your opponent's turn, as long as there is another <<Virtual>> SIGNI on your field, this SIGNI gets +2000 power and gains [[Shadow -- Level one]]. "
        );
        
        setName("en_fan", "Code 2434 Hirame Sorahoshi");
        setDescription("en_fan",
                "@C: During your opponent's turn, as long as there is another <<Virtual>> SIGNI on your field, this SIGNI gets +2000 power, and it gains [[Shadow (level 1)]]."
        );

		setName("zh_simplified", "2434代号 空星煌");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，你的场上有其他的<<バーチャル>>精灵时，这只精灵的力量+2000，这只精灵得到[[暗影（等级1）]]。（这只精灵不会被对战对手的等级1的分身和等级1的精灵作为对象）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(2000),new AbilityGainModifier(this::onConstEffModGetSample));
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).except(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getLevel().getValue() == 1 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
