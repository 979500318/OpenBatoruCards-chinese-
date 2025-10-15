package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class LRIGA_K2_UrithCurse extends Card {

    public LRIGA_K2_UrithCurse()
    {
        setImageSets("WX24-P2-046");

        setOriginalName("ウリス・カース");
        setAltNames("ウリスカース Urisu Kaasu Ulith");
        setDescription("jp",
                "@E：ターン終了時まで、このルリグは@>@U：対戦相手のルリグかシグニ１体がアタックしたとき、対戦相手のデッキの上からカードを１０枚トラッシュに置く。@@を得る。"
        );

        setName("en", "Urith Curse");
        setDescription("en",
                "@E: Until end of turn, this LRIG gains:" +
                "@>@U: Whenever 1 of your opponent's LRIG or SIGNI attacks, put the top 10 cards of your opponent's deck into the trash."
        );

		setName("zh_simplified", "乌莉丝·诅咒");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，这只分身得到\n" +
                "@>@U :当对战对手的分身或精灵1只攻击时，从对战对手的牌组上面把10张牌放置到废弃区。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(4));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            millDeck(getOpponent(), 10);
        }
    }
}
