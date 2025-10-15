package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventPowerChanged;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class SIGNI_K2_Code2434Ruco extends Card {

    public SIGNI_K2_Code2434Ruco()
    {
        setImageSets("WXDi-CP01-048");
        setLinkedImageSets("WXDi-P00-080");

        setOriginalName("コード２４３４　Ｒｕｃｏ");
        setAltNames("コードニジサンジルコ Koodo Nijisanji Ruko");
        setDescription("jp",
                "@C：あなたの《ダークネス・イーター》の使用コストは%X %X減る。\n" +
                "@U $T1：対戦相手のシグニ１体のパワーが０以下になったとき、あなたの場にある他の＜バーチャル＞のシグニ１体につき対戦相手のデッキの上からカードを１枚トラッシュに置く。"
        );

        setName("en", "Ruco, Code 2434");
        setDescription("en",
                "@C: Use costs of your \"Darkness Eater\" are reduced by %X %X.\n@U $T1: When the power of a SIGNI on your opponent's field becomes 0 or less, put the top card of your opponent's deck into their trash for each other <<Virtual>> SIGNI on your field."
        );
        
        setName("en_fan", "Code 2434 Ruco");
        setDescription("en_fan",
                "@C: The use cost of your \"Darkness Eater\" is reduced by %X %X.\n" +
                "@U $T1: When the power of your opponent's SIGNI becomes 0 or less, put the top card of your opponent's deck into the trash for each other <<Virtual>> SIGNI on your field."
        );

		setName("zh_simplified", "2434代号 Ruco");
        setDescription("zh_simplified", 
                "@C :你的《ダークネス・イーター》的使用费用减%X %X。\n" +
                "@U $T1 :当对战对手的精灵1只的力量在0以下时，依据你的场上的<<バーチャル>>精灵的数量，每有1只就从对战对手的牌组上面把1张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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

            registerConstantAbility(new TargetFilter().own().spell().withName("ダークネス・イーター").anyLocation(),
                new CostModifier(() -> new EnerCost(Cost.colorless(2)), ModifierMode.REDUCE)
            );

            AutoAbility auto = registerAutoAbility(GameEventId.POWER_CHANGED, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex cardIndex)
        {
            return !isOwnCard(cardIndex) && EventPowerChanged.getDataNewValue() <= 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex cardIndex)
        {
            millDeck(getOpponent(), new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).except(getCardIndex()).getValidTargetsCount());
        }
    }
}
