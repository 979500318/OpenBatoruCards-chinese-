package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R2_ObsidianNaturalStone extends Card {

    public SIGNI_R2_ObsidianNaturalStone()
    {
        setImageSets("WX24-P4-065");

        setOriginalName("羅石　オブシディアン");
        setAltNames("ラセキオブシディアン Raseki Obushidian");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたが手札を２枚以上捨てていた場合、対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Obsidian, Natural Stone");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you discarded 2 or more cards from your hand this turn, target 1 of your opponent's SIGNI with power 5000 or less, and banish it."
        );

		setName("zh_simplified", "罗石 黑曜石");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合你把手牌2张以上舍弃过的场合，对战对手的力量3000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(2);
        setPower(5000);

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
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.DISCARD && isOwnCard(event.getCaller())) >= 2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
                banish(target);
            }
        }
    }
}
