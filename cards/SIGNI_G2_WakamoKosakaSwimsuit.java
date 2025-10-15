package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G2_WakamoKosakaSwimsuit extends Card {

    public SIGNI_G2_WakamoKosakaSwimsuit()
    {
        setImageSets("WX25-CP1-081");

        setOriginalName("狐坂ワカモ(水着)");
        setAltNames("コサカワカモミズギ Kosaka Wakamo Mizugi");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのエナゾーンから＜ブルアカ＞のカード２枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、このシグニは[[ランサー（パワー10000以下のシグニ）]]と@>@U：このシグニがシグニ１体とバトルしたとき、ターン終了時まで、そのシグニは能力を失う。@@を得る。" +
                "~{{C：このシグニのパワーは＋4000される。"
        );

        setName("en", "Kosaka Wakamo (Swimsuit)");

        setName("en_fan", "Wakamo Kosaka (Swimsuit)");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may put 2 <<Blue Archive>> cards from your ener zone into the trash. If you do, until end of turn, this SIGNI gains [[Lancer (SIGNI with power 10000 or less)]] and:" +
                "@>@U: Whenever this SIGNI battles a SIGNI, until end of turn, that SIGNI loses its abilities.@@" +
                "~{{C: This SIGNI gets +4000 power."
        );

		setName("zh_simplified", "狐坂若藻(泳装)");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以从你的能量区把<<ブルアカ>>牌2张放置到废弃区。这样做的场合，直到回合结束时为止，这只精灵得到[[枪兵（力量10000以下的精灵）]]和\n" +
                "@>@U :当这只精灵与精灵1只战斗时，直到回合结束时为止，那只精灵的能力失去。@@\n" +
                "~{{C:这只精灵的力量+4000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

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

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner());
            
            if(trash(data) > 0)
            {
                attachAbility(getCardIndex(), new StockAbilityLancer(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
                
                attachAbility(getCardIndex(), new AutoAbility(GameEventId.ATTACK_BATTLE, this::onAttachedAutoEff), ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getPower().getValue() <= 10000 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff()
        {
            disableAllAbilities(getEvent().getSourceCardIndex(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex())).get();
            if(target != null) gainPower(target, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
