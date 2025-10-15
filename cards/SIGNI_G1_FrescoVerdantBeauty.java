package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G1_FrescoVerdantBeauty extends Card {

    public SIGNI_G1_FrescoVerdantBeauty()
    {
        setImageSets("WX24-P4-078");

        setOriginalName("翠美　フレスコ");
        setAltNames("スイビフレスコ Suibi Furesuko");
        setDescription("jp",
                "@C $TP：このシグニのパワーは＋2000される。\n" +
                "@C $TP：[[シャドウ（パワー5000以下のシグニ）]]"
        );

        setName("en", "Fresco, Verdant Beauty");
        setDescription("en",
                "@C $TP: This SIGNI gets +2000 power.\n" +
                "@C $TP: [[Shadow (SIGNI with power 5000 or less)]]"
        );

		setName("zh_simplified", "翠美 壁画");
        setDescription("zh_simplified", 
                "@C $TP :这只精灵的力量+2000。\n" +
                "@C $TP :[[暗影（力量5000以下的精灵）]]（这只精灵不会被对战对手的力量5000以下的精灵作为对象）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
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

            registerConstantAbility(this::onConstEffCond, new PowerModifier(2000));
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEff2ModGetSample));
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getPower().getValue() <= 5000 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
