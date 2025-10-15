package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.Cost;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.Enter;

public final class LRIGA_W2_BrilliantGabriela extends Card {

    public LRIGA_W2_BrilliantGabriela()
    {
        setImageSets("WXDi-P16-035");

        setOriginalName("堂々！！ガブリエラ");
        setAltNames("ドウドウガブリエラ Doudou Gaburiera");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中からカードを１枚まで手札に加え、シグニを１枚まで場に出し、残りを好きな順番でデッキの一番下に置く。そのシグニの@E能力は発動しない。"
        );

        setName("en", "Gabriela, Graceful!!");
        setDescription("en",
                "@E: Look at the top five cards of your deck. Add up to one card from among them to your hand. Put up to one SIGNI from the remaining cards onto your field, and put the rest on the bottom of your deck in any order. The @E abilities of SIGNI put onto the field this way do not activate."
        );
        
        setName("en_fan", "Brilliant!! Gabriela");
        setDescription("en_fan",
                "@E: Look at the top 5 cards of your deck. Add up to 1 card from among them to your hand, put up to 1 SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. That SIGNI's @E abilities don't activate."
        );

		setName("zh_simplified", "堂堂！！哲布伊来");
        setDescription("zh_simplified", 
                "@E 从你的牌组上面看5张牌。从中把牌1张最多加入手牌，精灵1张最多出场，剩下的任意顺序放置到牌组最下面。那只精灵的@E能力不能发动。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.GABRIELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable()).get();
            putOnField(cardIndex, Enter.DONT_ACTIVATE);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
