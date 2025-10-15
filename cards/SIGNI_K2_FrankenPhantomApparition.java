package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.actions.ActionMillDeck;
import open.batoru.core.gameplay.actions.ActionRequestInfoDeck;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.events.GameEvent;

public final class SIGNI_K2_FrankenPhantomApparition extends Card {

    public SIGNI_K2_FrankenPhantomApparition()
    {
        setImageSets("WX25-P1-106");

        setOriginalName("幻怪　フランケン");
        setAltNames("ゲンカイフランケン Genkai Furanken");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたがアーツを使用していた場合、対戦相手のデッキの上からカードを３枚トラッシュに置く。" +
                "~#：このターン、次にあなたがダメージを受ける場合、代わりにあなたのデッキの上からカードを３枚トラッシュに置く。"
        );

        setName("en", "Franken, Phantom Apparition");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you used ARTS this turn, put the top 3 cards of your opponent's deck into the trash." +
                "~#This turn, the next time you would be damaged, instead put the top 3 cards of your deck into the trash."
        );

		setName("zh_simplified", "幻怪 弗兰肯");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合你把必杀使用过的场合，从对战对手的牌组上面把3张牌放置到废弃区。" +
                "~#这个回合，下一次你受到伤害的场合，作为替代，从你的牌组上面把3张牌放置到废弃区。（牌组在2张以下的场合，不能置换）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller())) > 0)
            {
                millDeck(getOpponent(), 3);
            }
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
