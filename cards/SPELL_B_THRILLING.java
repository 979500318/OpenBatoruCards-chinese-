package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class SPELL_B_THRILLING extends Card {
    
    public SPELL_B_THRILLING()
    {
        setImageSets("WXDi-P03-072");
        
        setOriginalName("THRILLING");
        setAltNames("スリリング Suriringu");
        setDescription("jp",
                "このスペルを使用する際、使用コストとして追加でエクシード４を支払ってもよい。\n\n" +
                "カードを２枚引き、手札を１枚捨てる。追加でエクシード４を支払っていた場合、代わりにカードを３枚引く。"
        );
        
        setName("en", "THRILLING");
        setDescription("en",
                "As you use this spell, you may pay Exceed 4 as an additional use cost.\n\n" +
                "Draw two cards and discard a card. If you paid the Exceed 4, instead draw three cards. "
        );
        
        setName("en_fan", "THRILLING");
        setDescription("en_fan",
                "While using this spell, you may pay an additional @[Exceed 4]@ for its use cost.\n\n" +
                "Draw 2 cards, and discard 1 card from your hand. If you additionally paid @[Exceed 4]@, instead draw 3 cards."
        );
        
		setName("zh_simplified", "THRILLING");
        setDescription("zh_simplified", 
                "这张魔法使用时，可以作为使用费用追加把@[超越 4]@支付。（从你的分身的下面把牌合计4张放置到分身废弃区）\n" +
                "抽2张牌，手牌1张舍弃。追加把@[超越 4]@支付过的场合，作为替代，抽3张牌。\n" +
                "（把@[超越 4]@支付，\n" +
                "@>:抽2张牌，手牌1张舍弃@@\n" +
                "被替代为抽3张牌）\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        
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
            if(!spell.hasPaidAdditionalCost())
            {
                draw(2);
                discard(1);
            } else {
                draw(3);
            }
        }
    }
}
