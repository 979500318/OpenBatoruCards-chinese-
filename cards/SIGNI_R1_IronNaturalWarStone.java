package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R1_IronNaturalWarStone extends Card {

    public SIGNI_R1_IronNaturalWarStone()
    {
        setImageSets("WX24-D2-11");

        setOriginalName("羅闘石　アイロン");
        setAltNames("ラトウセキアイロン Ratouseki Airon");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたが赤のアーツを使用していた場合、対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Iron, Natural War Stone");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you used a red ARTS this turn, target 1 of your opponent's SIGNI with power 2000 or less, and banish it."
        );

		setName("zh_simplified", "罗斗石 铁");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合你把红色的必杀使用过的场合，对战对手的力量2000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller()) && event.getCaller().getColor().matches(CardColor.RED)) > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
                banish(target);
            }
        }
    }
}
