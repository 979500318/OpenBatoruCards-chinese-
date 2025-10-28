package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.DataTable;
import open.batoru.data.CardDataImageSet.Mask;

public final class ARTS_G_OverAndOverAndOverAgain extends Card {

    public ARTS_G_OverAndOverAndOverAgain()
    {
        setImageSets("WX24-D4-05", Mask.IGNORE+"SPDi37-14");

        setOriginalName("再四再五");
        setAltNames("ウェイキングアップ Weikingu Appu Waking Up");
        setDescription("jp",
                "対戦相手のパワー10000以上のシグニ１体を対象とし、それをバニッシュする。あなたのエナゾーンからカードを３枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "Over and Over and Over Again");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 10000 or more, and banish it. Target up to 3 cards from your ener zone, and add them to your hand."
        );

        setName("zh_simplified", "再四再五");
        setDescription("zh_simplified", 
                "对战对手的力量10000以上的精灵1只作为对象，将其破坏。从你的能量区把牌3张最多作为对象，将这些加入手牌。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            banish(target);

            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().fromEner());
            addToHand(data);
        }
    }
}

