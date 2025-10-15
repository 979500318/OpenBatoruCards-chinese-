package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityLancer;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class SIGNI_G3_SeiryuPhantomBeastDeity extends Card {

    public SIGNI_G3_SeiryuPhantomBeastDeity()
    {
        setImageSets("WX24-D4-20");

        setOriginalName("幻獣神　セイリュ");
        setAltNames("ゲンジュウシンセイリュ Genjuushin Seiryu");
        setDescription("jp",
                "@E %G %G：ターン終了時まで、このシグニは【ランサー】を得る。&E５枚以上@0代わりにターン終了時まで、このシグニは【Ｓランサー】を得る。" +
                "~#どちらか１つを選ぶ。\n$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n$$2【エナチャージ１】"
        );

        setName("en", "Seiryu, Phantom Beast Deity");
        setDescription("en",
                "@E %G %G: Until end of turn, this SIGNI gains [[Lancer]]. &E5 or more@0 Instead, until end of turn, this SIGNI gains [[S Lancer]]." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "幻兽神 青龙");
        setDescription("zh_simplified", 
                "@E %G %G直到回合结束时为止，这只精灵得到[[枪兵]]。&E5张以上@0作为替代，直到回合结束时为止，这只精灵得到[[S枪兵]]。（当持有[[S枪兵]]的精灵战斗把精灵破坏时，对战对手有生命护甲的场合，将其1张击溃。没有的场合，给予对战对手伤害）" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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

            EnterAbility enter = registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 2)), this::onEnterEff);
            enter.setRecollect(5);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            attachAbility(getCardIndex(), !getAbility().isRecollectFulfilled() ? new StockAbilityLancer() : new StockAbilitySLancer(), ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
