package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W3_ShironakujiWaterPhantomPrincess extends Card {
    
    public SIGNI_W3_ShironakujiWaterPhantomPrincess()
    {
        setImageSets("WXDi-P03-037");
        
        setOriginalName("幻水姫　シロナクジ");
        setAltNames("ゲンスイヒメシロナクジ Gensuihime Shironakuji");
        setDescription("jp",
                "@C：あなたの手札が対戦相手より多いかぎり、このシグニのパワーはその差１枚につき＋1000される。\n" +
                "@U：対戦相手のアタックフェイズ開始時、あなたの手札が６枚の場合、カードを１枚引く。\n" +
                "@A $T1 %W %W @[手札を２枚捨てる]@：対戦相手のシグニ１体を対象とし、それをトラッシュに置く。"
        );
        
        setName("en", "Blue Whaleen, Aquatic Phantom Queen");
        setDescription("en",
                "@C: This SIGNI gets +1000 power for each card in your hand that's more than your opponent.\n" +
                "@U: At the beginning of your opponent's attack phase, if you have exactly six cards in your hand, draw a card.\n" +
                "@A $T1 %W %W @[Discard two cards]@: Put target SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "Shironakuji, Water Phantom Princess");
        setDescription("en_fan",
                "@C: As long as there are more cards in your hand that your opponent's, this SIGNI gets +1000 power for each card of difference.\n" +
                "@U: At the beginning of your opponent's attack phase, if there are 6 cards in your hand, draw 1 card.\n" +
                "@A $T1 %W %W @[Discard 2 cards from your hand]@: Target 1 of your opponent's SIGNI, and put it into the trash."
        );
        
		setName("zh_simplified", "幻水姬 白鲸");
        setDescription("zh_simplified", 
                "@C :你的手牌比对战对手多时，这只精灵的力量依据那个差值的数量，每有1张就+1000。\n" +
                "@U :对战对手的攻击阶段开始时，你的手牌在6张的场合，抽1张牌。\n" +
                "@A $T1 %W %W手牌2张舍弃:对战对手的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(this::onConstEffModGetValue));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.WHITE, 2)), new DiscardCost(2)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) > getHandCount(getOpponent()) ? ConditionState.OK : ConditionState.BAD;
        }
        private double onConstEffModGetValue(CardIndex cardIndex)
        {
            return 1000 * (getHandCount(getOwner()) - getHandCount(getOpponent()));
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getHandCount(getOwner()) == 6)
            {
                draw(1);
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            trash(target);
        }
    }
}
