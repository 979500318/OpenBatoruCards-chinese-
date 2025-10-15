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
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G1_ShizukoKawawaSwimsuit extends Card {

    public SIGNI_G1_ShizukoKawawaSwimsuit()
    {
        setImageSets("WX25-CP1-072");

        setOriginalName("河和シズコ(水着)");
        setAltNames("カワワシズコミズギ Kawawa Shizuko Mizugi");
        setDescription("jp",
                "@U：あなたのターン終了時、次の対戦相手のターン終了時まで、あなたのすべての＜ブルアカ＞のシグニのパワーを＋2000する。" +
                "~{{C：[[シャドウ（パワー8000以下のシグニ）]]@@" +
                "~#：【エナチャージ１】をする。その後、あなたのエナゾーンから＜ブルアカ＞のシグニを１枚まで対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Kawawa Shizuko (Swimsuit)");

        setName("en_fan", "Shizuko Kawawa (Swimsuit)");
        setDescription("en",
                "@U: At the end of your turn, until the end of your opponent's next turn, all of your <<Blue Archive>> SIGNI get +2000 power." +
                "~{{C: [[Shadow (SIGNI with power 8000 or less)]]@@" +
                "~#[[Ener Charge 1]]. Then, target up to 1 <<Blue Archive>> SIGNI from your ener zone, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "河和静子(泳装)");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，直到下一个对战对手的回合结束时为止，你的全部的<<ブルアカ>>精灵的力量+2000。\n" +
                "~{{C:[[暗影（力量8000以下的精灵）]]@@" +
                "~#[[能量填充1]]。然后，从你的能量区把<<ブルアカ>>精灵1张最多作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ConstantAbility cont = registerConstantAbility(new AbilityGainModifier(this::onConstEffModGetSample));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).getExportedData(), 2000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                    cardIndexSource.getIndexedInstance().getPower().getValue() <= 8000 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()).get();
            
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
