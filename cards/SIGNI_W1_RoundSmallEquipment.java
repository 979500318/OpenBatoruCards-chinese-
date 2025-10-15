package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_W1_RoundSmallEquipment extends Card {

    public SIGNI_W1_RoundSmallEquipment()
    {
        setImageSets("WX24-P1-051");

        setOriginalName("小装　ラウンド");
        setAltNames("ショウソウラウンド Shousou Raundo");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、次の対戦相手のターン終了時まで、このシグニのパワーを＋7000する。"
        );

        setName("en", "Round, Small Equipment");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, until the end of your opponent's next turn, this SIGNI gets +7000 power."
        );

		setName("zh_simplified", "小装 圆");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，直到下一个对战对手的回合结束时为止，这只精灵的力量+7000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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
            gainPower(getCardIndex(), 7000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
