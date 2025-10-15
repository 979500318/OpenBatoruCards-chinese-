package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B1_ReiDissonaNaturalStone extends Card {

    public SIGNI_B1_ReiDissonaNaturalStone()
    {
        setImageSets("WXDi-P13-070");

        setOriginalName("羅石　レイ//ディソナ");
        setAltNames("ラセキレイディソナ Raseki Rei Disona");
        setDescription("jp",
                "@C：あなたの手札が４枚以上あるかぎり、あなたの他の#Sのシグニのパワーを＋2000する。\n" +
                "@U：あなたのアタックフェイズ開始時、このターンにあなたが効果によってカードを２枚以上引いていた場合、対戦相手のシグニ１体を対象とし、それを凍結する。"
        );

        setName("en", "Rei//Dissona, Natural Crystal");
        setDescription("en",
                "@C: As long as you have four or more cards in your hand, other #S SIGNI on your field get +2000 power.\n@U: At the beginning of your attack phase, if you have drawn two or more cards by effects this turn, freeze target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Rei//Dissona, Natural Stone");
        setDescription("en_fan",
                "@C: As long as there are 4 or more cards in your hand, all of your other #S @[Dissona]@ SIGNI get +2000 power.\n" +
                "@U: At the beginning of your attack phase, if you drew 2 or more cards by effects this turn, target 1 of your opponent's SIGNI, and freeze it."
        );

		setName("zh_simplified", "罗石 令//失调");
        setDescription("zh_simplified", 
                "@C 你的手牌在4张以上时，你的其他的#S的精灵的力量+2000。\n" +
                "@U :你的攻击阶段开始时，这个回合你因为效果抽牌2张以上的场合，对战对手的精灵1只作为对象，将其冻结。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().dissona().except(cardId), new PowerModifier(2000));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) >= 4 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(e -> e.getId() == GameEventId.DRAW && isOwnCard(e.getCaller()) && e.getSourceAbility() != null) >= 2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
                freeze(target);
            }
        }
    }
}
