package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionReturnToDeck;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class ARTS_B_AfterTheCarrotOperation extends Card {

    public ARTS_B_AfterTheCarrotOperation()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-003", Mask.VERTICAL+"WX25-CP1-003U");

        setOriginalName("ニンジン作戦の後に");
        setAltNames("ニンジンサクセンノアトニ Ninjin Sakusen no Atoni");
        setDescription("jp",
                "カードを３枚引く。その後、対戦相手のシグニ１体を対象とし、あなたの手札から＜ブルアカ＞のカードを好きな枚数公開する。ターン終了時まで、それのパワーをこの方法で公開したカード１枚につき－3000する。\n" +
                "&E４枚以上@0追加で対戦相手のすべてのシグニを凍結し、次の対戦相手のターン終了時まで、それらのシグニは@>@C：このシグニがエナゾーンに置かれる場合、代わりにデッキの一番下に置かれる。@@を得る。"
        );

        setName("en", "After the Carrot Operation");
        setDescription("en",
                "Draw 3 cards. Then, target 1 of your opponent's SIGNI, and you may reveal any number of <<Blue Archive>> cards from your hand. Until end of turn, it gets --3000 power for each card revealed this way.\n" +
                "&E4 or more@0 Freeze all of your opponent's SIGNI, and until the end of your opponent's next turn, they gain:" +
                "@>@C: If this SIGNI would be put in the ener zone, it is put on the bottom of the deck instead."
        );

		setName("zh_simplified", "胡萝卜作战之后");
        setDescription("zh_simplified", 
                "抽3张牌。然后，对战对手的精灵1只作为对象，从你的手牌把<<ブルアカ>>牌任意张数公开。直到回合结束时为止，其的力量依据这个方法公开的牌的数量，每有1张就-3000。\n" +
                "&E4张以上@0追加对战对手的全部的精灵冻结，直到下一个对战对手的回合结束时为止，这些精灵得到\n" +
                "@>@C :这只精灵放置到能量区的场合，作为替代，放置到牌组最下面。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

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
        
        private void onARTSEff()
        {
            draw(3);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.REVEAL).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromHand());
                if(reveal(data) > 0)
                {
                    gainPower(target, -3000 * data.size(), ChronoDuration.turnEnd());
                    
                    addToHand(data);
                }
            }
            
            if(getAbility().isRecollectFulfilled())
            {
                forEachSIGNIOnField(getOpponent(), cardIndex -> {
                    freeze(cardIndex);
                    
                    ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                        new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER,OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideCond, this::onAttachedConstEffModOverrideHandler)
                    ));
                    GFX.attachToAbility(attachedConst, new GFXCardTextureLayer(cardIndex, new GFXTextureCardCanvas("border/bottom", 0.75,3)));
                    attachAbility(cardIndex, attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
                });
            }
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return ((EventMove)event).getMoveLocation() == CardLocation.ENER;
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionReturnToDeck(list.getSourceEvent().getCallerCardIndex(), DeckPosition.BOTTOM));
        }
    }
}
