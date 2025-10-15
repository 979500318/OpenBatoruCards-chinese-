package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B3_BrynhildrAzureAngelPrincess extends Card {

    public SIGNI_B3_BrynhildrAzureAngelPrincess()
    {
        setImageSets("WXDi-P07-043");

        setOriginalName("蒼天姫　ブリュンヒルデ");
        setAltNames("ソウテンキブリュンヒルデ Soutenki Buryunhirude");
        setDescription("jp",
                "@U $T2：あなたの＜天使＞のシグニ１体が場に出たとき、カードを１枚引いてもよい。そうした場合、手札を１枚捨てる。\n" +
                "@U $T1：あなたの＜天使＞のシグニ１体がアタックしたとき、対戦相手の手札を１枚見ないで選び、捨てさせる。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、手札を２枚捨ててもよい。そうした場合、それをバニッシュする。\n" +
                "$$2対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Brynhildr, Azure Angel Queen");
        setDescription("en",
                "@U $T2: Whenever an <<Angel>> SIGNI enters your field, you may draw a card. If you do, discard a card.\n" +
                "@U $T1: When an <<Angel>> SIGNI on your field attacks, your opponent discards a card at random." +
                "~#Choose one -- \n$$1 You may discard two cards. If you do, vanish target SIGNI on your opponent's field. \n$$2 Your opponent discards a card at random."
        );

        setName("en_fan", "Brynhildr, Azure Angel Queen");
        setDescription("en_fan",
                "@U $T2: Whenever an <<Angel>> SIGNI enters your field, you may draw 1 card. If you do, discard 1 card from your hand.\n" +
                "@U $T1: When 1 of your <<Angel>> SIGNI attacks, choose 1 card from your opponent's hand without looking, and discard it." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and you may discard 2 cards from your hand. If you do, banish it.\n" +
                "$$2 Choose 1 card from your opponent's hand without looking, and discard it."
        );

		setName("zh_simplified", "苍天姬 布伦希尔德");
        setDescription("zh_simplified", 
                "@U $T2 :当你的<<天使>>精灵1只出场时，可以抽1张牌。这样做的场合，手牌1张舍弃。\n" +
                "@U $T1 :当你的<<天使>>精灵1只攻击时，不看对战对手的手牌选1张，舍弃。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，可以把手牌2张舍弃。这样做的场合，将其破坏。\n" +
                "$$2 不看对战对手的手牌选1张，舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.ENTER, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 2);

            AutoAbility auto2 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff1Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.ANGEL) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(playerChoiceActivate() && draw(1).get() != null)
            {
                discard(1);
            }
        }

        private void onAutoEff2(CardIndex caller)
        {
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();

                if(target != null && discard(0,2, ChoiceLogic.BOOLEAN).size() == 2)
                {
                    banish(target);
                }
            } else {
                onAutoEff2(null);
            }
        }
    }
}
