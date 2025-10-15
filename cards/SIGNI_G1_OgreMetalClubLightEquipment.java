package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataSIGNIClass;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G1_OgreMetalClubLightEquipment extends Card {

    public SIGNI_G1_OgreMetalClubLightEquipment()
    {
        setImageSets("WX25-P1-088");

        setOriginalName("小装　オニカナボウ");
        setAltNames("ショウソウオニカナボウ Shousou Onikanebou");
        setDescription("jp",
                "@C $TO：あなたの場にあるすべてのシグニがそれぞれ共通するクラスを持たないかぎり、このシグニのパワーは＋3000される。\n" +
                "@E %G：ターン終了時まで、このシグニは[[ランサー（パワー5000以下のシグニ）]]を得る。"
        );

        setName("en", "Ogre Metal Club, Light Equipment");
        setDescription("en",
                "@C $TO: As long as all of your SIGNI do not share a common class, this SIGNI gets +3000 power.\n" +
                "@E %G: Until end of turn, this SIGNI gains [[Lancer (SIGNI with power 5000 or less)]]."
        );

		setName("zh_simplified", "小装 鬼金棒");
        setDescription("zh_simplified", 
                "@C $TO :你的场上的全部的精灵不持有共通类别时，这只精灵的力量+3000。\n" +
                "@E %G:直到回合结束时为止，这只精灵得到[[枪兵（力量5000以下的精灵）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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

            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000));

            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1)), this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return isOwnTurn() && !CardDataSIGNIClass.shareCommonClass(getSIGNIOnField(getOwner())) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            attachAbility(getCardIndex(), new StockAbilityLancer(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getPower().getValue() <= 5000 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
