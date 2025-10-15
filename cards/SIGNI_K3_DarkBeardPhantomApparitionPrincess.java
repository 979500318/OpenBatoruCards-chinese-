package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_K3_DarkBeardPhantomApparitionPrincess extends Card {

    public SIGNI_K3_DarkBeardPhantomApparitionPrincess()
    {
        setImageSets("WX25-P2-062");

        setOriginalName("幻怪姫　ダークベアード");
        setAltNames("ゲンカイキダークベアード Genkaiki Daaku Beaado");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたがアーツを使用していた場合、ターン終了時まで、このシグニは[[アサシン（パワー5000以下のシグニ）]]を得る。\n" +
                "@E %K：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたのルリグトラッシュにあるアーツ１枚につき－1000する。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Dark Beard, Phantom Apparition Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you used an ARTS this turn, until end of turn, this SIGNI gains [[Assassin (SIGNI with power 5000 or less)]].\n" +
                "@E %K: Target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power for each ARTS in your LRIG trash." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "幻怪姬 黑暗贝亚德");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这个回合你把必杀使用过的场合，直到回合结束时为止，这只精灵得到[[暗杀（力量5000以下的精灵）]]。\n" +
                "@E %K:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的分身废弃区的必杀的数量，每有1张就-1000。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller())) > 0)
            {
                attachAbility(getCardIndex(), new StockAbilityAssassin(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexOpposite)
        {
            return cardIndexOpposite.getIndexedInstance().getPower().getValue() <= 5000 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, -1000 * new TargetFilter().own().ARTS().fromTrash(DeckType.LRIG).getValidTargetsCount(), ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
                gainPower(target, -15000, ChronoDuration.turnEnd());
            } else {
                draw(1);
            }
        }
    }
}
