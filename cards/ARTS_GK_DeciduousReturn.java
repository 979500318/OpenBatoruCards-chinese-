package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionMillDeck;
import open.batoru.core.gameplay.actions.ActionRequestInfoDeck;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class ARTS_GK_DeciduousReturn extends Card {

    public ARTS_GK_DeciduousReturn()
    {
        setImageSets("WX24-P4-009","WX24-P4-009U");

        setOriginalName("落葉帰根");
        setAltNames("リバーサルリーフ Ribaasaru Riifu Reversal Reef");
        setDescription("jp",
                "あなたのトラッシュにあるすべてのカードをデッキに加えてシャッフルする。このターン、あなたのライフクロスが対戦相手のシグニによってクラッシュされる場合、代わりにあなたのデッキの上からカードを１０枚トラッシュに置いてもよい。"
        );

        setName("en", "Deciduous Return");
        setDescription("en",
                "Shuffle all cards from your trash into the deck. This turn, if your life cloth would be crushed by your opponent's SIGNI, you may put the top 10 cards of your deck into the trash instead."
        );

        setName("zh_simplified", "落叶归根");
        setDescription("zh_simplified", 
                "你的废弃区的全部的牌加入牌组洗切。这个回合，你的生命护甲因为对战对手的精灵被击溃的场合，作为替代，可以从你的牌组上面把10张牌放置到废弃区。（牌组在9张以下的场合，不能置换）"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN, CardColor.BLACK);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.color(CardColor.BLACK, 1));
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

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            returnToDeck(getCardsInTrash(getOwner()), DeckPosition.TOP);
            shuffleDeck();

            ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.CRUSH, OverrideScope.GLOBAL, OverrideFlag.NON_MANDATORY, this::onAttachedConstEffModOverrideCond,this::onAttachedConstEffModOverrideHandler))
            );
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return getDeckCount(getOwner()) >= 10 && !isOwnCard(event.getSourceCardIndex()) && CardType.isSIGNI(event.getSource().getCardReference().getType());
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionRequestInfoDeck(getOwner(), 10, DeckPosition.TOP));
            list.getAction(0).setOnActionCompleted(() ->
                    ((ActionMillDeck)list.getAction(1)).setDataDeckInfo((DataTable<CardIndex>)list.getAction(0).getDataTable())
            );
            list.addAction(new ActionMillDeck(getOwner(), DeckPosition.TOP));
        }
    }
}
