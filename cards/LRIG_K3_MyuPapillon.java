package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class LRIG_K3_MyuPapillon extends Card {

    public LRIG_K3_MyuPapillon()
    {
        setImageSets("WX25-P2-030", "WX25-P2-030U");

        setOriginalName("ミュウ＝パピヨン");
        setAltNames("ミュウパピヨン Myu Papiyon");
        setDescription("jp",
                "@U $TO $T2：：あなたの＜凶蟲＞のシグニ１体が場に出たとき、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のデッキの上からカードを２枚トラッシュに置く。\n" +
                "$$2対戦相手のシグニ１体を対象とし、対戦相手のトラッシュからカード１枚をそれの【チャーム】にする。\n" +
                "@A $G1 @[@|サイレント|@]@ %K0：次の対戦相手のターン終了時まで、このルリグは@>@U：対戦相手のシグニ１体が場に出たとき、ターン終了時まで、そのシグニは能力を失う。@@を得る。"
        );

        setName("en", "Myu=Papillon");
        setDescription("en",
                "@U $TO $T2: When 1 of your <<Misfortune Insect>> SIGNI enters the field, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Put the top 2 cards of your opponent's deck into the trash.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and attach 1 card from your opponent's trash to it as a [[Charm]].\n" +
                "@A $G1 @[@|Silent|@]@ %K0: Until the end of your opponent's next turn, this LRIG gains:" +
                "@>@U: Whenever your opponent's SIGNI enters the field, until end of turn, that SIGNI loses its abilities."
        );

		setName("zh_simplified", "缪＝蝶耳");
        setDescription("zh_simplified", 
                "@U $TO $T2 :当你的<<凶蟲>>精灵1只出场时，从以下的2种选1种。\n" +
                "$$1 从对战对手的牌组上面把2张牌放置到废弃区。\n" +
                "$$2 对战对手的精灵1只作为对象，从对战对手的废弃区把1张牌作为其的[[魅饰]]。\n" +
                "@A $G1 寂静%K0:直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@U :当对战对手的精灵1只出场时，直到回合结束时为止，那只精灵的能力失去。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MYU);
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

            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 2);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Silent");
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.MISFORTUNE_INSECT) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceMode() == 1)
            {
                millDeck(getOpponent(), 2);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ATTACH).OP().SIGNI().attachable(CardUnderType.ATTACHED_CHARM)).get();

                if(target != null)
                {
                    CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ATTACH).OP().fromTrash()).get();
                    attach(target, cardIndex, CardUnderType.ATTACHED_CHARM);
                }
            }
        }

        private void onActionEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ENTER, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            ChronoRecord record = new ChronoRecord(ChronoDuration.nextTurnEnd(getOpponent()));
            CardLocation[] locations = new CardLocation[]{CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT};
            for(CardLocation location : locations) GFX.attachToChronoRecord(record, new GFXZoneUnderIndicator(getOpponent(),location, "web", 0, new int[]{140,140,140}));
            
            attachAbility(getCardIndex(), attachedAuto, record);
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            disableAllAbilities(caller, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
    }
}
