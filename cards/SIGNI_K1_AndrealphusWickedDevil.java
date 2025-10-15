package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_AndrealphusWickedDevil extends Card {

    public SIGNI_K1_AndrealphusWickedDevil()
    {
        setImageSets("WX24-P4-085");

        setOriginalName("凶魔　アンドレアルフス");
        setAltNames("キョウマアンドレアルフス Kyouma Andorearufusu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの一番上のカードをトラッシュに置いてもよい。この方法でトラッシュに置かれたシグニのレベル１につき対戦相手のデッキの上からカードを１枚トラッシュに置く。"
        );

        setName("en", "Andrealphus, Wicked Devil");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may put the top card of your deck into the trash. For each level of the SIGNI put into the trash this way, put 1 card from your opponent's deck into the trash."
        );

		setName("zh_simplified", "凶魔 安德雷安富");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，可以把你的牌组最上面的牌放置到废弃区。依据这个方法放置到废弃区的精灵的等级的数量，每有1级就从对战对手的牌组上面把1张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }

        private void onAutoEff()
        {
            if(playerChoiceActivate())
            {
                CardIndex cardIndex = millDeck(1).get();
                
                if(cardIndex != null && CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()))
                {
                    millDeck(getOpponent(), cardIndex.getIndexedInstance().getLevelByRef());
                }
            }
        }
    }
}
