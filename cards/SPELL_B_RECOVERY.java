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
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.CoinCost;

public final class SPELL_B_RECOVERY extends Card {
    
    public SPELL_B_RECOVERY()
    {
        setImageSets("WXDi-P07-077");
        
        setOriginalName("RECOVERY");
        setAltNames("リカバリー Rikabarii");
        setDescription("jp",
                "@[ベット]@ ― #C\n\n" +
                "カードを２枚引き、手札を２枚捨てる。あなたがベットしていた場合、代わりにカードを３枚引き、手札を２枚捨てる。\n" +
                "このターン、あなたはスペルを使用できない。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをデッキの一番下に置く。"
        );
        
        setName("en", "RECOVERY");
        setDescription("en",
                "Bet -- #C \n\n" +
                "Draw two cards and discard two cards. If you made a bet, instead draw three cards and discard two cards.\n" +
                "You cannot use spells this turn." +
                "~#You may discard a card. If you do, put target upped SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "RECOVERY");
        setDescription("en_fan",
                "@[Bet]@ - #C\n\n" +
                "Draw 2 cards, and discard 2 cards from your hand. If you bet, instead draw 3 cards, and discard 2 cards from your hand.\n" +
                "This turn, you can't use spells." +
                "~#Target 1 of your opponent's upped SIGNI, and you may discard 1 card from your hand. If you do, put it on the bottom of their deck."
        );
        
		setName("zh_simplified", "RECOVERY");
        setDescription("zh_simplified", 
                "下注—#C（这张魔法使用时，可以作为使用费用追加把#C:支付）\n" +
                "抽2张牌，手牌2张舍弃。你下注的场合，作为替代，抽3张牌，手牌2张舍弃。\n" +
                "这个回合，你不能把魔法使用。" +
                "~#对战对手的竖直状态的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            spell = registerSpellAbility(this::onSpellEff);
            spell.setBetCost(new CoinCost(1));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            draw(!spell.hasUsedBet() ? 2 : 3);
            discard(2);
            
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_USE_SPELL, getOwner(), ChronoDuration.turnEnd(), data -> RuleCheckState.BLOCK);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().upped()).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
    }
}
