package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W3_CoelaWaterPhantomPrincess extends Card {
    
    public SIGNI_W3_CoelaWaterPhantomPrincess()
    {
        setImageSets("WXDi-P06-033");
        
        setOriginalName("幻水姫　シィラ");
        setAltNames("ゲンスイヒメシィラ Gensuihime Shira");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたと対戦相手のライフクロスの枚数が同じ場合、カードを１枚引く。\n" +
                "@U $T1：対戦相手のターンの間、シグニ１体がアタックしたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。\n" +
                "@E %W：あなたのデッキの上からカードを３枚見る。その中からカードを１枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );
        
        setName("en", "Coela, Aquatic Phantom Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you and your opponent have the same number of Life Cloth, draw a card.\n" +
                "@U $T1: During your opponent's turn, when a SIGNI attacks, target SIGNI on your opponent's field loses its abilities until end of turn.\n" +
                "@E %W: Look at the top three cards of your deck. Add up to one card from among them into your hand. Put the rest on the bottom of your deck in any order." +
                "~#Put target upped SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "Coela, Water Phantom Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if you and your opponent have the same number of life cloth, draw 1 card.\n" +
                "@U $T1: During your opponent's turn, when a SIGNI attacks, target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities.\n" +
                "@E %W: Look at the top 3 cards of your deck. Add up to 1 card from among them to your hand, and put the rest on the bottom of your deck in any order." +
                "~#Target 1 of your opponent's upped SIGNI, and put it into the trash."
        );
        
		setName("zh_simplified", "幻水姬 鲯鳅");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你和对战对手的生命护甲的张数相同的场合，抽1张牌。\n" +
                "@U $TP $T1 :当精灵1只攻击时，对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。\n" +
                "@E %W:从你的牌组上面看3张牌。从中把牌1张最多加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getLifeClothCount(getOwner()) == getLifeClothCount(getOpponent()))
            {
                draw(1);
            }
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnTurn() && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
            disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(target);
        }
    }
}
