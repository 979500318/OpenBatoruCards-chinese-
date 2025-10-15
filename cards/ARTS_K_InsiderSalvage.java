package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_K_InsiderSalvage extends Card {

    public ARTS_K_InsiderSalvage()
    {
        setImageSets("SP26-004", "WXK01-005");

        setOriginalName("インサイダー・サルベージ");
        setAltNames("インサイダーサルベージ Insaidaa Sarubeeji");
        setDescription("jp",
                "あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。この方法で黒のシグニを手札に加えた場合、このカードをルリグデッキに戻し、このターン、あなたは《インサイダー・サルベージ》を使用できない。"
        );

        setName("en", "Insider Salvage");
        setDescription("en",
                "Target 1 SIGNI from your trash, and add it to your hand. If you added a black SIGNI to your hand this way, put this card into your LRIG deck, and this turn, you can't use \"Insider Salvage\"."
        );

		setName("zh_simplified", "黑幕·营救");
        setDescription("zh_simplified", 
                "从你的废弃区把精灵1张作为对象，将其加入手牌。这个方法把黑色的精灵加入手牌的场合，这张牌返回分身牌组，这个回合，你不能把《インサイダー・サルベージ》使用。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            boolean wasBlack = target.getIndexedInstance().getColor().matches(CardColor.BLACK);
            if(addToHand(target) && wasBlack)
            {
                returnToDeck(getCardIndex(), DeckPosition.TOP);
                
                addPlayerRuleCheck(PlayerRuleCheckType.CAN_USE_ARTS, getOwner(), ChronoDuration.turnEnd(), data ->
                    data.getSourceCardIndex().getCardReference().getOriginalName().equals("インサイダー・サルベージ") ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
                );
            }
        }
    }
}
