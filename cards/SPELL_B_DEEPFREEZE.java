package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.CostRuleCheck;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.AbilityORCost;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SPELL_B_DEEPFREEZE extends Card {

    public SPELL_B_DEEPFREEZE()
    {
        setImageSets("WXDi-P13-075");

        setOriginalName("DEEP FREEZE");
        setAltNames("ディープフリーズ Diipu Furiizu");
        setDescription("jp",
                "対戦相手のすべてのシグニを凍結する。次の対戦相手のアップフェイズに、対戦相手が手札を１枚捨てるか%Xを支払わないかぎり、対戦相手のセンタールリグはアップしない。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。対戦相手は手札を１枚捨てる。"
        );

        setName("en", "DEEP FREEZE");
        setDescription("en",
                "Freeze all SIGNI on your opponent's field. During your opponent's next up phase, your opponent's Center LRIG does not up unless they discard a card or pay %X." +
                "~#Down target SIGNI on your opponent's field and freeze it. Your opponent discards a card."
        );
        
        setName("en_fan", "DEEP FREEZE");
        setDescription("en_fan",
                "Freeze all of your opponent's SIGNI. During your opponent's next up phase, your opponent's center LRIG doesn't up unless they discard 1 card from their hand or pay %X." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "DEEP　FREEZE");
        setDescription("zh_simplified", 
                "对战对手的全部的精灵冻结。下一个对战对手的竖直阶段，如果对战对手不把手牌1张舍弃或支付%X，那么对战对手的核心分身不能竖直。" +
                "~#对战对手的精灵1只作为对象，将其#D并冻结。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

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
            freeze(getSIGNIOnField(getOpponent()));
            
            addPlayerRuleCheck(PlayerRuleCheckType.COST_TO_UP, getOpponent(), ChronoDuration.nextPhaseEnd(getOpponent(), GamePhase.UP), data ->
                CostRuleCheck.getCardIndex(data) == getLRIG(getOpponent()) ? new AbilityORCost(new DiscardCost(1), new EnerCost(Cost.colorless(1))) : null
            );
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            discard(getOpponent(), 1);
        }
    }
}
