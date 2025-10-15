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

public final class SIGNI_W2_AkiShirokaneDenonbu extends Card {

    public SIGNI_W2_AkiShirokaneDenonbu()
    {
        setImageSets("WXDi-P14-078");

        setOriginalName("電音部　白金煌");
        setAltNames("デンオンブシロカネアキ Denonbu Shirokane Aki");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に他の＜電音部＞のシグニがある場合、対戦相手のレベル１のシグニ１体を対象とし、あなたのエナゾーンから＜電音部＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、それを手札に戻す。"
        );

        setName("en", "DEN-ON-BU Aki Shirokane");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is another <<DEN-ON-BU>> SIGNI on your field, you may put a <<DEN-ON-BU>> SIGNI from your Ener Zone into your trash. If you do, return target level one SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Aki Shirokane, Denonbu");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there is another <<Denonbu>> SIGNI on your field, target 1 of your opponent's level 1 SIGNI, and you may put 1 <<Denonbu>> SIGNI from your ener zone into the trash. If you do, return it to their hand."
        );

		setName("zh_simplified", "电音部 白金煌");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有其他的<<電音部>>精灵的场合，对战对手的等级1的精灵1只作为对象，可以从你的能量区把<<電音部>>精灵1张放置到废弃区。这样做的场合，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DENONBU).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(1)).get();
                
                if(target != null)
                {
                    CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.DENONBU).fromEner()).get();
                    
                    if(trash(cardIndex))
                    {
                        addToHand(target);
                    }
                }
            }
        }
    }
}
