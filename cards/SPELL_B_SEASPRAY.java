package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.cost.DiscardCost;

public final class SPELL_B_SEASPRAY extends Card {

    public SPELL_B_SEASPRAY()
    {
        setImageSets("WX24-P3-079");

        setOriginalName("SEA SPRAY");
        setAltNames("シースプレー Shii Supuree");
        setDescription("jp",
                "カードを３枚引く。あなたは手札から＜水獣＞のシグニを１枚捨てないかぎり手札を２枚捨てる。" +
                "~#：対戦相手のシグニを２体まで対象とし、それらをダウンする。"
        );

        setName("en", "SEA SPRAY");
        setDescription("en",
                "Draw 3 cards. Discard 2 cards from your hand unless you discard 1 <<Water Beast>> SIGNI from your hand." +
                "~#Target up to 2 of your opponent's SIGNI, and down them."
        );

		setName("zh_simplified", "SEA SPRAY");
        setDescription("zh_simplified", 
                "抽3张牌。你如果不从手牌把<<水獣>>精灵1张舍弃，那么把手牌2张舍弃。" +
                "~#对战对手的精灵2只最多作为对象，将这些横置。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerSpellAbility(this::onSpellEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEff()
        {
            draw(3);

            pay(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.WATER_BEAST)), new DiscardCost(2));
        }

        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
    }
}
