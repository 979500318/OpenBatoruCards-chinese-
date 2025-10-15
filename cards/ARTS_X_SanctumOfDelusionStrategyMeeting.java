package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.ability.AbilityConst.Enter;

public final class ARTS_X_SanctumOfDelusionStrategyMeeting extends Card {

    public ARTS_X_SanctumOfDelusionStrategyMeeting()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-037");

        setOriginalName("虚妄のサンクトゥム攻略会議");
        setAltNames("キョモウノサンクトゥムコウリャクカイギ Kyomou no Sankutumu Kouryaku Kaigi");
        setDescription("jp",
                "あなたのデッキの上からカードを４枚見る。その中から＜ブルアカ＞のシグニ１枚を場に出し、残りを好きな順番でデッキの一番下に置く。そのシグニの@E能力は発動しない。"
        );

        setName("en", "Sanctum of Delusion Strategy Meeting");
        setDescription("en",
                "Look at the top 4 cards of your deck. Put 1 <<Blue Archive>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. That SIGNI's @E abilities don't activate."
        );

		setName("zh_simplified", "虚妄的圣所之塔攻略会议");
        setDescription("zh_simplified", 
                "从你的牌组上面看4张牌。从中把<<ブルアカ>>精灵1张出场，剩下的任意顺序放置到牌组最下面。那只精灵的@E能力不能发动。\n"
        );

        setType(CardType.ARTS);
        setUseTiming(UseTiming.ATTACK);

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
            look(4);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromLooked().playable()).get();
            putOnField(cardIndex, Enter.DONT_ACTIVATE);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
