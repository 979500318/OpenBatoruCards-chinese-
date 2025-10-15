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
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;

import java.util.List;

public final class SIGNI_R3_CodeAccelHaulTruck extends Card {

    public SIGNI_R3_CodeAccelHaulTruck()
    {
        setImageSets("WX24-P4-046");

        setOriginalName("コードアクセル　ホウルトラック");
        setAltNames("コードアクセルホウルトラック Koodo Akuseru Houru Torakku");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの手札とエナゾーンからカードを合計３枚までこのシグニの下に置く。\n" +
                "@U：このシグニがアタックしたとき、このシグニの下からそれぞれレベルの異なるシグニ３枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、このシグニは【アサシン】を得る。\n" +
                "@E：&E４枚以上@0あなたのトラッシュからシグニ１枚を対象とし、それをこのシグニの下に置く。"
        );

        setName("en", "Code Accel Haul Truck");
        setDescription("en",
                "@U: At the beginning of your attack phase, put up to 3 cards from your hand and/or ener zone under this SIGNI.\n" +
                "@U: Whenever this SIGNI attacks, you may put 3 SIGNI with different levels from under this SIGNI into the trash. If you do, until end of turn, this SIGNI gains [[Assassin]].\n" +
                "@E: &E4 or more@0 Target 1 SIGNI from your trash, and put it under this SIGNI."
        );

		setName("zh_simplified", "加速代号 非公路矿用自卸车");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从你的手牌和能量区把牌合计3张最多放置到这只精灵的下面。\n" +
                "@U :当这只精灵攻击时，可以从这只精灵的下面把等级不同的精灵3张放置到废弃区。这样做的场合，直到回合结束时为止，这只精灵得到[[暗杀]]。\n" +
                "@E &E4张以上@0从你的废弃区把精灵1张作为对象，将其放置到这只精灵的下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            
            registerEnterAbility(this::onEnterEff).setRecollect(4);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.UNDER).own().fromLocation(CardLocation.ENER, CardLocation.HAND));
            attach(getCardIndex(), data, CardUnderType.UNDER_GENERIC);
        }

        private void onAutoEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().SIGNI().under(getCardIndex()), this::onAutoEff2TargetCond);
            if(trash(data) == 3)
            {
                attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
        }
        private boolean onAutoEff2TargetCond(List<CardIndex> pickedCards)
        {
            return pickedCards.size() == 3 && pickedCards.stream().mapToInt(c -> c.getIndexedInstance().getLevel().getValue()).distinct().count() == 3;
        }
        
        private void onEnterEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.UNDER).own().SIGNI().fromTrash()).get();
                attach(getCardIndex(), target, CardUnderType.UNDER_GENERIC);
            }
        }
    }
}
