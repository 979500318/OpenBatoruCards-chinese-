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
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.GameEvent;

import java.util.HashMap;
import java.util.Map;

public final class LRIGA_K2_AlfouSewnIvy extends Card {

    public LRIGA_K2_AlfouSewnIvy()
    {
        setImageSets("WXDi-P15-041");

        setOriginalName("アルフォウソーンアイビー");
        setAltNames("Arufou Soon Aibii");
        setDescription("jp",
                "@E：このターン、次にあなたがダメージを受ける場合、代わりにあなたのデッキの上からカードを３枚トラッシュに置く。\n" +
                "@E @[手札を２枚捨てる]@：このターン、次にあなたがダメージを受ける場合、代わりにあなたのデッキの上からカードを３枚トラッシュに置く。\n" +
                "@E %K %X：このターン、次にあなたがダメージを受ける場合、代わりにあなたのデッキの上からカードを３枚トラッシュに置く。"
        );

        setName("en", "Alfou Thorn Ivy");
        setDescription("en",
                "@E: The next time you would take damage this turn, instead put the top three cards of your deck into your trash.\n@E @[Discard two cards]@: The next time you would take damage this turn, instead put the top three cards of your deck into your trash.\n@E %K %X: The next time you would take damage this turn, instead put the top three cards of your deck into your trash.\n"
        );
        
        setName("en_fan", "Alfou Sewn Ivy");
        setDescription("en_fan",
                "@E: This turn, the next time you would be damaged, instead put the top 3 cards of your deck into the trash.\n" +
                "@E @[Discard 2 cards from your hand]@: This turn, the next time you would be damaged, instead put the top 3 cards of your deck into the trash.\n" +
                "@E %K %X: This turn, the next time you would be damaged, instead put the top 3 cards of your deck into the trash."
        );

		setName("zh_simplified", "阿尔芙荆棘常春藤");
        setDescription("zh_simplified", 
                "@E :这个回合，下一次你受到伤害的场合，作为替代，从你的牌组上面把3张牌放置到废弃区。\n" +
                "@E 手牌2张舍弃:这个回合，下一次你受到伤害的场合，作为替代，从你的牌组上面把3张牌放置到废弃区。\n" +
                "@E %K%X:这个回合，下一次你受到伤害的场合，作为替代，从你的牌组上面把3张牌放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
            registerEnterAbility(new DiscardCost(2), this::onEnterEff);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)), this::onEnterEff);
        }

        private Map<Ability,ChronoRecord> mapRecords = new HashMap<>();
        private void onEnterEff()
        {
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
            addPlayerRuleCheck(PlayerRuleCheckType.ACTION_OVERRIDE, getOwner(), record, data ->
                new OverrideAction(GameEventId.DAMAGE, OverrideScope.GLOBAL, OverrideFlag.MANDATORY, this::onAttachedConstEffModOverrideCond, this::onAttachedConstEffModOverrideHandler)
            );
            mapRecords.put(getAbility(), record);
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
            list.getAction(1).setOnActionCompleted(() -> mapRecords.computeIfPresent(sourceAbilityRC, (ability,record) -> {
                record.forceExpire();
                return null;
            }));
        }
    }
}

