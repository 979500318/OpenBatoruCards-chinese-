package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_MokumorenDissonaNaturalSpirit extends Card {

    public SIGNI_W1_MokumorenDissonaNaturalSpirit()
    {
        setImageSets("WXDi-P13-058");

        setOriginalName("幻怪　モクモレン//ディソナ");
        setAltNames("ゲンカイモクモレンディソナ Genkai Mokumoren Disona");
        setDescription("jp",
                "@C：対戦相手のターンの間、あなたの場に他の#Sのシグニがあるかぎり、このシグニは【シャドウ（レベル１）】と【シャドウ（レベル２）】を得る。"
        );

        setName("en", "Mokumoren//Dissona, Phantom Spirit");
        setDescription("en",
                "@C: During your opponent's turn, as long as there is another #S SIGNI on your field, this SIGNI gains [[Shadow -- Level one]] and [[Shadow -- Level two]]. "
        );
        
        setName("en_fan", "Mokumoren//Dissona, Natural Apparition");
        setDescription("en_fan",
                "@C: During your opponent's turn, as long as there is another #S @[Dissona]@ SIGNI on your field, this SIGNI gains [[Shadow (level 1)]] and [[Shadow (level 2)]]."
        );

		setName("zh_simplified", "幻怪 目犍连//失调");
        setDescription("zh_simplified", 
                "@C 对战对手的回合期间，你的场上有其他的#S的精灵时，这只精灵得到[[暗影（等级1）]]和[[暗影（等级2）]]。（这只精灵不会被对战对手的等级1的分身和等级1的精灵作为对象。等级2也同样）\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffMod1GetSample),new AbilityGainModifier(this::onConstEffMod2GetSample));
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && new TargetFilter().own().SIGNI().dissona().except(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffMod1GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEff1AddCond));
        }
        private ConditionState onAttachedStockEff1AddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getLevel().getValue() == 1 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffMod2GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEff2AddCond, 1));
        }
        private ConditionState onAttachedStockEff2AddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getLevel().getValue() == 2 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}

