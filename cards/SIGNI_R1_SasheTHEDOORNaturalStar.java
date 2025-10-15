package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_R1_SasheTHEDOORNaturalStar extends Card {

    public SIGNI_R1_SasheTHEDOORNaturalStar()
    {
        setImageSets("WXDi-P15-061");

        setOriginalName("羅星　サシェ//THE DOOR");
        setAltNames("ラセイサシェザドアー Rasei Sashe Za Doaa");
        setDescription("jp",
                "@E @[手札から＜解放派＞のシグニを１枚捨てる]@：対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。\n\n" +
                "@C：このカードの上にある＜解放派＞のシグニは@>@U：あなたのアタックフェイズ開始時、対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。@@を得る。"
        );

        setName("en", "Sashe//THE DOOR, Natural Planet");
        setDescription("en",
                "@E @[Discard a <<Liberation Division>> SIGNI]@: Vanish target SIGNI on your opponent's field with power 5000 or less.\n\n@C: The <<Liberation Division>> SIGNI on top of this card gains@>@U: At the beginning of your attack phase, vanish target SIGNI on your opponent's field with power 3000 or less."
        );
        
        setName("en_fan", "Sashe//THE DOOR, Natural Star");
        setDescription("en_fan",
                "@E @[Discard 1 <<Liberation Faction>> SIGNI from your hand]@: Target 1 of your opponent's SIGNI with power 5000 or less, and banish it.\n\n" +
                "@C: The <<Liberation Faction>> SIGNI on top of this card gains:" +
                "@>@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 3000 or less, and banish it."
        );

		setName("zh_simplified", "罗星 莎榭//THE DOOR");
        setDescription("zh_simplified", 
                "@E 从手牌把<<解放派>>精灵1张舍弃:对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n" +
                "@C :这张牌的上面的<<解放派>>精灵得到\n" +
                "@>@U :你的攻击阶段开始时，对战对手的力量3000以下的精灵1只作为对象，将其破坏。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION)), this::onEnterEff);

            ConstantAbility cont = registerConstantAbility(new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).over(cardId), new AbilityGainModifier(this::onConstEffModGetSample));
            cont.setActiveUnderFlags(CardUnderCategory.UNDER);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            banish(target);
        }

        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
            banish(target);
        }
    }
}
