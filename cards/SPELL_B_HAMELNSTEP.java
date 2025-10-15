package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.RuleCheckMustRuleCheckBeBlocked;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SPELL_B_HAMELNSTEP extends Card {
    
    public SPELL_B_HAMELNSTEP()
    {
        setImageSets("WXDi-P05-068");
        setLinkedImageSets("WXDi-P05-037");
        
        setOriginalName("HAMELN STEP");
        setAltNames("ハーメルンステップ Haamerun Suteppu");
        setDescription("jp",
                "カードを２枚引き、あなたの手札から《大罠　ハーメルン》を１枚まで場に出す。その後、あなたの《大罠　ハーメルン》１体を対象とし、ターン終了時まで、それは@>@C：このシグニのアタックはこのシグニの効果によって無効にされない。@@を得る。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをデッキの一番下に置く。"
        );
        
        setName("en", "HAMELN STEP");
        setDescription("en",
                "Draw two cards and put up to one \"Hameln, Master Trickster\" from your hand onto your field. Then, target \"Hameln, Master Trickster\" on your field gains@>@C: This SIGNI's attack is not negated by this SIGNI's effect.@@until end of turn." +
                "~#You may discard a card. If you do, put target upped SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "HAMELN STEP");
        setDescription("en_fan",
                "Draw 2 cards, and put 1 \"Hameln, Great Trap\" from your hand onto the field. Then, target 1 of your \"Hameln, Great Trap\", and until end of turn, it gains:" +
                "@>@C: This SIGNI's attack can't be disabled by its effect.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and you may discard 1 card from your hand. If you do, put it on the bottom of their deck."
        );
        
		setName("zh_simplified", "HAMELN STEP");
        setDescription("zh_simplified", 
                "抽2张牌，从你的手牌把《大罠　ハーメルン》1张最多出场。然后，你的《大罠　ハーメルン》1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :这只精灵的攻击不会因为这只精灵的效果无效。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerSpellAbility(this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            draw(2);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withName("大罠　ハーメルン").fromHand().playable()).get();
            putOnField(cardIndex);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withName("大罠　ハーメルン")).get();
            if(target != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.MUST_BE_BLOCKED, this::onAttachedConstEffModRuleCheck));
                attachAbility(target, attachedConst, ChronoDuration.turnEnd());
            }
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(CardRuleCheckData data)
        {
            return RuleCheckMustRuleCheckBeBlocked.getDataSourceRuleCheck(data) ==
                    data.getCardIndex().getIndexedInstance().getRCRegistry().getRuleCheck(CardRuleCheckType.CAN_LAND_ATTACK) ? RuleCheckState.OK : RuleCheckState.IGNORE;
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
