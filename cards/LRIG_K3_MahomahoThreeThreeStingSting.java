package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_K3_MahomahoThreeThreeStingSting extends Card {

    public LRIG_K3_MahomahoThreeThreeStingSting()
    {
        setImageSets("SPDi43-02", "SPDi43-02P");

        setOriginalName("まほまほ☆さんさんちくちく");
        setAltNames("マホマホサンサンチクチク Mahomaho Sansan Chikuchiku");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手が手札を２枚捨てないかぎり、あなたは以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のデッキの上からカードを８枚トラッシュに置く。\n" +
                "$$2対戦相手のライフクロスが０枚の場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、そのパワーを－10000する。\n" +
                "@A $G1 @[@|永遠♡不滅☆宣言|@]@ %K0：対戦相手は手札をすべてルリグゾーンに裏向きで置く。ターン終了時、対戦相手はそれらのカードを手札に加える。"
        );

        setName("en", "Mahomaho☆Three Three Sting Sting");
        setDescription("en",
                "@U: At the beginning of your attack phase, unless your opponent discards 2 cards from their hand, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Put the top 8 cards of your opponent's deck into the trash.\n" +
                "$$2 If your opponent has 0 life cloth, target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power.\n" +
                "@A $G1 @[@|Eternal♡Immortal☆Declaration|@]@ %K0: Your opponent puts all cards from their hand onto the LRIG zone face down. At the end of the turn, your opponent adds those cards to their hand."
        );

		setName("zh_simplified", "真帆帆☆三重混编");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，如果对战对手不把手牌2张舍弃，那么你从以下的2种选1种。\n" +
                "$$1 从对战对手的牌组上面把8张牌放置到废弃区。\n" +
                "$$2 对战对手的生命护甲在0张的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n" +
                "@A $G1 永远♡不灭☆宣言%K0:对战对手把手牌全部里向放置到分身区。回合结束时，对战对手把这些牌加入手牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MAHOMAHO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

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

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("EternalImmortalDeclaration");
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(getOpponent(), 0,2, ChoiceLogic.BOOLEAN).get() == null)
            {
                if(playerChoiceMode() == 1)
                {
                    millDeck(getOpponent(), 8);
                } else {
                    if(getLifeClothCount(getOpponent()) == 0)
                    {
                        CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                        gainPower(target, -10000, ChronoDuration.turnEnd());
                    }
                }
            }
        }

        private void onActionEff()
        {
            DataTable<CardIndex> data = getCardsInHand(getOpponent());
            if(putOnZone(data, CardLocation.LRIG, CardUnderType.ZONE_GENERIC) > 0)
            {
                callDelayedEffect(ChronoDuration.turnEnd(), () -> addToHand(data.andRemoveIf(cardIndex -> cardIndex.getLocation() != CardLocation.LRIG)));
            }
        }
    }
}

