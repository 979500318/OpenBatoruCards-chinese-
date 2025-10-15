package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class SPELL_R_PrankPrep extends Card {

    public SPELL_R_PrankPrep()
    {
        setImageSets("WX24-P3-072");

        setOriginalName("悪戯の仕込");
        setAltNames("イタズラノシコミ Itazura no Shikomi");
        setDescription("jp",
                "あなたのデッキの上からカードを３枚見る。その中からカードを１枚まで【マジックボックス】としてあなたのシグニゾーンに設置し、残りを好きな順番でデッキの一番下に置く。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Prank Prep");
        setDescription("en",
                "Look at the top 3 cards of your deck. Put up to 1 card from among them onto 1 of your SIGNI zones as a [[Magic Box]], and put the rest on the bottom of your deck in any order." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );

		setName("zh_simplified", "恶戏的准备");
        setDescription("zh_simplified", 
                "从你的牌组上面看3张牌。从中把牌1张最多作为[[魔术箱]]在你的精灵区设置，剩下的任意顺序放置到牌组最下面。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.RED);

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
            look(3);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ZONE).own().fromLooked()).get();
            putAsMagicBox(cardIndex);

            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
