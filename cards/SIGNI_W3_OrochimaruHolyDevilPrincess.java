package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

import java.util.List;

public final class SIGNI_W3_OrochimaruHolyDevilPrincess extends Card {

    public SIGNI_W3_OrochimaruHolyDevilPrincess()
    {
        setImageSets("WXDi-P09-037");

        setOriginalName("聖魔姫　オロチマル");
        setAltNames("セイマキオロチマル Seimaki Orochimaru");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のパワー12000以下のシグニ１体を対象とし、以下の２つから１つを選ぶ。\n" +
                "$$1手札を３枚捨ててもよい。そうした場合、それを手札に戻す。\n" +
                "$$2手札からカード２枚と#Gを持つシグニ１枚を捨ててもよい。そうした場合、それをゲームから除外する。\n" +
                "@E %W %X：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Orochimaru, Blessed Evil Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, choose one of the following.\n" +
                "$$1 You may discard three cards. If you do, return target SIGNI on your opponent's field with power 12000 or less to its owner's hand.\n" +
                "$$2 You may discard two cards and a SIGNI with #G. If you do, remove target SIGNI on your opponent's field with power 12000 or less from the game.\n" +
                "@E %W %X: Add target SIGNI with a #G from your trash to your hand."
        );

        setName("en_fan", "Orochimaru, Holy Devil Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 12000 or less, and @[@|choose 1 of the following:|@]@\n" +
                "$$1 You may discard 3 cards from your hand. If you do, return it to their hand.\n" +
                "$$2 You may discard 2 cards and 1 #G @[Guard]@ SIGNI from your hand. If you do, exclude it from the game.\n" +
                "@E %W %X: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "圣魔姬 大蛇丸");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的力量12000以下的精灵1只作为对象，从以下的2种选1种。\n" +
                "$$1 可以把手牌3张舍弃。这样做的场合，将其返回手牌。\n" +
                "$$2 可以从手牌把2张牌和持有#G的精灵1张舍弃。这样做的场合，将其从游戏除外。\n" +
                "@E %W%X从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(10000);

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

            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)), this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI().withPower(0,12000)).get();

            if(target != null)
            {
                if(playerChoiceMode() == 1)
                {
                    if(discard(0,3, ChoiceLogic.BOOLEAN).size() == 3)
                    {
                        addToHand(target);
                    }
                } else if(discard(getOwner(), 0,3, ChoiceLogic.BOOLEAN, new TargetFilter(), this::onAutoEffDiscardTargetCond).size() == 3)
                {
                    exclude(target);
                }
            }
        }
        private boolean onAutoEffDiscardTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 3 &&
                   listPickedCards.stream().anyMatch(cardIndex -> CardType.isSIGNI(cardIndex.getCardReference().getType()) && cardIndex.getIndexedInstance().isState(CardStateFlag.CAN_GUARD));
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }
    }
}
