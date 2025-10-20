package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_CodeArtSCoreboard extends Card {

    public SIGNI_B1_CodeArtSCoreboard()
    {
        setImageSets("WX24-P1-066");

        setOriginalName("コードアート　Sコアボード");
        setAltNames("コードアートエスコアボード Koodo Aato Esu Koaboodo");
        setDescription("jp",
                "@E:あなたのデッキの一番上を公開する。そのカードがスペルの場合、カードを１枚引く。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Code Art S Coreboard");
        setDescription("en",
                "@E: Reveal the top card of your deck. If it is a spell, draw 1 card." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "必杀代号 记分板");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面公开。那张牌是魔法的场合，抽1张牌。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(3000);

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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null || cardIndex.getIndexedInstance().getTypeByRef() != CardType.SPELL || draw(1).get() == null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
