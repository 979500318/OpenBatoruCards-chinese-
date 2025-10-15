package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R1_PteranodonPhantomDragon extends Card {

    public SIGNI_R1_PteranodonPhantomDragon()
    {
        setImageSets("WX24-P4-063");

        setOriginalName("幻竜　プテラノドン");
        setAltNames("ゲンリュウプテラノドン Genjuu Puteranodon");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Pteranodon, Phantom Dragon");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash."
        );

		setName("zh_simplified", "幻龙 无齿翼龙");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(1);
        setPower(2000);

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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor()))).get();
            trash(target);
        }
    }
}
