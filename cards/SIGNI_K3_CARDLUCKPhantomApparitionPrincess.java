package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_K3_CARDLUCKPhantomApparitionPrincess extends Card {

    public SIGNI_K3_CARDLUCKPhantomApparitionPrincess()
    {
        setImageSets("WX25-P1-062", "WX25-P1-062U");
        setLinkedImageSets("WX25-P1-034");

        setOriginalName("幻怪姫　CARD LUCK");
        setAltNames("ゲンカイヒメカードラック Genkaihime Kaado Rakku");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたがアーツを使用していた場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のデッキの上からカードを４枚トラッシュに置く。\n" +
                "$$2あなたのエナゾーンから＜怪異＞のシグニ２枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、このシグニは[[アサシン（パワー12000以下のシグニ）]]を得る。\n" +
                "@E：あなたの場に《創造の針姫　ヤミノ＝Ⅲ》がいる場合、あなたのトラッシュから黒の＜怪異＞のシグニ１枚を対象とし、それをエナゾーンに置く。"
        );

        setName("en", "CARD LUCK, Phantom Apparition Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you used ARTS this turn, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Put the top 4 cards of your opponent's deck into the trash.\n" +
                "$$2 You may put 2 <<Apparition>> SIGNI from your ener zone into the trash. If you do, until end of turn, this SIGNI gains [[Assassin (SIGNI with power 12000 or less)]].\n" +
                "@E: If your LRIG is \"Yamino-III, Needle Princess of Creation\", target 1 black <<Apparition>> SIGNI from your trash, and put it into the ener zone."
        );

		setName("zh_simplified", "幻怪姬 CARD LUCK");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这个回合你把必杀使用过的场合，从以下的2种选1种。\n" +
                "$$1 从对战对手的牌组上面把4张牌放置到废弃区。\n" +
                "$$2 可以从你的能量区把<<怪異>>精灵2张放置到废弃区。这样做的场合，直到回合结束时为止，这只精灵得到[[暗杀（力量12000以下的精灵）]]。\n" +
                "@E 你的场上有《創造の針姫:ヤミノ＝Ⅲ》的场合，从你的废弃区把黑色的<<怪異>>精灵1张作为对象，将其放置到能量区。\n"
        );

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

            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller())) > 0)
            {
                if(playerChoiceMode() == 1)
                {
                    millDeck(getOpponent(), 4);
                } else {
                    DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.APPARITION).fromEner());
                    
                    if(trash(data) == 2)
                    {
                        attachAbility(getCardIndex(), new StockAbilityAssassin(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
                    }
                }
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexOpposite)
        {
            return cardIndexOpposite.getIndexedInstance().getPower().getValue() <= 12000 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onEnterEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("創造の針姫　ヤミノ＝Ⅲ"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).own().SIGNI().withColor(CardColor.BLACK).withClass(CardSIGNIClass.APPARITION).fromTrash()).get();
                putInEner(target);
            }
        }
    }
}
