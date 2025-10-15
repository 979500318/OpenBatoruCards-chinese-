package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_MalayanTapirPhantomBeast extends Card {

    public SIGNI_K1_MalayanTapirPhantomBeast()
    {
        setImageSets("WX24-P1-081");

        setOriginalName("幻獣　マレーバク");
        setAltNames("ゲンジュウマレーバク Genjuu Mareebaku");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニのパワーが5000以上の場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。このシグニのパワーが10000以上の場合、代わりにターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Malayan Tapir, Phantom Beast");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if this SIGNI's power is 5000 or more, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power. If this SIGNI's power is 10000 or more, instead, until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "幻兽 马来貘");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这只精灵的力量在5000以上的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。这只精灵的力量在10000以上的场合，作为替代，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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
            if(getPower().getValue() >= 5000)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, getPower().getValue() < 10000 ? -2000 : -5000, ChronoDuration.turnEnd());
            }
        }
    }
}
