package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_G3_BangDissonaVerdantDevilPrincess extends Card {

    public SIGNI_G3_BangDissonaVerdantDevilPrincess()
    {
        setImageSets("WXDi-P12-051", "WXDi-P12-051P", "SPDi02-18",Mask.IGNORE+"SPDi02-29");

        setOriginalName("翠魔姫　バン//ディソナ");
        setAltNames("スイマキバンディソナ Suimaki Ban Disona");
        setDescription("jp",
                "@C：あなたのエナゾーンに#Sのカードが３枚以上あるかぎり、このシグニのパワーは＋3000され、このシグニは[[シャドウ（レベル３以上のシグニ）]]を得る。\n" +
                "@A $T1 %G %X：対戦相手は自分のパワー10000以上のシグニ１体を選びエナゾーンに置く。"
        );

        setName("en", "Bang//Dissona, Jade Evil Queen");
        setDescription("en",
                "@C: As long as there are three or more #S cards in your Ener Zone, this SIGNI gets +3000 power and gains [[Shadow -- Level three or more SIGNI]].\n@A $T1 %G %X: Your opponent chooses a SIGNI on their field with power 10000 or more and puts it into its owner's Ener Zone."
        );
        
        setName("en_fan", "Bang//Dissona, Verdant Devil Princess");
        setDescription("en_fan",
                "@C: As long as there are 3 or more #S @[Dissona]@ cards in your ener zone, this SIGNI gets +3000 power, and it gains [[Shadow (level 3 or higher SIGNI)]].\n" +
                "@A $T1 %G %X: Your opponent chooses 1 of their SIGNI with power 10000 or more, and puts it into the ener zone."
        );

		setName("zh_simplified", "翠魔姬 梆//失调");
        setDescription("zh_simplified", 
                "@C 你的能量区的#S的牌在3张以上时，这只精灵的力量+3000，这只精灵得到[[暗影（等级3以上的精灵）]]。\n" +
                "@A $T1 %G%X:对战对手选自己的力量10000以上的精灵1只放置到能量区。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000), new AbilityGainModifier(this::onConstEffModGetSample));

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().dissona().fromEner().getValidTargetsCount() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.ENER).own().SIGNI().withPower(10000,0)).get();
            putInEner(cardIndex);
        }
    }
}
