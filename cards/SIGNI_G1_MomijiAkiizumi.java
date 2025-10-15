package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G1_MomijiAkiizumi extends Card {

    public SIGNI_G1_MomijiAkiizumi()
    {
        setImageSets("WX25-CP1-076");

        setOriginalName("秋泉モミジ");
        setAltNames("アキイズミモミジ Akiizuki Momiji");
        setDescription("jp",
                "@E @[エナゾーンから＜ブルアカ＞のカード１枚をトラッシュに置く]@：ターン終了時まで、このシグニは[[ランサー（パワー5000以下のシグニ）]]を得る。" +
                "~{{C：このシグニのパワーは＋4000される。"
        );

        setName("en", "Akiizumi Momiji");

        setName("en_fan", "Momiji Akiizumi");
        setDescription("en",
                "@E @[Put 1 <<Blue Archive>> card from your ener zone into the trash]@: Until end of turn, this SIGNI gains [[Lancer (SIGNI with power 5000 or less)]]." +
                "~{{C: This SIGNI gets +4000 power."
        );

		setName("zh_simplified", "秋泉红叶");
        setDescription("zh_simplified", 
                "@E 从能量区把<<ブルアカ>>牌1张放置到废弃区:直到回合结束时为止，这只精灵得到[[枪兵（力量5000以下的精灵）]]。\n" +
                "~{{C:这只精灵的力量+4000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
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

            registerEnterAbility(new TrashCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()), this::onEnterEff);

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);
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
