package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventTarget;

public final class SIGNI_B3_CordycepsNaturalBacteriaPrincess extends Card {

    public SIGNI_B3_CordycepsNaturalBacteriaPrincess()
    {
        setImageSets("WX24-P1-045");

        setOriginalName("羅菌姫　トーチュー・カソー");
        setAltNames("ラキンヒメトーチューカソー Rakinhime Toochuu Kasoo");
        setDescription("jp",
                "@U $T1：あなたの青のシグニ１体が対戦相手の、能力か効果の対象になったとき、対戦相手は手札を１枚捨てる。\n\n" +
                "@U：対戦相手の効果によってこのカードが捨てられたとき、あなたの手札が対戦相手より少ない場合、カードを１枚引く。" +
                "~#：どちらか１つを選ぶ。\n$$1対戦相手のシグニを２体まで対象とし、それらをダウンする。\n$$2カードを１枚引く。"
        );

        setName("en", "Cordyceps, Natural Bacteria Princess");
        setDescription("en",
                "@U $T1: When 1 of your blue SIGNI is targeted by your opponent's ability or effect, your opponent discards 1 card from their hand.\n\n" +
                "@U: When this card is discarded by your opponent's effect, if you have less cards in your hand than your opponent, draw 1 card." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "罗菌姬 冬虫·夏草");
        setDescription("zh_simplified", 
                "@U $T1 :当你的蓝色的精灵1只被作为对战对手的，能力或效果的对象时，对战对手把手牌1张舍弃。\n" +
                "@U :当因为对战对手的效果把这张牌舍弃时，你的手牌比对战对手少的场合，抽1张牌。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵2只最多作为对象，将这些横置。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.TARGET, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                    isOwnCard(caller) && CardLocation.isSIGNI(caller.getLocation()) && caller.getIndexedInstance().getColor().matches(CardColor.BLUE) &&
                    EventTarget.getDataSourceTargetRole() != getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2()
        {
            if(getHandCount(getOwner()) < getHandCount(getOpponent()))
            {
                draw(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
                down(data);
            } else {
                draw(1);
            }
        }
    }
}
