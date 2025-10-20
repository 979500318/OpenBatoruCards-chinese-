package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.*;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class SIGNI_B3_CodeHeartPirulukFessone extends Card {

    public SIGNI_B3_CodeHeartPirulukFessone()
    {
        setImageSets("WXDi-P14-045", "WXDi-P14-045P");

        setOriginalName("コードハート　ピルルク//フェゾーネ");
        setAltNames("コードハートピルルクフェゾーネ Koodo Haato Piruruku Fezoone");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このシグニが覚醒状態の場合、対戦相手の手札を見て#Gを持たないカード１枚を選び、捨てさせる。\n" +
                "@A $T1 %B0：このターン、次にあなたがスペルを使用する場合、そのスペルの使用コストは%B減る。\n" +
                "@E @[アップ状態のルリグ２体をダウンする]@：このシグニは覚醒する。"
        );

        setName("en", "Piruluk//Fesonne, Code: Heart");
        setDescription("en",
                "@U: At the beginning of your attack phase, if this SIGNI is awakened, look at your opponent's hand and choose a card without a #G. Your opponent discards it.\n@A $T1 %B0: The next time you use a spell this turn, the use cost of that spell is reduced by %B.\n@E @[Down two upped LRIG]@: Awaken this SIGNI. "
        );
        
        setName("en_fan", "Code Heart Piruluk//Fessone");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if this SIGNI is awakened, look at your opponent's hand, choose 1 card without #G @[Guard]@, and your opponent discards it.\n" +
                "@A $T1 %B0: This turn, the use cost of the next spell you use is reduced by %B.\n" +
                "@E @[Down 2 of your upped LRIG]@: This SIGNI awakens."
        );

		setName("zh_simplified", "爱心代号 皮璐璐可//音乐节");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，这只精灵在觉醒状态的场合，看对战对手的手牌选不持有#G的牌1张，舍弃。\n" +
                "@A $T1 %B0:这个回合，下一次你把魔法使用的场合，那张魔法的使用费用减%B。\n" +
                "@E 竖直状态的分身2只横置:这只精灵觉醒。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(10000);

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

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);

            registerEnterAbility(new DownCost(2, new TargetFilter().anyLRIG()), this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(isState(CardStateFlag.AWAKENED))
            {
                reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
                
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.DISCARD).OP().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromRevealed()).get();
                discard(cardIndex);
                
                addToHand(getCardsInRevealed(getOpponent()));
            }
        }
        
        private long cacheUsedSpellsCount;
        private void onActionEff()
        {
            cacheUsedSpellsCount = getUsedSpellsCount();
            
            ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().own().spell().anyLocation(),
                new CostModifier(this::onAttachedConstEffModCond, () -> new EnerCost(Cost.color(CardColor.BLUE, 1)), ModifierMode.REDUCE)
            );
            
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedConstEffModCond(CardIndex cardIndex)
        {
            return getUsedSpellsCount() == cacheUsedSpellsCount ? ConditionState.OK : ConditionState.BAD;
        }
        private int getUsedSpellsCount()
        {
            return GameLog.getTurnRecordsCount(event ->
                    event.getId() == GameEventId.ABILITY &&
                    event.getSourceAbility() instanceof SpellAbility &&
                    isOwnCard(event.getCaller()));
        }
        
        private void onEnterEff()
        {
            getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }
    }
}
