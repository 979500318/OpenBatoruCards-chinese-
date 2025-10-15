package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.PutInEnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G1_KaedeIsami extends Card {

    public SIGNI_G1_KaedeIsami()
    {
        setImageSets("WXDi-CP02-083");

        setOriginalName("勇美カエデ");
        setAltNames("イサミカエデ Isami Kaede");
        setDescription("jp",
                "@E @[手札から＜ブルアカ＞のカードを１枚エナゾーンに置く]@：あなたの他の＜ブルアカ＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋3000する。" +
                "~{{C：[[シャドウ（パワー8000以下のシグニ）]]@@" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Isami Kaede");
        setDescription("en",
                "@E @[Put a <<Blue Archive>> card from your hand into your Ener Zone]@: Another target <<Blue Archive>> SIGNI on your field gets +3000 power until the end of your opponent's next end phase.~{{C: [[Shadow -- SIGNI with power 8000 or less]].@@" +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Kaede Isami");
        setDescription("en_fan",
                "@E @[Put 1 <<Blue Archive>> card from your hand into the ener zone]@: Target 1 of your other <<Blue Archive>> SIGNI, and until the end of your opponent's next turn, it gets +3000 power." +
                "~{{C: [[Shadow (SIGNI with power 8000 or less)]]@@" +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "勇美枫");
        setDescription("zh_simplified", 
                "@E 从手牌把<<ブルアカ>>牌1张放置到能量区:你的其他的<<ブルアカ>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+3000。\n" +
                "~{{C:[[暗影（力量8000以下的精灵）]]@@" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(3000);

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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex())).get();
            if(target != null) gainPower(target, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
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

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
