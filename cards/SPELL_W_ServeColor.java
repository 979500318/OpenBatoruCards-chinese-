package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SPELL_W_ServeColor extends Card {
    
    public SPELL_W_ServeColor()
    {
        setImageSets("WXDi-P07-059");
        
        setOriginalName("サーブ・カラー");
        setAltNames("サーブカラー Saabu Koraa");
        setDescription("jp",
                "@[ベット]@ ― #C\n\n" +
                "カードを１枚引く。その後、色１つを宣言する。あなたのシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは$$1を得る。あなたがベットしていた場合、代わりに$$1を得る。\n" +
                "$$1@>@C：対戦相手のターンの間、これは[[シャドウ（{{宣言された色のシグニ$%1のシグニ}}）]]を得る。@@" +
                "$$2@>@C：対戦相手のターンの間、これは[[シャドウ（{{宣言された色$%1}}）]]を得る。@@" +
                "~#：対戦相手のアップ状態のシグニを１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Serve Color");
        setDescription("en",
                "Bet -- #C\n\n" +
                "Draw a card. Then, declare a color. Target SIGNI on your field gains $$1 until the end of your opponent's next end phase. If you made a bet, instead it gains $$2 until the end of your opponent's next end phase.\n" +
                "$$1@>@C: During your opponent's turn, this SIGNI gains [[Shadow -- {{SIGNI with declared color$%1 SIGNI}}]].@@" +
                "$$2@>@C: During your opponent's turn, this SIGNI gains [[Shadow -- {{Declared color$%1}}]].@@" +
                "~#Return target upped SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Serve Color");
        setDescription("en_fan",
                "@[Bet]@ - #C\n\n" +
                "Draw 1 card. Then, declare a color. Target 1 of your SIGNI, and until the end of your opponent's next turn, it gains $$1. If you bet, instead it gains $$2.\n" +
                "$$1@>@C: During your opponent's turn, this SIGNI gains [[Shadow ({{SIGNI of the declared color$%1 SIGNI}})]].@@" +
                "$$2@>@C: During your opponent's turn, this SIGNI gains [[Shadow ({{the declared color$%1}})]].@@" +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "奉献·色彩");
        setDescription("zh_simplified", 
                "下注—#C\n" +
                "抽1张牌。然后，颜色1种宣言。你的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到\n" +
                "$$1 。你下注的场合，作为替代，得到\n" +
                "$$2 。\n" +
                "@>@C :对战对手的回合期间，其得到[[暗影（宣言的颜色的精灵）]]。@@\n" +
                "@>@C :对战对手的回合期间，其得到[[暗影（宣言的颜色）]]@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        
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
            
            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            spell.setBetCost(new CoinCost(1));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()));
        }
        private void onSpellEff()
        {
            draw(1);
            
            if(spell.getTarget() != null)
            {
                CardColor color = playerChoiceColor();
                boolean hasUsedBet = spell.hasUsedBet();
                
                ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(cardIndex -> {
                    return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(cardIndexSource -> {
                        return (hasUsedBet || CardType.isSIGNI(cardIndexSource.getCardReference().getType())) &&
                                cardIndexSource.getIndexedInstance().getColor().matches(color) ? ConditionState.OK : ConditionState.BAD;
                    }, color::getLabel));
                }));
                if(hasUsedBet) attachedConst.setNestedDescriptionOffset(1);
                attachedConst.setDynamicAbilityDescription(color::getLabel);
                attachedConst.setCondition(this::onAttachedConstEffCond);
                
                attachAbility(spell.getTarget(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private ConditionState onAttachedConstEffCond(CardIndex cardIndex)
        {
            return !cardIndex.getIndexedInstance().isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}
