package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G2_MoosePhantomBeast extends Card {

    public SIGNI_G2_MoosePhantomBeast()
    {
        setImageSets("WX24-P4-079");

        setOriginalName("幻獣　ヘラジカ");
        setAltNames("ゲンジュウヘラジカ Genjuu Herajika");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの＜地獣＞のシグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000する。\n" +
                "@E %G %G：ターン終了時まで、このシグニは[[ランサー（パワー10000以下のシグニ）]]を得る。"
        );

        setName("en", "Moose, Phantom Beast");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your <<Earth Beast>> SIGNI, and until end of turn, it gets +5000 power.\n" +
                "@E %G %G: Until end of turn, this SIGNI gains [[Lancer (SIGNI with power 10000 or less)]]."
        );

		setName("zh_simplified", "幻兽 驼鹿");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的<<地獣>>精灵1只作为对象，直到回合结束时为止，其的力量+5000。\n" +
                "@E %G %G:直到回合结束时为止，这只精灵得到[[枪兵（力量10000以下的精灵）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(2);
        setPower(5000);

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

            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 2)), this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.EARTH_BEAST)).get();
            gainPower(target, 5000, ChronoDuration.turnEnd());
        }

        private void onEnterEff()
        {
            attachAbility(getCardIndex(), new StockAbilityLancer(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getPower().getValue() <= 10000 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
