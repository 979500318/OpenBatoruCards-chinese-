package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_UrithAfflict extends Card {

    public LRIGA_K2_UrithAfflict()
    {
        setImageSets("WXDi-P10-032");

        setOriginalName("ウリス・アフリクト");
        setAltNames("ウリスアフリクト Urisu Afurikuto");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたのトラッシュにあるカード１枚につき－1000する。\n" +
                "@E %X：各プレイヤーは自分のデッキの上からカードを４枚トラッシュに置く。"
        );

        setName("en", "Urith Afflict");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --1000 power for each card in your trash until end of turn.\n" +
                "@E %X: Each player puts the top four cards of their deck into their trash."
        );
        
        setName("en_fan", "Urith Afflict");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power for each card in your trash.\n" +
                "@E %X: Each player puts the top 4 cards of their deck into the trash."
        );

		setName("zh_simplified", "乌莉丝·折磨");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的废弃区的牌的数量，每有1张就-1000。\n" +
                "@E %X:各玩家从自己的牌组上面把4张牌放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -1000 * getTrashCount(getOwner()), ChronoDuration.turnEnd());
        }
        private void onEnterEff2()
        {
            millDeck(4);
            millDeck(getOpponent(), 4);
        }
    }
}
