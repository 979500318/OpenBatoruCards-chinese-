package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.stock.StockAbilityShoot;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_W3_HasumiHanekawaSportswear extends Card {

    public SIGNI_W3_HasumiHanekawaSportswear()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WX25-CP1-040");

        setOriginalName("羽川ハスミ(体操服)");
        setAltNames("ハネカワハスミタイソウフク Hanekawa Hasumi Taisoufuku");
        setDescription("jp",
                "@C：【シュート】\n" +
                "@A $T1 @[エナゾーンから＜ブルアカ＞のカードを３枚までトラッシュに置く]@：この方法でトラッシュに置いたカードの枚数と同じレベルの対戦相手のシグニ１体を対象とし、それを手札に戻す。" +
                "~{{U：このシグニがバトルによってシグニ１体をバニッシュしたとき、【エナチャージ１】をする。"
        );

        setName("en", "Hanekawa Hasumi (Sportswear)");

        setName("en_fan", "Hasumi Hanekawa (Sportswear)");
        setDescription("en",
                "@C: [[Shoot]]\n" +
                "@A $T1 @[Put up to 3 <<Blue Archive>> cards from your ener zone into the trash]@: Target 1 of your opponent's SIGNI with the same level as the number of cards put into the trash this way, and return it to their hand.\n" +
                "~{{U: Whenever this SIGNI banishes a SIGNI in battle, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "羽川莲见(体操服)");
        setDescription("zh_simplified", 
                "@C :[[击落]]\n" +
                "@A $T1 从能量区把<<ブルアカ>>牌3张最多放置到废弃区:与这个方法放置到废弃区的牌的张数相同等级的对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "~{{U:当这只精灵因为战斗把精灵1只破坏时，[[能量填充1]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerStockAbility(new StockAbilityShoot());

            ActionAbility act = registerActionAbility(new TrashCost(0,3, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onActionEff()
        {
            if(!getAbility().getCostPaidData().isEmpty() && getAbility().getCostPaidData().get() != null)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(getAbility().getCostPaidData().size())).get();
                addToHand(target);
            }
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() == null && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}
