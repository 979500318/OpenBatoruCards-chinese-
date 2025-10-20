package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_G3_CodeOrderMelFessone extends Card {

    public SIGNI_G3_CodeOrderMelFessone()
    {
        setImageSets("WXDi-P14-047", "WXDi-P14-047P");

        setOriginalName("コードオーダー　メル//フェゾーネ");
        setAltNames("コードオーダーメルフェゾーネ Koodo Ooda Meru Fezoone");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このシグニが覚醒状態の場合、【エナチャージ２】をする。\n" +
                "@U：このシグニがバトルによって対戦相手のシグニ１体をバニッシュしたとき、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。\n" +
                "@E @[アップ状態のルリグ２体をダウンする]@：このシグニは覚醒する。\n" +
                "@A #C：あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋3000する。"
        );

        setName("en", "Mel//Fesonne, Code: Order");
        setDescription("en",
                "@U: At the beginning of your attack phase, if this SIGNI is awakened, [[Ener Charge 2]].\n@U: Whenever this SIGNI vanishes a SIGNI on your opponent's field through battle, add up to one target SIGNI from your Ener Zone to your hand.\n@E @[Down two upped LRIG]@: Awaken this SIGNI. \n@A #C: Target SIGNI on your field gets +3000 power until end of turn."
        );
        
        setName("en_fan", "Code Order Mel//Fessone");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if this SIGNI is awakened, [[Ener Charge 2]].\n" +
                "@U: Whenever this SIGNI banishes an opponent's SIGNI in battle, target up to 1 SIGNI from your ener zone, and add it to your hand.\n" +
                "@E @[Down 2 of your upped LRIG]@: This SIGNI awakens.\n" +
                "@A #C: Target 1 of your SIGNI, and until end of turn, it gets +3000 power."
        );

		setName("zh_simplified", "点单代号 梅露//音乐节");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这只精灵在觉醒状态的场合，[[能量填充2]]。\n" +
                "@U :当这只精灵因为战斗把对战对手的精灵1只破坏时，从你的能量区把精灵1张最多作为对象，将其加入手牌。\n" +
                "@E 竖直状态的分身2只横置:这只精灵觉醒。（精灵觉醒后在场上保持觉醒状态）\n" +
                "@A #C:你的精灵1只作为对象，直到回合结束时为止，其的力量+3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            
            registerEnterAbility(new DownCost(2, new TargetFilter().anyLRIG()), this::onEnterEff);
            
            registerActionAbility(new CoinCost(1), this::onActionEff);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(isState(CardStateFlag.AWAKENED))
            {
                enerCharge(2);
            }
        }

        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceAbility() == null &&
                   getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
        
        private void onEnterEff()
        {
            getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 3000, ChronoDuration.turnEnd());
        }
    }
}
