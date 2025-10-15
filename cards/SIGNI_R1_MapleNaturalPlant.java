package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R1_MapleNaturalPlant extends Card {

    public SIGNI_R1_MapleNaturalPlant()
    {
        setImageSets("WX24-P2-068");

        setOriginalName("羅植　モミジ");
        setAltNames("ラショクモミジ Rashoku Momiji");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のパワー5000以下のシグニ１体を対象とし、あなたのエナゾーンから＜植物＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Maple, Natural Plant");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 5000 or less, and you may put 1 <<Plant>> SIGNI from your ener zone into the trash. If you do, banish it."
        );

		setName("zh_simplified", "罗植 红叶");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的力量5000以下的精灵1只作为对象，可以从你的能量区把<<植物>>精灵1张放置到废弃区。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, 5000)).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.PLANT).fromEner()).get();
                if(trash(cardIndex))
                {
                    banish(target);
                }
            }
        }
    }
}
