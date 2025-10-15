package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class SPELL_B_FEVER extends Card {

    public SPELL_B_FEVER()
    {
        setImageSets("WXDi-P11-070");

        setOriginalName("FEVER");
        setAltNames("フィーバー Fiibaa");
        setDescription("jp",
                "このスペルを使用する際、使用コストとして追加でエクシード７を支払ってもよい。\n\n" +
                "あなたのデッキの上からカードを３枚見る。その中から好きな枚数のカードを好きな順番でデッキの一番下に置き、残りを好きな順番でデッキの一番上に戻す。追加でエクシード７を支払っていた場合、カードを２枚引く。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをデッキの一番下に置く。"
        );

        setName("en", "Fever");
        setDescription("en",
                "As you use this spell, you may pay Exceed 7 as an additional use cost.\n\n" +
                "Look at the top three cards of your deck. Put any number of them on the bottom of your deck in any order and the rest on top of your deck in any order. If you paid the Exceed 7, draw two cards." +
                "~#You may discard a card. If you do, put target upped SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "FEVER");
        setDescription("en_fan",
                "While using this spell, you may pay an additional @[Exceed 7]@ for its use cost.\n\n" +
                "Look at the top 3 cards of your deck. Put any number of them on the bottom of your deck in any order, and return the rest to the top of your deck in any order. If you paid an additional @[Exceed 7]@, draw 2 cards." +
                "~#Target 1 of your opponent's upped SIGNI, and you may discard 1 card from your hand. If you do, put it on the bottom of their deck."
        );

		setName("zh_simplified", "FEVER");
        setDescription("zh_simplified", 
                "这张魔法使用时，可以作为使用费用追加把超越7支付。（从你的分身的下面把牌合计7张放置到分身废弃区）\n" +
                "从你的牌组上面看3张牌。从中把任意张数的牌任意顺序放置到牌组最下面，剩下的任意顺序返回牌组最上面。追加把超越7支付过的场合，抽2张牌。" +
                "~#对战对手的竖直状态的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);

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
            spell.setAdditionalCost(new ExceedCost(7));

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEff()
        {
            look(3);

            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.BOTTOM).own().fromLooked());
            returnToDeck(data, DeckPosition.BOTTOM);

            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
            
            if(spell.hasPaidAdditionalCost())
            {
                draw(2);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().upped()).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
    }
}
