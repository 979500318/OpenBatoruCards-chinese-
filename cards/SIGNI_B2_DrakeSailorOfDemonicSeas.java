package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B2_DrakeSailorOfDemonicSeas extends Card {

    public SIGNI_B2_DrakeSailorOfDemonicSeas()
    {
        setImageSets("WXK01-054");

        setOriginalName("魔海の船員　ドレイク");
        setAltNames("マカイノセンインドレイク Makai no Senin Doreiku");
        setDescription("jp",
                "@E @[手札を２枚捨てる]@：このターン終了時、カードを２枚引く。"
        );

        setName("en", "Drake, Sailor of Demonic Seas");
        setDescription("en",
                "@E @[Discard 2 cards from your hand]@: At the end of this turn, draw 2 cards."
        );

		setName("zh_simplified", "魔海的船员 德雷克");
        setDescription("zh_simplified", 
                "@E 手牌2张舍弃:这个回合结束时，抽2张牌。（即使回合结束时这只精灵不在场上也能抽牌）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new DiscardCost(2), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            callDelayedEffect(ChronoDuration.turnEnd(), () -> draw(2));
        }
    }
}
