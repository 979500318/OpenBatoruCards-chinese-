package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R3_AiasGreatshield extends Card {

    public SIGNI_R3_AiasGreatshield()
    {
        setImageSets("WXDi-P11-041");

        setOriginalName("大盾　アイアース");
        setAltNames("ダイジュンアイアース Daijun Aiaasu");
        setDescription("jp",
                "@C：あなたの手札が１枚以下であるかぎり、あなたの＜アーム＞と＜ウェポン＞のシグニのパワーを＋3000する。\n" +
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、手札から＜アーム＞と＜ウェポン＞のシグニを合計２枚捨ててもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Ajax, Great Shield");
        setDescription("en",
                "@C: As long as you have one or fewer cards in your hand, <<Armed>> SIGNI and <<Weapon>> SIGNI on your field get +3000 power.\n" +
                "@U: At the beginning of your attack phase, you may discard a total of two <<Armed>> SIGNI or <<Weapon>> SIGNI. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Aias, Greatshield");
        setDescription("en_fan",
                "@C: As long as there are 1 or less cards in your hand, all of your <<Arm>> and <<Weapon>> SIGNI get +3000 power.\n" +
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may discard 2 <<Arm>> and/or <<Weapon>> SIGNI from your hand. If you do, banish it."
        );

		setName("zh_simplified", "大盾 埃阿斯");
        setDescription("zh_simplified", 
                "@C :你的手牌在1张以下时，你的<<アーム>>和<<ウェポン>>精灵的力量+3000。\n" +
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以从手牌把<<アーム>>和<<ウェポン>>精灵合计2张舍弃。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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
            
            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ARM,CardSIGNIClass.WEAPON), new PowerModifier(3000));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) <= 1 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && discard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter().SIGNI().withClass(CardSIGNIClass.ARM,CardSIGNIClass.WEAPON)).size() == 2)
            {
                banish(target);
            }
        }
    }
}
