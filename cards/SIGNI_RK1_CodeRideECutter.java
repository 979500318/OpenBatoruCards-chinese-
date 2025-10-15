package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
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

public final class SIGNI_RK1_CodeRideECutter extends Card {

    public SIGNI_RK1_CodeRideECutter()
    {
        setImageSets("WXDi-P16-089");

        setOriginalName("コードライド　Ｅ・カッター");
        setAltNames("コードライドイーカッター Koodo Raido Ii Kattaa E-Cutter");
        setDescription("jp",
                "=T ＜デウス・エクス・マキナ＞\n" +
                "^U：このシグニがアタックしたとき、対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。このシグニに【ソウル】が付いている場合、代わりに対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。\n\n" +
                "@C：あなたの場に＜デウス・エクス・マキナ＞のルリグが３体いないかぎり、このカードはすべての領域で色を失う。"
        );

        setName("en", "E - Cutter, Code: Ride");
        setDescription("en",
                "=T <<Deus Ex Machina>>\n^U: Whenever this SIGNI attacks, vanish target SIGNI on your opponent's field with power 2000 or less. If a [[Soul]] is attached to this SIGNI, instead vanish target SIGNI on your opponent's field with power 3000 or less.\n\n@C: This card loses its colors in all zones unless there are three <<Deus Ex Machina>> LRIG on your field."
        );
        
        setName("en_fan", "Code Ride E Cutter");
        setDescription("en_fan",
                "=T <<Deus Ex Machina>>\n" +
                "^U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power 2000 or less, and banish it. If this SIGNI has a [[Soul]] attached to it, instead target 1 of your opponent's SIGNI with power 3000 or less, and banish it.\n\n" +
                "@C: If there aren't 3 <<Deus Ex Machina>> LRIG on your field, this SIGNI loses all of its colors in all zones."
        );

		setName("zh_simplified", "骑乘代号 E·美工刀");
        setDescription("zh_simplified", 
                "=T<<デウス・エクス・マキナ>>\n" +
                "^U:当这只精灵攻击时，对战对手的力量2000以下的精灵1只作为对象，将其破坏。这只精灵有[[灵魂]]附加的场合，作为替代，对战对手的力量3000以下的精灵1只作为对象，将其破坏。\n" +
                "@C :你的场上的<<デウス・エクス・マキナ>>分身没有在3只时，这张牌在全部的领域的颜色失去。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
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
            return isLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, getCardsUnderCount(CardUnderType.ATTACHED_SOUL) == 0 ? 2000 : 3000)).get();
            banish(target);
        }

        private ConditionState onConstEffCond()
        {
            return !isLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA) ? ConditionState.OK : ConditionState.BAD;
        }
        private CardDataColor onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getColor();
        }
    }
}
