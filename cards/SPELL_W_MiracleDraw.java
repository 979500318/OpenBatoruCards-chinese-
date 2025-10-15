package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class SPELL_W_MiracleDraw extends Card {
    
    public SPELL_W_MiracleDraw()
    {
        setImageSets("WXDi-P03-054");
        
        setOriginalName("ミラクル・ドロー");
        setAltNames("ミラクルドロー Mirakuru Doroo");
        setDescription("jp",
                "このスペルを使用する際、使用コストとして追加でエクシード４を支払ってもよい。\n\n" +
                "あなたのデッキの上からカードを５枚見る。その中からカードを１枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。追加でエクシード４を支払っていた場合、代わりにその中からカードを２枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Miracle Draw");
        setDescription("en",
                "As you use this spell, you may pay Exceed 4 as an additional use cost. \n\n" +
                "Look at the top five cards of your deck. Add up to one card from among them to your hand and put the rest on the bottom of your deck in any order. If you paid the Exceed 4, instead add up to two cards from among them to your hand and put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Miracle Draw");
        setDescription("en_fan",
                "While using this spell, you may pay an additional @[Exceed 4]@ for its use cost.\n\n" +
                "Look at the top 5 cards of your deck. Add up to 1 card from among them to your hand, and put the rest on the bottom of your deck in any order. If you additionally paid @[Exceed 4]@, instead add up to 2 cards to your hand, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "奇迹·抽卡");
        setDescription("zh_simplified", 
                "这张魔法使用时，可以作为使用费用追加把@[超越 4]@支付。（从你的分身的下面把牌合计4张放置到分身废弃区）\n" +
                "从你的牌组上面看5张牌。从中把牌1张最多加入手牌，剩下的任意顺序放置到牌组最下面。追加把@[超越 4]@支付过的场合，作为替代，从中把牌2张最多加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            spell = registerSpellAbility(this::onSpellEff);
            spell.setAdditionalCost(new ExceedCost(4));
        }
        
        private void onSpellEff()
        {
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,spell.hasPaidAdditionalCost() ? 2 : 1, new TargetFilter(TargetHint.HAND).own().fromLooked());
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
