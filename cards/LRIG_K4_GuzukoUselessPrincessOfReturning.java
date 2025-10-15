package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_K4_GuzukoUselessPrincessOfReturning extends Card {
    
    public LRIG_K4_GuzukoUselessPrincessOfReturning()
    {
        setImageSets("WDK04-001");
        setLinkedImageSets("WDK04-006");

        setOriginalName("再来の駄姫　グズ子");
        setAltNames("サイライノダキグズコ Sairai no Daki Guzuko");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、あなたの場に《いっしょにあーや！》がいる場合、%K %Kを支払ってもよい。そうした場合、このターン、対戦相手は手札から#Gを持つカードを追加で１枚捨てないかぎり【ガード】ができない。\n" +
                "@E：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。\n" +
                "@A $T1 -M -A %K0：あなたのデッキの一番上のカードをトラッシュに置く。"
        );

        setName("en", "Guzuko, Useless Princess of Returning");
        setDescription("en",
                "@U: Whenever this LRIG attacks, if there is a \"Together with Aya!\" on your field, you may pay %K %K. If you do, this turn, your opponent can't [[Guard]] unless they discard 1 additional card with #G @[Guard]@ from their hand.\n" +
                "@E: Target 1 SIGNI from your trash, and add it to your hand.\n" +
                "@A $T1 -M -A %K0: Put the top card of your deck into the trash."
        );

		setName("zh_simplified", "再来的驮姬 迟钝子");
        setDescription("zh_simplified", 
                "@U :当这只分身攻击时，你的场上有《いっしょにあーや！》的场合，可以支付%K %K。这样做的场合，这个回合，如果对战对手不从手牌把持有#G的牌追加1张舍弃，那么不能[[防御]]。\n" +
                "@E :从你的废弃区把精灵1张作为对象，将其加入手牌。\n" +
                "@A $T1 主要阶段攻击阶段%K0:你的牌组最上面的牌放置到废弃区。\n"
        );

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setLevel(4);
        setLimit(11);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
            act.setActiveUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        }
        
        private void onAutoEff()
        {
            if(getKeys(getOwner()).stream().anyMatch(cardIndex -> cardIndex.getCardReference().getOriginalName().equals("いっしょにあーや！")) &&
               payEner(Cost.color(CardColor.BLACK, 2)))
            {
                addPlayerRuleCheck(PlayerRuleCheckType.COST_TO_GUARD, getOpponent(), ChronoDuration.turnEnd(), data ->
                    new DiscardCost(0,1, new TargetFilter(TargetHint.GUARD).SIGNI().guard())
                );
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
        
        private void onActionEff()
        {
            millDeck(1);
        }
    }
}
