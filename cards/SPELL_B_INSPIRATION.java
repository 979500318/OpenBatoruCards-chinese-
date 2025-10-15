package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class SPELL_B_INSPIRATION extends Card {

    public SPELL_B_INSPIRATION()
    {
        setImageSets("WXDi-P15-067");

        setOriginalName("INSPIRATION");
        setAltNames("インスピレーション Insupireeshon");
        setDescription("jp",
                "カードを２枚引く。あなたの手札から＜解放派＞のシグニ１枚をあなたの＜解放派＞のシグニ１体の下に置いてもよい。" +
                "~#：対戦相手のシグニを２体まで対象とし、それらをダウンする。"
        );

        setName("en", "INSPIRATION");
        setDescription("en",
                "Draw two cards. You may put a <<Liberation Division>> SIGNI from your hand under a <<Liberation Division>> SIGNI on your field." +
                "~#Down up to two target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "INSPIRATION");
        setDescription("en_fan",
                "Draw 2 cards. You may put 1 <<Liberation Faction>> SIGNI from your hand under 1 <<Liberation Faction>> SIGNI on your field." +
                "~#Target up to 2 of your opponent's SIGNI, and down them."
        );

		setName("zh_simplified", "INSPIRATION");
        setDescription("zh_simplified", 
                "抽2张牌。可以从你的手牌把<<解放派>>精灵1张放置到你的<<解放派>>精灵1只的下面。" +
                "~#对战对手的精灵2只最多作为对象，将这些#D。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            draw(2);
            
            CardIndex cardIndexFromHand = playerTargetCard(0,1, new TargetFilter(TargetHint.UNDER).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).fromHand()).get();
            
            if(cardIndexFromHand != null)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.UNDER).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION)).get();
                attach(cardIndex,cardIndexFromHand, CardUnderType.UNDER_GENERIC);
            }
        }

        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
    }
}
