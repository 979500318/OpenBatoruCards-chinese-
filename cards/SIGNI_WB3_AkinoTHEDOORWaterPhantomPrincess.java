package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_WB3_AkinoTHEDOORWaterPhantomPrincess extends Card {

    public SIGNI_WB3_AkinoTHEDOORWaterPhantomPrincess()
    {
        setImageSets("WXDi-P16-054", "WXDi-P16-054P");

        setOriginalName("幻水姫　アキノ//THE DOOR");
        setAltNames("ゲンスイヒメアキノザドアー Gensuihime Akino Za Doaa");
        setDescription("jp",
                "@C：このシグニは同じシグニゾーンに【ゲート】があるかぎり、@>@C：対戦相手のターンの間、このシグニのパワーは＋5000され、このシグニは対戦相手の効果によってバニッシュされない。@@を得る。\n" +
                "@U：このシグニがアタックしたとき、あなたの場に【ゲート】がある場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のパワー5000以下のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2カードを２枚引く。"
        );

        setName("en", "Akino//THE DOOR, Aquatic Queen");
        setDescription("en",
                "@C: As long as this SIGNI is in the same SIGNI Zone as a [[Gate]], it gains@>@C: During your opponent's turn, this SIGNI gets +5000 power and cannot be vanished by your opponent's effects.@@@U: Whenever this SIGNI attacks, if there is a [[Gate]] on your field, choose one of the following.\n$$1Return target SIGNI on your opponent's field with power 5000 or less to its owner's hand.\n$$2Draw two cards."
        );
        
        setName("en_fan", "Akino//THE DOOR, Water Phantom Princess");
        setDescription("en_fan",
                "@C: As long as this SIGNI is on a SIGNI zone with a [[Gate]], it gains:" +
                "@>@C: During your opponent's turn, this SIGNI gets +5000 power, and can't be banished by your opponent's effects.@@" +
                "@U: Whenever this SIGNI attacks, if there is a [[Gate]] on your field, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 5000 or less, and return it to their hand.\n" +
                "$$2 Draw 2 cards."
        );

		setName("zh_simplified", "幻水姬 昭乃//THE DOOR");
        setDescription("zh_simplified", 
                "@C :这只精灵的相同精灵区有[[大门]]时，得到\n" +
                "@>@C :对战对手的回合期间，这只精灵的力量+5000，这只精灵不会因为对战对手的效果破坏。@@\n" +
                "@U :当这只精灵攻击时，你的场上有[[大门]]的场合，从以下的2种选1种。\n" +
                "$$1 对战对手的力量5000以下的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 抽2张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEFENSE_FACTION,CardSIGNIClass.WATER_BEAST);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }

        private ConditionState onConstEffCond()
        {
            return hasZoneObject(CardUnderType.ZONE_GATE) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            ConstantAbility attachedConst = cardIndex.getIndexedInstance().registerConstantAbility(new PowerModifier(5000), new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, this::onAttachedConstEffMod2RuleCheck));
            attachedConst.setCondition(this::onAttachedConstEffCond);
            return attachedConst;
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onAttachedConstEffMod2RuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().zone().withZoneObject(CardUnderType.ZONE_GATE).getValidTargetsCount() > 0)
            {
                if(playerChoiceMode() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,5000)).get();
                    addToHand(target);
                } else {
                    draw(2);
                }
            }
        }
    }
}
