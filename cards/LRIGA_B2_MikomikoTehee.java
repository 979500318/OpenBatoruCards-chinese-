package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B2_MikomikoTehee extends Card {

    public LRIGA_B2_MikomikoTehee()
    {
        setImageSets("WX24-P4-101");

        setOriginalName("みこみこ☆てへっ");
        setAltNames("ミコミコテヘッ Mikomiko Tehhe");
        setDescription("jp",
                "@E：対戦相手が手札を１枚捨てないかぎり、カードを２枚引く。\n" +
                "@E：対戦相手のシグニ１体を対象とし、それをダウンする。"
        );

        setName("en", "Mikomiko☆Tehee");
        setDescription("en",
                "@E: Draw 2 cards unless your opponent discards 1 card from their hand.\n" +
                "@E: Target 1 of your opponent's SIGNI, and down it."
        );

		setName("zh_simplified", "みこみこ☆呵呵");
        setDescription("zh_simplified", 
                "@E :如果对战对手不把手牌1张舍弃，那么抽2张牌。\n" +
                "@E :对战对手的精灵1只作为对象，将其横置。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MIKOMIKO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLUE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            if(discard(getOpponent(), 0,1).get() == null)
            {
                draw(2);
            }
        }
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
            down(target);
        }
    }
}
