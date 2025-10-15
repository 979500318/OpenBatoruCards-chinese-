package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.*;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_K3_NanashiMemoriaNaturalBacteriaPrincess extends Card {

    public SIGNI_K3_NanashiMemoriaNaturalBacteriaPrincess()
    {
        setImageSets("WXDi-P09-046", "WXDi-P09-046P");

        setOriginalName("羅菌姫　ナナシ//メモリア");
        setAltNames("ラキンヒメナナシメモリア Rakinhime Nanashi Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのデッキの一番上のカードをこのシグニの下に置く。\n" +
                "@U：このシグニがアタックしたとき、対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらのパワーをそれぞれこのシグニの下にあるカード１枚につき－3000する。\n" +
                "@A $T1 #C #C：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Nanashi//Memoria, Bacteria Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, put the top card of your deck under this SIGNI. \n" +
                "@U: Whenever this SIGNI attacks, up to two target SIGNI on your opponent's field get --3000 power for each card underneath this SIGNI until end of turn.\n" +
                "@A $T1 #C #C: Target SIGNI on your opponent's field gets --5000 power until end of turn."
        );
        
        setName("en_fan", "Nanashi//Memoria, Natural Bacteria Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, put the top card of your deck under this SIGNI.\n" +
                "@U: Whenever this SIGNI attacks, target up to 2 of your opponent's SIGNI, and until end of turn, each of them gets --3000 power for every card under this SIGNI.\n" +
                "@A $T1 #C #C: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "罗菌姬 无名//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的牌组最上面的牌放置到这只精灵的下面。（表向放置）\n" +
                "@U :当这只精灵攻击时，对战对手的精灵2只最多作为对象，直到回合结束时为止，这些的力量依据这只精灵的下面的牌的数量，每有1张就-3000。\n" +
                "@A $T1 #C #C:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);

            ActionAbility act = registerActionAbility(new CoinCost(2), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            attach(getCardIndex(), CardLocation.DECK_MAIN, CardUnderType.UNDER_GENERIC);
        }
        
        private void onAutoEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.MINUS).OP().SIGNI());
            gainPower(data, -3000 * getCardsUnderCount(CardUnderCategory.UNDER), ChronoDuration.turnEnd());
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());
        }
    }
}
