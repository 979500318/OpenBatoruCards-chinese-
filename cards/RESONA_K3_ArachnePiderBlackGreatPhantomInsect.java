package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class RESONA_K3_ArachnePiderBlackGreatPhantomInsect extends Card {

    public RESONA_K3_ArachnePiderBlackGreatPhantomInsect()
    {
        setImageSets("WXDi-P11-TK05");

        setOriginalName("黒大幻蟲　アラクネ・パイダ");
        setAltNames("コクダイゲンチュウアラクネパイダ Kokudaigenchuu Arakune Paida");
        setDescription("jp",
                "手札とエナゾーンからシグニを合計２枚トラッシュに置く\n\n" +
                "@U：各アタックフェイズ開始時、ターン終了時まで、【チャーム】が付いている対戦相手のすべてのシグニは能力を失う。\n" +
                "@U：【チャーム】が付いている対戦相手のシグニ１体がバニッシュされたとき、カードを１枚引く。\n" +
                "@A $T1 %K0：対戦相手のシグニ１体を対象とし、対戦相手のトラッシュからカード１枚をそれの【チャーム】にする。"
        );

        setName("en", "Arachne Pider, Giant Phantom Insect");
        setDescription("en",
                "Put two SIGNI from your hand and/or Ener Zone into your trash.\n\n" +
                "@U: At the beginning of each attack phase, all SIGNI on your opponent's field with [[Charm]] attached lose their abilities until end of turn.\n" +
                "@U: Whenever a SIGNI on your opponent's field with [[Charm]] attached is vanished, draw a card.\n" +
                "@A $T1 %K0: Attach a card from your opponent's trash to target SIGNI on your opponent's field as a [[Charm]]."
        );
        
        setName("en_fan", "Arachne Pider, Black Great Phantom Insect");
        setDescription("en_fan",
                "Put 2 SIGNI from your hand and/or ener zone into the trash\n\n" +
                "@U: At the beginning of each attack phase, until end of turn, all of your opponent's SIGNI with [[Charm]] attached to them lose their abilities.\n" +
                "@U: Whenever your opponent's SIGNI with a [[Charm]] attached to it is banished, draw 1 card.\n" +
                "@A $T1 %K0: Target 1 of your opponent's SIGNI, and attach 1 card from your opponent's trash to it as a [[Charm]]."
        );
        
		setName("zh_simplified", "黑大幻虫 阿拉克尼·蜘蛛");
        setDescription("zh_simplified", 
                "[[出现条件]]主要阶段从手牌和能量区把精灵合计2张放置到废弃区\n" +
                "@U :各攻击阶段开始时，直到回合结束时为止，有[[魅饰]]附加的对战对手的全部的精灵的能力失去。\n" +
                "@U :当有[[魅饰]]附加的对战对手的精灵1只被破坏时，抽1张牌。\n" +
                "@A $T1 %K0:对战对手的精灵1只作为对象，从对战对手的废弃区把1张牌作为其的[[魅饰]]。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.RESONA);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(3);
        setPower(12000);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            setUseCondition(UseCondition.RESONA, 2, new TargetFilter().or(new TargetFilter().fromHand(), new TargetFilter().fromEner()));

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEff1Cond()
        {
            return getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            disableAllAbilities(new TargetFilter().OP().SIGNI().withCardsUnder(CardUnderType.ATTACHED_CHARM).getExportedData(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && caller.getIndexedInstance().getCardsUnderCount(CardUnderType.ATTACHED_CHARM) > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            draw(1);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ATTACH).OP().SIGNI().attachable(CardUnderType.ATTACHED_CHARM)).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ATTACH).OP().fromTrash()).get();
                
                attach(target, cardIndex, CardUnderType.ATTACHED_CHARM);
            }
        }
    }
}

