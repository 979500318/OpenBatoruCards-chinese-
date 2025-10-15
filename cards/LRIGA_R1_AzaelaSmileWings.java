package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_R1_AzaelaSmileWings extends Card {

    public LRIGA_R1_AzaelaSmileWings()
    {
        setImageSets("WXDi-P16-038");

        setOriginalName("アザエラ「勇気の矢」");
        setAltNames("アザエラユウキノヤ Azaera Yuuki no Ya");
        setDescription("jp",
                "@E @[手札を４枚まで捨てる]@：この方法で捨てたカードの枚数に１を加えた枚数のカードを引く。"
        );

        setName("en", "Azaela \"Smiling Wings\"");
        setDescription("en",
                "@E @[Discard up to four cards]@: Draw cards equal to the number of cards discarded this way plus one."
        );
        
        setName("en_fan", "Azaela [Smile Wings]");
        setDescription("en_fan",
                "@E @[Discard up to 4 cards from your hand]@: Draw cards equal to the number of cards discarded this way plus 1."
        );

		setName("zh_simplified", "阿左伊来「微笑的翼」");
        setDescription("zh_simplified", 
                "@E 手牌4张最多舍弃:抽这个方法舍弃的牌的张数加1的张数的牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AZAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.RED);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(0,4), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            int count = getAbility().getCostPaidData().size();
            if(count == 1 && getAbility().getCostPaidData().get() == null) count = 0;
            draw(count+1);
        }
    }
}
