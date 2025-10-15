package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R2_HisuiNaturalWarStone extends Card {

    public SIGNI_R2_HisuiNaturalWarStone()
    {
        setImageSets("WX24-P1-061");

        setOriginalName("羅闘石　ヒスイ");
        setAltNames("ラトウセキヒスイ Ratouseki Hisui");
        setDescription("jp",
                "@E %R @[手札から＜宝石＞のシグニを１枚捨てる]@：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Hisui, Natural War Stone");
        setDescription("en",
                "@E %R @[Discard 1 <<Gem>> SIGNI from your hand]@: Target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );

		setName("zh_simplified", "罗斗石 翡翠");
        setDescription("zh_simplified", 
                "@E %R从手牌把<<宝石>>精灵1张舍弃:对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.RED, 1)), new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.GEM))), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
    }
}
