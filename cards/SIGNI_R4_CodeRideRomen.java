package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R4_CodeRideRomen extends Card {

    public SIGNI_R4_CodeRideRomen()
    {
        setImageSets("WDK01-012");

        setOriginalName("コードライド　ロメン");
        setAltNames("コードライドロメン Koodo Raido Romen");
        setDescription("jp",
                "@U：このシグニがドライブ状態になったとき、対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Code Ride Romen");
        setDescription("en",
                "@U: When this SIGNI enters the drive state, target 1 of your opponent's SIGNI, and banish it."
        );

		setName("zh_simplified", "骑乘代号 路面电车");
        setDescription("zh_simplified", 
                "@U :当这只精灵变为驾驶状态时，对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(4);
        setPower(10000);

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
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
    }
}
