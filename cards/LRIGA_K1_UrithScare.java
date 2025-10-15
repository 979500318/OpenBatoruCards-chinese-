package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K1_UrithScare extends Card {

    public LRIGA_K1_UrithScare()
    {
        setImageSets("WXDi-P10-028");

        setOriginalName("ウリス・スケアー");
        setAltNames("ウリススケアー Urisu Sukeaa");
        setDescription("jp",
                "@E：対戦相手のレベル１のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E：対戦相手のデッキの上からカードを４枚トラッシュに置く。"
        );

        setName("en", "Urith Scare");
        setDescription("en",
                "@E: Vanish target level one SIGNI on your opponent's field.\n" +
                "@E: Put the top four cards of your opponent's deck into their trash."
        );
        
        setName("en_fan", "Urith Scare");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 1 SIGNI, and banish it.\n" +
                "@E: Put the top 4 cards of your opponent's deck into the trash."
        );

		setName("zh_simplified", "乌莉丝·鬼脸");
        setDescription("zh_simplified", 
                "@E :对战对手的等级1的精灵1只作为对象，将其破坏。\n" +
                "@E :从对战对手的牌组上面把4张牌放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(1)).get();
            banish(target);
        }
        private void onEnterEff2()
        {
            millDeck(getOpponent(), 4);
        }
    }
}
