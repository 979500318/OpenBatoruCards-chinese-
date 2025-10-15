package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.RuleCheckCanPowerBeChanged;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_R3_CodeArtJUkebox extends Card {

    public SIGNI_R3_CodeArtJUkebox()
    {
        setImageSets("WXDi-P11-062");

        setOriginalName("コードアート　Ｊユークボックス");
        setAltNames("コードアートジェイユークボックス Koodo Aato Jei Yuukibokkusu");
        setDescription("jp",
                "@C：あなたのトラッシュにスペルが２枚以上あるかぎり、このシグニのパワーは＋3000され、対戦相手の効果によって－（マイナス）されない。\n" +
                "@U $T1：あなたがスペルを使用したとき、手札を１枚捨ててもよい。そうした場合、カードを１枚引く。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "J - Ukebox, Code: Art");
        setDescription("en",
                "@C: As long as there are two or more spells in your trash, this SIGNI gets +3000 power and cannot be -- by your opponent's effects.\n" +
                "@U $T1: When you use a spell, you may discard a card. If you do, draw a card." +
                "~#Choose one -- \n$$1 Vanish target upped SIGNI on your opponent's field. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Code Art J Ukebox");
        setDescription("en_fan",
                "@C: As long as there are 2 or more spells in your trash, this SIGNI gets +3000 power and its power can't be -- (minus) by your opponent's effects.\n" +
                "@U $T1: When you use a spell, you may discard 1 card from your hand. If you do, draw 1 card." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "必杀代号 点歌机");
        setDescription("zh_simplified", 
                "@C :你的废弃区的魔法在2张以上时，这只精灵的力量+3000，不会因为对战对手的效果-（减号）。\n" +
                "@U $T1 :当你把魔法使用时，可以把手牌1张舍弃。这样做的场合，抽1张牌。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000), new RuleCheckModifier<>(CardRuleCheckType.CAN_POWER_BE_CHANGED, this::onConstEffModRuleCheck));

            AutoAbility auto = registerAutoAbility(GameEventId.USE_SPELL, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().spell().fromTrash().getValidTargetsCount() >= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return RuleCheckCanPowerBeChanged.getDataAddValue(data) < 0 &&
                   data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                draw(1);
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                draw(1);
            }
        }
    }
}
