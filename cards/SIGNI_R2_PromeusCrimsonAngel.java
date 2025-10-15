package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R2_PromeusCrimsonAngel extends Card {

    public SIGNI_R2_PromeusCrimsonAngel()
    {
        setImageSets("WXDi-P10-055");

        setOriginalName("紅天　プロメウス");
        setAltNames("コウテンプロメウス Kouten Puromeusu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にある＜天使＞のシグニのパワーの合計が20000以上の場合、対戦相手のパワー10000以下のシグニ１体を対象とし、%R %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Promeus, Crimson Angel");
        setDescription("en",
                "@U: At the beginning of your attack phase, if the total power of <<Angel>> SIGNI on your field is 20000 or more, you may pay %R %X. If you do, vanish target SIGNI on your opponent's field with power 10000 or less."
        );
        
        setName("en_fan", "Promeus, Crimson Angel");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if the total power of all of your <<Angel>> SIGNI is 20000 or more, target 1 of your opponent's SIGNI with power 10000 or less, and you may pay %R %X. If you do, banish it."
        );

		setName("zh_simplified", "红天 普罗米修斯");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的<<天使>>精灵的力量的合计在20000以上的场合，对战对手的力量10000以下的精灵1只作为对象，可以支付%R%X。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).getExportedData().stream().mapToDouble(c -> ((CardIndex)c).getIndexedInstance().getPower().getValue()).sum() >= 20000)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
                
                if(target != null && payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
                {
                    banish(target);
                }
            }
        }
    }
}
