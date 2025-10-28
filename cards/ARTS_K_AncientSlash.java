package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionMillDeck;
import open.batoru.core.gameplay.actions.ActionRequestInfoDeck;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.game.gfx.GFXZoneWall;

public final class ARTS_K_AncientSlash extends Card {

    public ARTS_K_AncientSlash()
    {
        setImageSets("WX25-P1-010", "WX25-P1-010U");

        setOriginalName("アンシエント・スラッシュ");
        setAltNames("アンシエントスラッシュ Anshiento Surasshu");
        setDescription("jp",
                "@[ブースト]@ -- %K %X\n\n" +
                "このターン、次にあなたがシグニによってダメージを受ける場合、代わりにあなたのデッキの上からカードを３枚トラッシュに置く。あなたがブーストしていた場合、対戦相手のデッキの上からカードを８枚トラッシュに置く。"
        );

        setName("en", "Ancient Slash");
        setDescription("en",
                "@[Boost]@ -- %K %X\n\n" +
                "This turn, the next time you would be damaged by a SIGNI, put the top 3 cards of your deck into the trash instead. If you used Boost, put the top 8 cards of your opponent's deck into the trash."
        );

        setName("zh_simplified", "远古·斩击");
        setDescription("zh_simplified", 
                "赋能—%K%X（这张必杀使用时，可以作为使用费用追加把%K%X支付）\n" +
                "这个回合，下一次你因为精灵受到伤害的场合，作为替代，从你的牌组上面把3张牌放置到废弃区。你赋能的场合，从对战对手的牌组上面把8张牌放置到废弃区。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setAdditionalCost(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)));
        }
        
        private ChronoRecord record;
        private void onARTSEff()
        {
            record = new ChronoRecord(ChronoDuration.turnEnd());
            GFXZoneWall.attachToChronoRecord(record, new GFXZoneWall(getOwner(),CardLocation.LIFE_CLOTH, "generic", new int[]{205,50,205}));
            
            addPlayerRuleCheck(PlayerRuleCheckType.ACTION_OVERRIDE, getOwner(), record, data ->
                new OverrideAction(GameEventId.DAMAGE, OverrideScope.GLOBAL, OverrideFlag.MANDATORY, this::onRuleCheckModOverrideCond, this::onRuleCheckModOverrideHandler)
            );
            
            if(arts.hasPaidAdditionalCost())
            {
                millDeck(getOpponent(), 8);
            }
        }
        private boolean onRuleCheckModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return getDeckCount(getOwner()) >= 3 && CardType.isSIGNI(event.getSource().getCardReference().getType());
        }
        private void onRuleCheckModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
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

