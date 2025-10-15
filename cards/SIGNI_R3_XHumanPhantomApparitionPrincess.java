package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R3_XHumanPhantomApparitionPrincess extends Card {

    public SIGNI_R3_XHumanPhantomApparitionPrincess()
    {
        setImageSets("SPDi43-08");
        setLinkedImageSets("SPDi43-03");

        setOriginalName("幻怪姫　Ｘヒューマン");
        setAltNames("ゲンカイキエックスヒューマン Genkaiki Ekkusu Hyuuman");
        setDescription("jp",
                "@C $TO：このシグニに【ソウル】が付いているかぎり、このシグニのパワーは＋5000される。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの場に《エクス・スリーNEO》がいる場合、%Rを支払ってもよい。そうした場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2このシグニのパワーが15000以上の場合、対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "X Human, Phantom Apparition Princess");
        setDescription("en",
                "@C $TO: As long as this SIGNI has a [[Soul]] attached to it, this SIGNI gets +5000 power.\n" +
                "@U: At the beginning of your attack phase, if your LRIG is \"Ex Three NEO\", you may pay %R. If you do, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 12000 or less, and banish it.\n" +
                "$$2 If this SIGNI's power is 15000 or more, target 1 of your opponent's SIGNI, and banish it."
        );

		setName("zh_simplified", "幻怪姬 X修曼");
        setDescription("zh_simplified", 
                "@C $TO :这只精灵有[[灵魂]]附加时，这只精灵的力量+5000。\n" +
                "@U :你的攻击阶段开始时，你的场上有《エクス・スリーNEO》的场合，可以支付%R。这样做的场合，从以下的2种选1种。\n" +
                "$$1 对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 这只精灵的力量在15000以上的场合，对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
        }
        
        private ConditionState onConstEffCond()
        {
            return isOwnTurn() && getCardIndex().getIndexedInstance().getCardsUnderCount(CardUnderType.ATTACHED_SOUL) > 0 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("エクス・スリーNEO") &&
               payEner(Cost.color(CardColor.RED, 1)))
            {
                if(playerChoiceMode() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
                    banish(target);
                } else {
                    if(getPower().getValue() >= 15000)
                    {
                        CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                        banish(target);
                    }
                }
            }
        }
    }
}
