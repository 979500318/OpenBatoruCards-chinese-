package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_RaiChan extends Card {

    public SIGNI_B2_RaiChan()
    {
        setImageSets("WX25-CP1-TK1A");

        setOriginalName("雷ちゃん");
        setAltNames("イカズチチャン Ikazuchi chan");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。このシグニのパワーが12000以上の場合、代わりにターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Rai-chan");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power. If this SIGNI's power is 12000 or more, until end of turn, it gets --5000 power instead."
        );

		setName("zh_simplified", "雷酱");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。这只精灵的力量在12000以上的场合，作为替代，直到回合结束时为止，其的力量-5000。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, getPower().getValue() < 12000 ? -3000 : -5000, ChronoDuration.turnEnd());
        }
    }
}
