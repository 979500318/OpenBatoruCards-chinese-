package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.ModifiableBaseValueModifier;

public final class SIGNI_GRB1_CodeMazeTrunembra extends Card {

    public SIGNI_GRB1_CodeMazeTrunembra()
    {
        setImageSets("WXDi-P16-092");

        setOriginalName("コードメイズ　トルネンブラ");
        setAltNames("コードメイズトルネンブラ Koodo Meizu Torunenbura");
        setDescription("jp",
                "=T ＜アンシエント・サプライズ＞\n" +
                "^U：このシグニがアタックしたとき、以下の３つから１つを選ぶ。\n" +
                "$$1対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。\n" +
                "$$3【エナチャージ１】\n\n" +
                "@C：あなたの場に＜アンシエント・サプライズ＞のルリグが３体いないかぎり、このカードはすべての領域で色を失う。"
        );

        setName("en", "Tru'nembra, Code: Maze");
        setDescription("en",
                "=T <<Ancient Surprise>> \n^U: Whenever this SIGNI attacks, choose one of the following.\n$$1Vanish target SIGNI on your opponent's field with power 2000 or less. \n$$2Draw a card. \n$$3[[Ener Charge 1]].\n\n@C: This card loses its colors in all zones unless there are three <<Ancient Surprise>> LRIG on your field."
        );
        
        setName("en_fan", "Code Maze Tru'nembra");
        setDescription("en_fan",
                "=T <<Ancient Surprise>>\n" +
                "^U: Whenever this SIGNI attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 2000 or less, and banish it.\n" +
                "$$2 Draw 1 card.\n" +
                "$$3 [[Ener Charge 1]]\n\n" +
                "@C: If there aren't 3 <<Ancient Surprise>> LRIG on your field, this SIGNI loses all of its colors in all zones."
        );

		setName("zh_simplified", "迷宫代号 特鲁宁布拉");
        setDescription("zh_simplified", 
                "=T<<アンシエント･サプライズ>>（假如你的场上的<<アンシエント･サプライズ>>的分身在3只，那么^U能力有效）\n" +
                "&nbsp;^U:当这只精灵攻击时，从以下的3种选1种。\n" +
                "$$1 对战对手的力量2000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n" +
                "$$3 [[能量填充1]]\n" +
                "@C :你的场上的<<アンシエント･サプライズ>>分身没有在3只时，这张牌在全部的领域的颜色失去。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN, CardColor.RED, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new ModifiableBaseValueModifier<>(this::onConstEffModGetSample, () -> CardDataColor.EMPTY_VALUE));
            cont.getFlags().addValue(AbilityFlag.IGNORE_LOCATION | AbilityFlag.IGNORE_UNDER_FLAGS);
        }

        private ConditionState onAutoEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            switch(playerChoiceMode())
            {
                case 1 -> {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
                    banish(target);
                }
                case 1<<1 -> draw(1);
                case 1<<2 -> enerCharge(1);
            }
        }

        private ConditionState onConstEffCond()
        {
            return !isLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE) ? ConditionState.OK : ConditionState.BAD;
        }
        private CardDataColor onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getColor();
        }
    }
}
