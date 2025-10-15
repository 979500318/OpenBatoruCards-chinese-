package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.FieldZone;

public final class SIGNI_W3_SirenWaterPhantom extends Card {

    public SIGNI_W3_SirenWaterPhantom()
    {
        setImageSets("WXDi-P10-035");
        
        setOriginalName("幻水姫　セイレーン");
        setAltNames("ゲンスイヒメセイレーン Gensuihime Saireen");
        setDescription("jp",
                "@C：このシグニが覚醒状態であるかぎり、このシグニのパワーは＋1000され、このシグニは@>@U：このシグニが場を離れたとき、対戦相手のシグニ１体を対象とし、%Wを支払ってもよい。そうした場合、それを手札に戻す。@@を得る。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの手札が４枚以上ある場合、このシグニは覚醒する。"
        );
        
        setName("en", "Siren, Aquatic Phantom Queen");
        setDescription("en",
                "@C: As long as this SIGNI is awakened, it gets +1000 power and gains@>@U: When this SIGNI leaves the field, you may pay %W. If you do, return target SIGNI on your opponent's field to its owner's hand.@@" +
                "@U: At the beginning of your attack phase, if you have four or more cards in your hand, this SIGNI is awakened. "
        );
        
        setName("en_fan", "Siren, Water Phantom");
        setDescription("en_fan",
                "@C: As long as this SIGNI is awakened, it gets +1000 power and it gains:" +
                "@>@U: When this SIGNI leaves the field, target 1 of your opponent's SIGNI, and you may pay %W. If you do, return it to their hand.@@" +
                "@U: At the beginning of your attack phase, if there are 4 or more cards in your hand, this SIGNI awakens."
        );
        
		setName("zh_simplified", "幻水姬 塞壬");
        setDescription("zh_simplified", 
                "@C :这只精灵在觉醒状态时，这只精灵的力量+1000，这只精灵得到\n" +
                "@>@U :当这只精灵离场时，对战对手的精灵1只作为对象，可以支付%W。这样做的场合，将其返回手牌。@@\n" +
                "@U :你的攻击阶段开始时，你的手牌在4张以上的场合，这只精灵觉醒。（精灵觉醒后在场上保持觉醒状态）\n"
        );

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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(1000), new AbilityGainModifier(this::onConstEffModGetSample));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return isState(CardStateFlag.AWAKENED) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.MOVE, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.WHITE, 1)))
            {
                addToHand(target);
            }
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getHandCount(getOwner()) >= 4)
            {
                getCardStateFlags().addValue(CardStateFlag.AWAKENED);
            }
        }
    }
}
