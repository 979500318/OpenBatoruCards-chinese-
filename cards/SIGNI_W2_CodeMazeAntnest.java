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
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityGuard;

public final class SIGNI_W2_CodeMazeAntnest extends Card {
    
    public SIGNI_W2_CodeMazeAntnest()
    {
        setImageSets("WXDi-P04-049");
        
        setOriginalName("コードメイズ　アントネスト");
        setAltNames("コードメイズアントネスト Koodo Meizu Anto Nesuto");
        setDescription("jp",
                "@U：各アタックフェイズ開始時、このシグニの正面のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。" +
                "~#：カードを１枚引く。このターン、あなたの手札にあるシグニは#Gを得る。"
        );
        
        setName("en", "Antnest, Code: Maze");
        setDescription("en",
                "@U: At the beginning of each attack phase, target SIGNI in front of this SIGNI loses its abilities until end of turn." +
                "~#Draw a card. The SIGNI in your hand gain a #G this turn. "
        );
        
        setName("en_fan", "Code Maze Antnest");
        setDescription("en_fan",
                "@U: At the beginning of each attack phase, target 1 SIGNI in front of this SIGNI, and until end of turn, it loses its abilities." +
                "~#Draw 1 card. This turn, all SIGNI in your hand gain #G @[Guard]@."
        );
        
		setName("zh_simplified", "迷宫代号 蚁巢");
        setDescription("zh_simplified", 
                "@U :各攻击阶段开始时，这只精灵的正面的精灵1只作为对象，直到回合结束时为止，其的能力失去。" +
                "~#抽1张牌。这个回合，你的手牌的精灵得到#G。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI().match(getOppositeSIGNI())).get();
            disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
            
            ConstantAbilityShared attachedConstShared = new ConstantAbilityShared(new TargetFilter().own().SIGNI().fromHand(), new AbilityGainModifier(this::onAttachedConstEff2ModGetSample));
            attachPlayerAbility(getOwner(), attachedConstShared, ChronoDuration.turnEnd());
        }
        private Ability onAttachedConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityGuard());
        }
    }
}
