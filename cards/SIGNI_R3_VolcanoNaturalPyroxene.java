package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R3_VolcanoNaturalPyroxene extends Card {

    public SIGNI_R3_VolcanoNaturalPyroxene()
    {
        setImageSets("WX24-D2-20");

        setOriginalName("大装　ローメイル");
        setAltNames("タイソウローメイル Daisou Roomairu");
        setDescription("jp",
                "@E %R %R：対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。&E５枚以上@0代わりに対戦相手のシグニ１体を対象とし、それをバニッシュする。" +
                "~#どちらか１つを選ぶ。\n$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n$$2カードを１枚引く。"
        );

        setName("en", "Volcano, Natural Pyroxene");
        setDescription("en",
                "@E %R %R: Target 1 of your opponent's SIGNI with power 12000 or less, and banish it. &E5 or more@0 Instead, target 1 of your opponent's SIGNI, and banish it." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "罗辉石 火山石");
        setDescription("zh_simplified", 
                "@E %R %R对战对手的力量12000以下的精灵1只作为对象，将其破坏。&E5张以上@0作为替代，对战对手的精灵1只作为对象，将其破坏。\n" +
                "（你的分身废弃区有5张以上的必杀时，则&E5张以上@0后的文字变为有效）" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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

            EnterAbility enter = registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 2)), this::onEnterEff);
            enter.setRecollect(5);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            TargetFilter filter = new TargetFilter(TargetHint.BANISH).OP().SIGNI();
            if(!getAbility().isRecollectFulfilled()) filter = filter.withPower(0,12000);
            CardIndex target = playerTargetCard(filter).get();
            banish(target);
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                draw(1);
            }
        }
    }
}
