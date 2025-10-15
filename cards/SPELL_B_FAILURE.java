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
import open.batoru.data.ability.SpellAbility;

public final class SPELL_B_FAILURE extends Card {
    
    public SPELL_B_FAILURE()
    {
        setImageSets("WXDi-P08-068");
        setLinkedImageSets("WXDi-P08-042","WXDi-P08-053","WXDi-P08-069");
        
        setOriginalName("FAILURE");
        setAltNames("フェイラー Feiraa");
        setDescription("jp",
                "このスペルはあなたの場に《羅星姫　タマゴ//メモリア》か《羅星　ノヴァ//メモリア》か《翠魔　バン//メモリア》があるか、対戦相手の手札が１枚以下の場合にしか使用できない。\n\n" +
		        "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。" +
		        "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "FAILURE");
        setDescription("en",
                "This spell can only be used if there is a \"Tamago//Memoria, Natural Planet Queen\", \"Nova//Memoria, Natural Planet\" or \"Bang//Memoria, Jade Evil\" on your field, or if your opponent has one or fewer cards in their hand.\n\n" +
                "Target SIGNI on your opponent's field gets --8000 power until end of turn." +
                "~#Down target SIGNI on your opponent's field and freeze it. Your opponent discards a card."
        );
        
        setName("en_fan", "FAILURE");
        setDescription("en_fan",
                "This spell can only be used if there is \"Tamago//Memoria, Natural Star Princess\", \"Nova//Memoria, Natural Star\", or \"Bang//Memoria, Verdant Devil\" on your field, or there are 1 or less cards in your opponent's hand.\n\n" +
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "FAILURE");
        setDescription("zh_simplified", 
                "这张魔法只有在你的场上有《羅星姫　タマゴ//メモリア》或《羅星　ノヴァ//メモリア》或《翠魔　バン//メモリア》或，对战对手的手牌在1张以下的场合才能使用。\n" +
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。" +
                "~#对战对手的精灵1只作为对象，将其#D并冻结。对战对手把手牌1张舍弃。\n"
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
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            spell.setCondition(this::onSpellEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onSpellEffCond()
        {
            return getHandCount(getOpponent()) <= 1 ||
                   new TargetFilter().own().SIGNI().withName("羅星姫　タマゴ//メモリア", "羅星　ノヴァ//メモリア", "翠魔　バン//メモリア").getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()));
        }
        private void onSpellEff()
        {
            gainPower(spell.getTarget(), -8000, ChronoDuration.turnEnd());
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
