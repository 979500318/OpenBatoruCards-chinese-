package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R1_CodeRideGoKart extends Card {

    public SIGNI_R1_CodeRideGoKart()
    {
        setImageSets("WDK01-017");

        setOriginalName("コードライド　ゴーカート");
        setAltNames("コードライドゴーカート Koodo Raido Gookaato");
        setDescription("jp",
                "@U：このシグニがドライブ状態になったとき、対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：カードを１枚引く。"
        );

        setName("en", "Code Ride Go-Kart");
        setDescription("en",
                "@U: When this SIGNI enters the drive state, target 1 of your opponent's SIGNI with power 3000 or less, and banish it." +
                "~#Draw 1 card."
        );

		setName("zh_simplified", "骑乘代号 卡丁车");
        setDescription("zh_simplified", 
                "@U :当这只精灵变为驾驶状态时，对战对手的力量3000以下的精灵1只作为对象，将其破坏。" +
                "~#抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(1);
        setPower(1000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.DRIVE, this::onAutoEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
            banish(target);
        }

        private void onLifeBurstEff()
        {
            draw(1);
        }
    }
}
