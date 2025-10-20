package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R3_HanayoFessoneNaturalPyroxene extends Card {

    public SIGNI_R3_HanayoFessoneNaturalPyroxene()
    {
        setImageSets("WXDi-P14-043", "WXDi-P14-043P");

        setOriginalName("羅輝石　花代//フェゾーネ");
        setAltNames("ラキセキハナヨフェゾーネ Rakiseki Hanayo Fezoone");
        setDescription("jp",
                "@C：このシグニが覚醒状態であるかぎり、このシグニのパワーは＋5000される。\n" +
                "@U：あなたのアタックフェイズ開始時、このシグニのパワー以下の対戦相手のシグニ１体を対象とし、手札を２枚捨ててもよい。そうした場合、それをバニッシュする。\n" +
                "@E @[アップ状態のルリグ２体をダウンする]@：このシグニは覚醒する。"
        );

        setName("en", "Hanayo//Fesonne, Natural Pyroxene");
        setDescription("en",
                "@C: As long as this SIGNI is awakened, this SIGNI gets +5000 power.\n@U: At the beginning of your attack phase, you may discard two cards. If you do, vanish target SIGNI on your opponent's field with power less than or equal to this SIGNI.\n@E @[Down two upped LRIG]@: Awaken this SIGNI. "
        );
        
        setName("en_fan", "Hanayo//Fessone, Natural Pyroxene");
        setDescription("en_fan",
                "@C: As long as this SIGNI is awakened, it gets +5000 power.\n" +
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power equal to less than this SIGNI's, and you may discard 2 cards from your hand. If you do, banish it.\n" +
                "@E @[Down 2 of your upped LRIG]@: This SIGNI awakens."
        );

		setName("zh_simplified", "罗辉石 花代//音乐节");
        setDescription("zh_simplified", 
                "@C :这只精灵在觉醒状态时，这只精灵的力量+5000。\n" +
                "@U :你的攻击阶段开始时，这只精灵的力量以下的对战对手的精灵1只作为对象，可以把手牌2张舍弃。这样做的场合，将其破坏。\n" +
                "@E 竖直状态的分身2只横置:这只精灵觉醒。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerEnterAbility(new DownCost(2, new TargetFilter().anyLRIG()), this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return isState(CardStateFlag.AWAKENED) ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,getPower().getValue())).get();
            
            if(target != null && discard(0,2, ChoiceLogic.BOOLEAN).size() == 2)
            {
                banish(target);
            }
        }
        
        private void onEnterEff()
        {
            getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }
    }
}
