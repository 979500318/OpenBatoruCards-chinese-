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
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneWall;

public final class ARTS_K_AbyssNostalgia extends Card {

    public ARTS_K_AbyssNostalgia()
    {
        setImageSets("WX24-P1-010", "WX24-P1-010U");

        setOriginalName("アビス・ノスタルジア");
        setAltNames("アビスノスタルジア Abisu Nosutarujia");
        setDescription("jp",
                "あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。それの@E能力は発動しない。\n" +
                "&E４枚以上@0追加で、このターン、次にあなたがダメージを受ける場合、代わりにあなたのデッキの上からカードを３枚トラッシュに置く。"
        );

        setName("en", "Abyss Nostalgia");
        setDescription("en",
                "Target 1 SIGNI from your trash, and put it onto the field. Its @E abilities don't activate.\n" +
                "&E4 or more@0 Additionally, this turn, the next time you would be damaged, instead put the top 3 cards of your deck into the trash."
        );

		setName("zh_simplified", "深渊·怀念");
        setDescription("zh_simplified", 
                "从你的废弃区把精灵1张作为对象，将其出场。其的@E能力不能发动。\n" +
                "&E4张以上@0追加，这个回合，下一次你受到伤害的场合，作为替代，从你的牌组上面把3张牌放置到废弃区。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff).setRecollect(4);
        }
        
        private ChronoRecord record;
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().playable().fromTrash()).get();
            putOnField(target, Enter.DONT_ACTIVATE);

            if(getAbility().isRecollectFulfilled())
            {
                record = new ChronoRecord(ChronoDuration.turnEnd());
                GFX.attachToChronoRecord(record, new GFXZoneWall(getOwner(),CardLocation.LIFE_CLOTH, "generic", new int[]{205,50,205}));
                
                addPlayerRuleCheck(PlayerRuleCheckType.ACTION_OVERRIDE, getOwner(), record, data ->
                    new OverrideAction(GameEventId.DAMAGE, OverrideScope.GLOBAL, OverrideFlag.MANDATORY, this::onAttachedConstEffModOverrideCond, this::onAttachedConstEffModOverrideHandler)
                );
            }
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return getDeckCount(getOwner()) >= 3;
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
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


