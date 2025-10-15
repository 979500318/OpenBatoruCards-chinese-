package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;
import open.batoru.data.ability.stock.StockAbilitySLancer;
import open.batoru.data.ability.stock.StockAbilityShadow;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_G3_RengeFuwa extends Card {

    public SIGNI_G3_RengeFuwa()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WX25-CP1-046");

        setOriginalName("不破レンゲ");
        setAltNames("フワレンゲ Fuwa Renge");
        setDescription("jp",
                "@U $TO $T1：あなたのエナゾーンにカード１枚が置かれたとき、ターン終了時まで、このシグニのパワーを＋5000する。\n" +
                "@U：このシグニがアタックしたとき、あなたの場にあるすべてのシグニが＜ブルアカ＞の場合、%G %Xを支払ってもよい。そうした場合、ターン終了時まで、このシグニは【ランサー】を得る。このシグニのパワーが15000以上の場合、代わりにターン終了時まで、このシグニは【Ｓランサー】を得る。" +
                "~{{C：[[シャドウ（レベル３以上のシグニ）]]"
        );

        setName("en", "Fuwa Renge");

        setName("en_fan", "Renge Fuwa");
        setDescription("en",
                "@U $TO $T1: When a card is put into your ener zone, until end of turn, this SIGNI gets +5000 power.\n" +
                "@U: Whenever this SIGNI attacks, if all of your SIGNI are <<Blue Archive>> SIGNI, you may pay %G %X. If you do, until end of turn, this SIGNI gains [[Lancer]]. If this SIGNI's power is 15000 or more, instead, until end of turn, this SIGNI gains [[S Lancer]]." +
                "~{{C: [[Shadow (level 3 or higher SIGNI)]]"
        );

		setName("zh_simplified", "不破莲华");
        setDescription("zh_simplified", 
                "@U $TO $T1 :当你的能量区有1张牌放置时，直到回合结束时为止，这只精灵的力量+5000。\n" +
                "@U :当这只精灵攻击时，你的场上的全部的精灵是<<ブルアカ>>的场合，可以支付%G%X。这样做的场合，直到回合结束时为止，这只精灵得到[[枪兵]]。这只精灵的力量在15000以上的场合，作为替代，直到回合结束时为止，这只精灵得到[[S枪兵]]。\n" +
                "~{{C:[[暗影（等级3以上的精灵）]]@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.ENER, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);

            Ability cont = registerConstantAbility(new AbilityGainModifier(this::onConstEffModGetSample));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            gainPower(getCardIndex(), 5000, ChronoDuration.turnEnd());
        }
        
        private void onAutoEff2()
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).getValidTargetsCount() == 0 &&
               payEner(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)))
            {
                attachAbility(getCardIndex(), getPower().getValue() < 15000 ? new StockAbilityLancer() : new StockAbilitySLancer(), ChronoDuration.turnEnd());
            }
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
    }
}
