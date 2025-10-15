package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.AbilityORCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class LRIG_G3_HitoeThirdFormula extends Card {

    public LRIG_G3_HitoeThirdFormula()
    {
        setImageSets("WX24-P2-026", "WX24-P2-026U");

        setOriginalName("参式　一衣");
        setAltNames("サンシキヒトエ Sanshiki Hitoe");
        setDescription("jp",
                "@C：あなたが【ガード】する際、#Gを持つカードを１枚捨てる代わりにあなたのエナゾーンから＜植物＞のシグニ１枚をトラッシュに置いてもよい。\n" +
                "@A $G1 @[@|本当の気持ち|@]@ %G0：各プレイヤーは手札をすべてエナゾーンに置く。その後、あなたは自分のエナゾーンからシグニを１枚まで対象とし、それを手札に加える。"
        );

        setName("en", "Hitoe, Third Formula");
        setDescription("en",
                "@C: You may [[Guard]] by putting 1 <<Plant>> SIGNI from your ener zone into the trash instead of discarding 1 card with #G @[Guard]@.\n" +
                "@A $G1 @[@|True Feelings|@]@ %G0: Each player puts all cards from their hand into the ener zone. Then, target up to 1 SIGNI from your ener zone, and add it to hand."
        );

		setName("zh_simplified", "叁式 一衣");
        setDescription("zh_simplified", 
                "@C 你[[防御]]时，把持有#G的牌1张舍弃，作为替代，可以从你的能量区把<<植物>>精灵1张放置到废弃区。\n" +
                "@A $G1 真正心意%G0:各玩家把手牌全部放置到能量区。然后，你从自己的能量区把精灵1张最多作为对象，将其加入手牌。\n"
        );

        setLRIGType(CardLRIGType.HITOE);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD, TargetFilter.HINT_OWNER_OWN, data -> 
                new AbilityORCost(AbilityORCost.REPLACE_DEFAULT, new TrashCost(0,1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.PLANT).fromEner()))
            ));
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("True Feelings");
        }
        
        private void onActionEff()
        {
            putInEner(getCardsInHand(getOwner()));
            putInEner(getCardsInHand(getOpponent()));
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
    }
}
