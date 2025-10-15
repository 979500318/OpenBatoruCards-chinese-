package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B3_OctopusSuctionCupsOfDemonicSeas extends Card {

    public SIGNI_B3_OctopusSuctionCupsOfDemonicSeas()
    {
        setImageSets("WXK01-084");

        setOriginalName("魔海の吸盤　オクトパス");
        setAltNames("マカイノキュウバンオクトパス Makai no Kyuuban Okutopasu");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたの手札が１枚以下でこのシグニが中央のシグニゾーンにある場合、カードを１枚引く。"
        );

        setName("en", "Octopus, Suction Cups of Demonic Seas");
        setDescription("en",
                "@U: At the end of your turn, if there are 1 or less cards in your hand and this SIGNI is in your center SIGNI zone, draw 1 card."
        );

		setName("zh_simplified", "魔海的吸盘 巨型章鱼");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，你的手牌在1张以下且这只精灵在中央的精灵区的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(7000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getHandCount(getOwner()) <= 1 && getCardIndex().getLocation() == CardLocation.SIGNI_CENTER)
            {
                draw(1);
            }
        }
    }
}
