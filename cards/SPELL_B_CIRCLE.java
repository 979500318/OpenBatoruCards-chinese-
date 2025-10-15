package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class SPELL_B_CIRCLE extends Card {

    public SPELL_B_CIRCLE()
    {
        setImageSets("WXK01-057");

        setOriginalName("ＣＩＲＣＬＥ");
        setAltNames("サークル Saakuru");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中からシグニを３枚まで場に出し、残りをトラッシュに置く。"
        );

        setName("en", "CIRCLE");
        setDescription("en",
                "Look at the top 5 cards of your deck. Put up to 3 SIGNI from among them onto the field, and put the rest into the trash."
        );

		setName("zh_simplified", "CIRCLE");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把精灵3张最多出场，剩下的放置到废弃区。\n"
        );

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 3));

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerSpellAbility(this::onSpellEff);
        }
        
        private void onSpellEff()
        {
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable());
            putOnField(data);
            
            trash(getCardsInLooked(getOwner()));
        }
    }
}
