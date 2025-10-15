package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.PutInEnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G2_TsubakiKasuga extends Card {

    public SIGNI_G2_TsubakiKasuga()
    {
        setImageSets("WXDi-CP02-091");

        setOriginalName("春日ツバキ");
        setAltNames("カスガツバキ Kasuga Tsubaki");
        setDescription("jp",
                "@E @[手札から＜ブルアカ＞のカードを１枚エナゾーンに置く]@：次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。" +
                "~{{C：[[シャドウ（パワー8000以下のシグニ）]]@@" +
                "~#：【エナチャージ１】をする。その後、あなたのエナゾーンから＜ブルアカ＞のシグニを１枚まで対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Kasuga Tsubaki");
        setDescription("en",
                "@E @[Put a <<Blue Archive>> card from your hand into your Ener Zone]@: This SIGNI gets +4000 power until the end of your opponent's next end phase.~{{C: [[Shadow -- SIGNI with power 8000 or less]].@@" +
                "~#[[Ener Charge 1]]. Then, add up to one target <<Blue Archive>> SIGNI from your Ener Zone to your hand or put it onto your field."
        );
        
        setName("en_fan", "Tsubaki Kasuga");
        setDescription("en_fan",
                "@E @[Put 1 <<Blue Archive>> card from your hand into the ener zone]@: Until the end of your opponent's next turn, this SIGNI gets +4000 power." +
                "~{{C: [[Shadow (SIGNI with power 8000 or less)]]@@" +
                "~#[[Ener Charge 1]]. Then, target up to 1 <<Blue Archive>> SIGNI from your ener zone, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "春日椿");
        setDescription("zh_simplified", 
                "@E 从手牌把<<ブルアカ>>牌1张放置到能量区:直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。\n" +
                "~{{C:[[暗影（力量8000以下的精灵）]]（这只精灵不会被对战对手的力量8000以下的精灵作为对象）@@" +
                "~#[[能量填充1]]。然后，从你的能量区把<<ブルアカ>>精灵1张最多作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new PutInEnerCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromHand()), this::onEnterEff);

            ConstantAbility cont = registerConstantAbility(new AbilityGainModifier(this::onConstEffModGetSample));
            cont.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
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
