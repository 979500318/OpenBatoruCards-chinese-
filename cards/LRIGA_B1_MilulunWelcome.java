package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_MilulunWelcome extends Card {

    public LRIGA_B1_MilulunWelcome()
    {
        setImageSets("WXDi-P14-023");

        setOriginalName("ミルルン☆ウェルカム");
        setAltNames("ミルルンウェルカム Mirurun Uerukamu");
        setDescription("jp",
                "@E：カードを３枚引く。あなたは手札からスペルを１枚捨てないかぎり手札を２枚捨てる。"
        );

        setName("en", "Milulun ☆ Welcome");
        setDescription("en",
                "@E: Draw three cards. Discard two cards unless you discard a spell."
        );
        
        setName("en_fan", "Milulun☆Welcome");
        setDescription("en_fan",
                "@E: Draw 3 cards. Discard 2 cards from your hand unless you discard 1 spell from your hand."
        );

		setName("zh_simplified", "米璐璐恩☆欢迎");
        setDescription("zh_simplified", 
                "@E :抽3张牌。你如果不从手牌把魔法1张舍弃，那么把手牌2张舍弃。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MILULUN);
        setColor(CardColor.BLUE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            draw(3);
            
            if(discard(0,1, new TargetFilter().spell()).get() == null)
            {
                discard(2);
            }
        }
    }
}
