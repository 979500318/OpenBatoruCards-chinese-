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

public final class SIGNI_W2_AquilaNaturalStar extends Card {

    public SIGNI_W2_AquilaNaturalStar()
    {
        setImageSets("WX25-P2-067");

        setOriginalName("羅星　アクイラ");
        setAltNames("ラセイアクイラ Rasei Akuira");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのエナゾーンから＜宇宙＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、以下の２つのうち１つを選ぶ。\n" +
                "$$1対戦相手のパワー3000以下のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2あなたの場にレツナがある場合、対戦相手のパワー8000以下のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Aquila, Natural Star");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may put 1 <<Space>> SIGNI from your ener zone into the trash. If you do, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 3000 or less, and return it to their hand.\n" +
                "$$2 If there is a Resona on your field, target 1 of your opponent's SIGNI with power 8000 or less, an return it to their hand."
        );

		setName("zh_simplified", "罗星 天鹰座");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以从你的能量区把<<宇宙>>精灵1张放置到废弃区。这样做的场合，从以下的2种选1种。\n" +
                "$$1 对战对手的力量3000以下的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 你的场上有共鸣的场合，对战对手的力量8000以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(5000);

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
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.SPACE).fromEner()).get();
            
            if(trash(cardIndex))
            {
                if(playerChoiceMode() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,3000)).get();
                    addToHand(target);
                } else if(new TargetFilter().own().Resona().getValidTargetsCount() > 0)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,8000)).get();
                    addToHand(target);
                }
            }
        }
    }
}
