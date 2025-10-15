package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K2_CosmoWickedDevil extends Card {

    public SIGNI_K2_CosmoWickedDevil()
    {
        setImageSets("WX24-D5-15");

        setOriginalName("凶魔　コスモ");
        setAltNames("キョウマコスモ Kyouma Kosumo");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のデッキの上からカードを２枚トラッシュに置く。あなたのトラッシュに黒のカードが１０枚以上ある場合、代わりに対戦相手のデッキの上からカードを４枚トラッシュに置く。"
        );

        setName("en", "Cosmo, Wicked Devil");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, put the top 2 cards of your opponent's deck into the trash. If you there are 10 or more black cards in your trash, instead put the top 4 cards of your opponent's deck into the trash."
        );

		setName("zh_simplified", "凶魔 科思莫");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从对战对手的牌组上面把2张牌放置到废弃区。你的废弃区的黑色的牌在10张以上的场合，作为替代，从对战对手的牌组上面把4张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }

        private void onAutoEff()
        {
            millDeck(getOpponent(), new TargetFilter().own().withColor(CardColor.BLACK).fromTrash().getValidTargetsCount() < 10 ? 2 : 4);
        }
    }
}
