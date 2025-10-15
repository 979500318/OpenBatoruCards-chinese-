package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;

public final class LRIGA_W2_AssistLizeLevel2 extends Card {
    
    public LRIGA_W2_AssistLizeLevel2()
    {
        setImageSets("WXDi-D02-18AT");
        
        setOriginalName("【アシスト】リゼ　レベル２");
        setAltNames("アシストリゼレベルニ Ashisuto Rize Reberu Ni Assist Lize");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中から＜バーチャル＞のシグニを２枚まで場に出し、残りを好きな順番でデッキの一番下に置く。それらのシグニの@E能力は発動しない。"
        );
        
        setName("en", "[Assist] Lize, Level 2");
        setDescription("en",
                "@E: Look at the top five cards of your deck. Put up to two white <<Virtual>> SIGNI from among them onto the field. Put the rest on the bottom of your deck in any order. The @E abilities of SIGNI put onto your field this way do not activate."
        );
        
        setName("en_fan", "[Assist] Lize Level 2");
        setDescription("en_fan",
                "@E: Look at the top 5 cards of your deck. Put up to 2 white <<Virtual>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. The @E abilities of these SIGNI don't activate."
        );
        
		setName("zh_simplified", "【支援】莉泽 等级2");
        setDescription("zh_simplified", 
                "@E 从你的牌组上面看5张牌。从中把<<バーチャル>>精灵2张最多出场，剩下的任意顺序放置到牌组最下面。这些精灵的@E能力不能发动。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LIZE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(4));
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
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.WHITE).withClass(CardSIGNIClass.VIRTUAL).fromLooked().playable());
            putOnField(data, Enter.DONT_ACTIVATE);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
