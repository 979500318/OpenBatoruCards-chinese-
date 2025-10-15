package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_W2_IchikaNakamasa extends Card {

    public SIGNI_W2_IchikaNakamasa()
    {
        setImageSets("WX25-CP1-055");

        setOriginalName("仲正イチカ");
        setAltNames("ナカマサイチカ Nakamasa Ichika");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に他の＜ブルアカ＞のシグニがある場合、対戦相手のレベル１のシグニ１体を対象とし、%Wを支払ってもよい。そうした場合、それを手札に戻す。" +
                "~{{E @[手札から＜ブルアカ＞のカードを２枚捨てる]@：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Nakamasa Ichika");

        setName("en_fan", "Ichika Nakamasa");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is another <<Blue Archive>> SIGNI on your field, target 1 of your opponent's level 1 SIGNI, and you may pay %W. If you do, return it to their hand." +
                "~{{E @[Discard 2 <<Blue Archive>> cards from your hand]@: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "仲正一花");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有其他的<<ブルアカ>>精灵的场合，对战对手的等级1的精灵1只作为对象，可以支付%W。这样做的场合，将其返回手牌。\n" +
                "~{{E从手牌把<<ブルアカ>>牌2张舍弃从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            EnterAbility enter = registerEnterAbility(new DiscardCost(2, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff);
            enter.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(1)).get();
                
                if(target != null && payEner(Cost.color(CardColor.WHITE, 1)))
                {
                    addToHand(target);
                }
            }
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().guard().fromTrash()).get();
            addToHand(target);
        }
    }
}
