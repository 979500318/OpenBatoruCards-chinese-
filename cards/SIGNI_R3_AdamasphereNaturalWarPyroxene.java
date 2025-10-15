package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class SIGNI_R3_AdamasphereNaturalWarPyroxene extends Card {

    public SIGNI_R3_AdamasphereNaturalWarPyroxene()
    {
        setImageSets("WX24-P1-042");
        setLinkedImageSets("WX24-P1-012");

        setOriginalName("羅闘輝石　アダマスフィア");
        setAltNames("ラトウキセキアダマスフィア Ratoukiseki Adamasufia");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《閃花繚乱　花代・参》がいる場合、対戦相手のパワー12000以下のシグニ１体を対象とし、手札から＜宝石＞のシグニを１枚捨ててもよい。そうした場合、それをバニッシュする。\n" +
                "@A @[エナゾーンから＜宝石＞のシグニ３枚をトラッシュに置く]@：ターン終了時まで、このシグニは@>@C：あなたの手札が２枚以下であるかぎり、このシグニは【ダブルクラッシュ】を得る。@@を得る。"
        );

        setName("en", "Adamasphere, Natural War Pyroxene");
        setDescription("en",
                "@U: At the beginning of your attack phase, if your LRIG is \"Hanayo Three, Profuse Bloom of Flourishing Flowers\", target 1 of your opponent's SIGNI with power 12000 or less, and you may discard 1 <<Gem>> SIGNI from your hand. If you do, banish it.\n" +
                "@A @[Put 3 <<Gem>> SIGNI from your ener zone into the trash]@: Until end of turn, this SIGNI gains:" +
                "@>@C: As long as there are 2 or less cards in your hand, this SIGNI gains [[Double Crush]]."
        );

		setName("zh_simplified", "罗斗辉石 金刚珠玉");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《閃花繚乱　花代・参》的场合，对战对手的力量12000以下的精灵1只作为对象，可以从手牌把<<宝石>>精灵1张舍弃。这样做的场合，将其破坏。\n" +
                "@A 从能量区把<<宝石>>精灵3张放置到废弃区:直到回合结束时为止，这只精灵得到\n" +
                "@>@C :你的手牌在2张以下时，这只精灵得到[[双重击溃]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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

            registerActionAbility(new TrashCost(3, new TargetFilter().SIGNI().withClass(CardSIGNIClass.GEM).fromEner()), this::onActionEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("閃花繚乱　花代・参"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
                
                if(target != null && discard(0,1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.GEM)).get() != null)
                {
                    banish(target);
                }
            }
        }

        private void onActionEff()
        {
            ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachedConst.setCondition(this::onAttachedConstEffCond);
            
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedConstEffCond()
        {
            return getHandCount(getOwner()) <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityDoubleCrush());
        }
    }
}
