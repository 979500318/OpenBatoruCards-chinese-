package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G1_MiaoPhantomWarBeast extends Card {

    public SIGNI_G1_MiaoPhantomWarBeast()
    {
        setImageSets("WX24-P1-072");

        setOriginalName("幻闘獣　ミャオ");
        setAltNames("ゲントウジュウミャオ Gentoujuu Myao");
        setDescription("jp",
                "@E %X：ターン終了時まで、このシグニは[[ランサー（パワー5000以下のシグニ）]]を得る。"
        );

        setName("en", "Miao, Phantom War Beast");
        setDescription("en",
                "@E %X: Until end of turn, this SIGNI gains [[Lancer (SIGNI with power 5000 or less)]]."
        );

		setName("zh_simplified", "幻斗兽 喵");
        setDescription("zh_simplified", 
                "@E %X:直到回合结束时为止，这只精灵得到[[枪兵（力量5000以下的精灵）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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

            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
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
