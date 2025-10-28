package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.stock.StockAbilityLancer;
import open.batoru.data.ability.stock.StockAbilityShoot;

public final class ARTS_GW_GreenGarbedHeart extends Card {

    public ARTS_GW_GreenGarbedHeart()
    {
        setImageSets("WX24-P4-008","WX24-P4-008U");

        setOriginalName("緑衣之心");
        setAltNames("グリーンストーリー Guriin Sutoorii Green Story");
        setDescription("jp",
                "あなたのデッキの上からカードを３枚見る。その中からシグニを好きな枚数場に出し、カードを好きな枚数手札に加え、残りをエナゾーンに置く。その後、あなたのシグニ１体を対象とし、ターン終了時まで、それは【シュート】を得る。あなたのシグニ１体を対象とし、ターン終了時まで、それは【ランサー】を得る。"
        );

        setName("en", "Green-Garbed Heart");
        setDescription("en",
                "Look at the top 3 cards of your deck. Put any number of SIGNI from among them onto the field, add any number of cards from among them to your hand, and put the rest into the ener zone. Then, target 1 of your SIGNI, and until end of turn, it gains [[Shoot]]. Then, target 1 of your SIGNI, and until end of turn, it gains [[Lancer]]."
        );

        setName("zh_simplified", "绿衣之心");
        setDescription("zh_simplified", 
                "从你的牌组上面看3张牌。从中把精灵任意张数出场，牌任意张数加入手牌，剩下的放置到能量区。然后，你的精灵1只作为对象，直到回合结束时为止，其得到[[击落]]。你的精灵1只作为对象，直到回合结束时为止，其得到[[枪兵]]。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN, CardColor.WHITE);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.color(CardColor.WHITE, 1));
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
            look(3);

            DataTable<CardIndex> data = playerTargetCard(0,getLookedCount(), new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable());
            putOnField(data);
            
            data = playerTargetCard(0,getLookedCount(), new TargetFilter(TargetHint.HAND).own().fromLooked());
            addToHand(data);
            
            putInEner(getCardsInLooked(getOwner()));
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            attachAbility(target, new StockAbilityShoot(), ChronoDuration.turnEnd());
            
            target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
        }
    }
}
