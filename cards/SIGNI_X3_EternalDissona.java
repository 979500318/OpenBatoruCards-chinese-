package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.ExcludeCost;
import open.batoru.data.ability.events.EventTarget;
import open.batoru.data.ability.stock.StockAbilityMultiEner;

public final class SIGNI_X3_EternalDissona extends Card {

    public SIGNI_X3_EternalDissona()
    {
        setImageSets("WXDi-P13-089");

        setOriginalName("夢限//ディソナ");
        setAltNames("ムゲンディソナ Mugen Disona");
        setDescription("jp",
                "@C：[[マルチエナ]]\n" +
                "@U $T1：対戦相手のターンの間、このシグニが対戦相手のシグニの、能力か効果の対象になったとき、そのシグニが場にある場合、そのシグニをゲームから除外する。\n" +
                "@A @[手札とエナゾーンとトラッシュにある《夢限//ディソナ》を１枚ずつゲームから除外する]@：対戦相手のシグニ１体を対象とし、それをゲームから除外する。"
        );

        setName("en", "Mugen//Dissona");
        setDescription("en",
                "@C: [[Multi Ener]]\n@U $T1: During your opponent's turn, when this SIGNI becomes the target of an ability or effect of your opponent's SIGNI, if that SIGNI is on your opponent's field, remove it from the game.\n@A @[Remove a \"Mugen//Dissona\" in your hand, your Ener Zone, and your trash from the game]@: Remove target SIGNI on your opponent's field from the game. "
        );
        
        setName("en_fan", "Eternal//Dissona");
        setDescription("en_fan",
                "@C: [[Multi Ener]]\n" +
                "@U $T1: During your opponent's turn, when this SIGNI is targeted by the ability or effect of your opponent's SIGNI on the field, if that SIGNI is still on the field, exclude it from the game.\n" +
                "@A @[Exclude 1 \"Mugen//Dissona\" from your hand, ener zone, and trash each from the game]@: Target 1 of your opponent's SIGNI, and exclude it from the game."
        );
        
		setName("zh_simplified", "梦限//失调");
        setDescription("zh_simplified", 
                "@C :[[万花色]]\n" +
                "@U $T1 :对战对手的回合期间，当这只精灵被作为对战对手的精灵的，能力或效果的对象时，那只精灵在场上的场合，那只精灵从游戏除外。\n" +
                "@A 手牌和能量区和废弃区的《夢限//ディソナ》各1张从游戏除外:对战对手的精灵1只作为对象，将其从游戏除外。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ORIGIN);
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

            registerStockAbility(new StockAbilityMultiEner());

            AutoAbility auto = registerAutoAbility(GameEventId.TARGET, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerActionAbility(new AbilityCostList(
                new ExcludeCost(new TargetFilter().SIGNI().withName("夢限//ディソナ").fromHand()),
                new ExcludeCost(new TargetFilter().SIGNI().withName("夢限//ディソナ").fromEner()),
                new ExcludeCost(new TargetFilter().SIGNI().withName("夢限//ディソナ").fromTrash())
            ), this::onActionEff);
        }

        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                   getEvent().getSourceCardIndex().isSIGNIOnField() &&
                   EventTarget.getDataSourceTargetRole() != getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(getEvent().getSourceCardIndex().isSIGNIOnField())
            {
                exclude(getEvent().getSourceCardIndex());
            }
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.EXCLUDE).OP().SIGNI()).get();
            exclude(target);
        }
    }
}
