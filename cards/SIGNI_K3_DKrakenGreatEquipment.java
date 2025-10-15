package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_K3_DKrakenGreatEquipment extends Card {

    public SIGNI_K3_DKrakenGreatEquipment()
    {
        setImageSets("SPDi43-09");
        setLinkedImageSets("SPDi43-04");

        setOriginalName("大装　Ｄクラーケン");
        setAltNames("タイソウディークラーケン Taisou Dii Kuraaken");
        setDescription("jp",
                "@C：このシグニは【ソウル】が付いているかぎり、[[シャドウ（レベル２以下のシグニ）]]を得る。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの場に《デウス・スリーNEO》がいる場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失い、それのパワーを－5000する。"
        );

        setName("en", "D Kraken, Great Equipment");
        setDescription("en",
                "@C: As long as this SIGNI has a [[Soul]] attached to it, this SIGNI gains [[Shadow (level 2 or lower SIGNI)]].\n" +
                "@U: At the beginning of your attack phase, if your LRIG is \"Deus Three NEO\", target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities, and it gets --5000 power."
        );

		setName("zh_simplified", "大装 D克拉肯");
        setDescription("zh_simplified", 
                "@C :这只精灵有[[灵魂]]附加时，得到[[暗影（等级2以下的精灵）]]。\n" +
                "@U :你的攻击阶段开始时，你的场上有《デウス・スリーNEO》的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onConstEffCond()
        {
            return getCardIndex().getIndexedInstance().getCardsUnderCount(CardUnderType.ATTACHED_SOUL) > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("デウス・スリーNEO"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null)
                {
                    disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
                    gainPower(target, -5000, ChronoDuration.turnEnd());
                }
            }
        }
    }
}
