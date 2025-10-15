package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B2_HijikataAzureGeneral extends Card {
    
    public SIGNI_B2_HijikataAzureGeneral()
    {
        setImageSets("WXDi-P02-068");
        
        setOriginalName("蒼将　ヒジカタ");
        setAltNames("ソウショウヒジカタ Soushou Hijikata");
        setDescription("jp",
                "@C：このターンにあなたが手札を１枚以上捨てていた場合、このシグニのパワーは＋3000される。\n" +
                "@C：このターンにあなたが手札を２枚以上捨てていた場合、このシグニは@>@U：このシグニがバトルによって対戦相手のシグニをバニッシュしたとき、対戦相手の手札を１枚見ないで選び、捨てさせる。@@を得る。"
        );
        
        setName("en", "Hijikata, Azure General");
        setDescription("en",
                "@C: As long as you have discarded one or more cards this turn, this SIGNI gets +3000 power.\n" +
                "@C: As long as you have discarded two or more cards this turn, this SIGNI gains@>@U: Whenever this SIGNI vanishes a SIGNI on your opponent's field through battle, your opponent discards a card at random."
        );
        
        setName("en_fan", "Hijikata, Azure General");
        setDescription("en_fan",
                "@C: If you discarded 1 or more cards this turn, this SIGNI gets +3000 power.\n" +
                "@C: If you discarded 2 or more cards this turn, this SIGNI gains:" +
                "@>@U: Whenever this SIGNI banishes your opponent's SIGNI in battle, choose 1 card from your opponent's hand without looking, and discard it."
        );
        
		setName("zh_simplified", "苍将 土方岁三");
        setDescription("zh_simplified", 
                "@C :这个回合你把手牌1张以上舍弃过的场合，这只精灵的力量+3000。\n" +
                "@C :这个回合你把手牌2张以上舍弃过的场合，这只精灵得到\n" +
                "@>@U :当这只精灵因为战斗把对战对手的精灵破坏时，不看对战对手的手牌选1张，舍弃。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(7000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEff1Cond, new PowerModifier(3000));
            
            registerConstantAbility(this::onConstEff2Cond, new AbilityGainModifier(this::onConstEff2ModGetSample));
        }
        
        private ConditionState onConstEff1Cond()
        {
            return GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.DISCARD && isOwnCard(event.getCaller())) >= 1 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onConstEff2Cond()
        {
            return GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.DISCARD && isOwnCard(event.getCaller())) >= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEff2ModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.BANISH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getAbility().getSourceCardIndex() && getEvent().getSourceAbility() == null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
    }
}
