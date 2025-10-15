package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_R1_AmethystNaturalWarStone extends Card {

    public SIGNI_R1_AmethystNaturalWarStone()
    {
        setImageSets("WX24-P1-058");

        setOriginalName("羅闘石　アメジスト");
        setAltNames("ラトウセキアメジスト Ratouseki Ameshisuto");
        setDescription("jp",
                "@E @[手札から＜宝石＞のシグニを１枚捨てる]@：対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Amethyst, Natural War Stone");
        setDescription("en",
                "@E @[Discard 1 <<Gem>> SIGNI from your hand]@: Target 1 of your opponent's SIGNI with power 5000 or less, and banish it."
        );

		setName("zh_simplified", "罗斗石 紫水晶");
        setDescription("zh_simplified", 
                "@E 从手牌把<<宝石>>精灵1张舍弃:对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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

            registerEnterAbility(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.GEM)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            banish(target);
        }
    }
}
