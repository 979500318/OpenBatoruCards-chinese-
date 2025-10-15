package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_DysprosiumNaturalSource extends Card {

    public SIGNI_G2_DysprosiumNaturalSource()
    {
        setImageSets("SPDi01-121");

        setOriginalName("羅原　Ｄｙ");
        setAltNames("ラゲンジスプロシウム Ragen Jisupuroshiumu");
        setDescription("jp",
                "@E：あなたのデッキの一番上を公開する。あなたの場にそのカードと共通する色を持つルリグがいる場合、【エナチャージ１】をする。"
        );

        setName("en", "Dysprosium, Natural Source");
        setDescription("en",
                "@E: Reveal the top card of your deck. If 1 of your LRIG shares a common color with that card, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "罗原 Dy");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面公开。你的场上持有与那张牌共通颜色的分身的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
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

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                if(new TargetFilter().own().anyLRIG().withColor(cardIndex.getIndexedInstance().getColor()).getValidTargetsCount() > 0)
                {
                    enerCharge(1);
                } else {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
    }
}
