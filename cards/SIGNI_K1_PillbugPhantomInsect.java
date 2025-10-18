package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionMillDeck;
import open.batoru.core.gameplay.actions.ActionRequestInfoDeck;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.events.GameEvent;

public final class SIGNI_K1_PillbugPhantomInsect extends Card {

    public SIGNI_K1_PillbugPhantomInsect()
    {
        setImageSets("WX25-P2-099");

        setOriginalName("幻蟲　ダンゴムシ");
        setAltNames("ゲンチュウダンゴムシ Genchuu Dangomushi");
        setDescription("jp",
                "@A %X @[このシグニを場からトラッシュに置く]@：あなたのトラッシュからレベル２以上の＜凶蟲＞のシグニ１枚を対象とし、それを場に出す。" +
                "~#：このターン、次にあなたがダメージを受ける場合、代わりにあなたのデッキの上からカードを３枚トラッシュに置く。"
        );

        setName("en", "Pillbug, Phantom Insect");
        setDescription("en",
                "@A %X @[Put this SIGNI from the field into the trash]@: Target 1 level 2 or higher <<Misfortune Insect>> SIGNI from your trash, and put it onto the field." +
                "~#This turn, the next time you would be damaged, instead put the top 3 cards of your deck into the trash."
        );

		setName("zh_simplified", "幻虫 团子虫");
        setDescription("zh_simplified", 
                "@A %X这只精灵从场上放置到废弃区:从你的废弃区把等级2以上的<<凶蟲>>精灵1张作为对象，将其出场。" +
                "~#这个回合，下一次你受到伤害的场合，作为替代，从你的牌组上面把3张牌放置到废弃区。（牌组在2张以下的场合，不能置换）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new AbilityCostList(new EnerCost(Cost.colorless(1)), new TrashCost()), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(2,0).withClass(CardSIGNIClass.MISFORTUNE_INSECT).fromTrash().playable()).get();
            putOnField(target);
        }
        
        private ChronoRecord record;
        private void onLifeBurstEff()
        {
            record = new ChronoRecord(ChronoDuration.turnEnd());
            addPlayerRuleCheck(PlayerRuleCheckType.ACTION_OVERRIDE, getOwner(), record, data ->
                new OverrideAction(GameEventId.DAMAGE, OverrideScope.GLOBAL, OverrideFlag.MANDATORY, this::onRuleCheckOverrideCond, this::onRuleCheckOverrideHandler)
            );
        }
        private boolean onRuleCheckOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return getDeckCount(getOwner()) >= 3;
        }
        private void onRuleCheckOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionRequestInfoDeck(getOwner(), 3, DeckPosition.TOP));
            list.getAction(0).setOnActionCompleted(() -> {
                DataTable<?> data = list.getAction(0).getDataTable();
                if(data.size() == 3) ((ActionMillDeck)list.getAction(1)).setDataDeckInfo((DataTable<CardIndex>)data);
            });
            list.addAction(new ActionMillDeck(getOwner(), DeckPosition.TOP));
            list.getAction(1).setOnActionCompleted(() -> record.forceExpire());
        }
    }
}
