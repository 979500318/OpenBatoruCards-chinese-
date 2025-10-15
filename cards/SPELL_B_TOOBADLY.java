package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SPELL_B_TOOBADLY extends Card {

    public SPELL_B_TOOBADLY()
    {
        setImageSets("WX24-D3-25", "SPDi37-06");

        setOriginalName("TOO BADLY");
        setAltNames("トゥーバッドリィ Tuu Baddorii");
        setDescription("jp",
                "カードを１枚引き、対戦相手は手札を１枚捨てる。&E５枚以上@0代わりにカードを１枚引き、対戦相手の手札を１枚見ないで選び、捨てさせる。" +
                "~#：対戦相手のルリグかシグニ１体を対象とする。このターン、それがアタックしたとき、対戦相手が手札を３枚捨てないかぎり、そのアタックを無効にする。"
        );

        setName("en", "TOO BADLY");
        setDescription("en",
                "Draw 1 card, and your opponent discards 1 card from their hand. &E5 or more@0 Instead, draw 1 card, and choose 1 card from your opponent's hand without looking, and discard it." +
                "~#Target 1 of your opponent's LRIG or SIGNI. This turn, whenever it attacks, disable that attack unless your opponent discards 3 cards from their hand."
        );

		setName("zh_simplified", "TOO BADLY");
        setDescription("zh_simplified", 
                "抽1张牌，对战对手把手牌1张舍弃。&E5张以上@0作为替代，抽1张牌，不看对战对手的手牌选1张，舍弃。" +
                "~#对战对手的分身或精灵1只作为对象。这个回合，当其攻击时，如果对战对手不把手牌3张舍弃，那么那次攻击无效。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerSpellAbility(this::onSpellEff).setRecollect(5);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEff()
        {
            draw(1);
            if(!getAbility().isRecollectFulfilled())
            {
                discard(getOpponent(), 1);
            } else {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().fromField()).get();
            
            if(target != null)
            {
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                GFX.attachToChronoRecord(record, new GFXCardTextureLayer(target, new GFXTextureCardCanvas("border/discard", 0.75,3)));
                addCardRuleCheck(CardRuleCheckType.COST_TO_LAND_ATTACK, target, record, data -> new DiscardCost(0,3, ChoiceLogic.BOOLEAN));
            }
        }
    }
}
