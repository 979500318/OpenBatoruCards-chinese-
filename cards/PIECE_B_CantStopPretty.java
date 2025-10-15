package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class PIECE_B_CantStopPretty extends Card {
    
    public PIECE_B_CantStopPretty()
    {
        setImageSets("WXDi-P07-004");
        
        setOriginalName("Can't Stop Pretty!");
        setAltNames("キャントストッププリティ　Kyanto Sutoppu Puriti");
        setDescription("jp",
                "このゲームの間、あなたのセンタールリグは以下の能力を得る。" +
                "@>@A -M -A @[エクシード４]@：対戦相手のシグニ１体を対象とし、対戦相手が手札を３枚捨てないかぎり、それをダウンする。"
        );
        
        setName("en", "Can't Stop Pretty!");
        setDescription("en",
                "Your Center LRIG gains the following ability for the duration of the game. \n" +
                "@>@A -M -A @[Exceed 4]@: Down target SIGNI on your opponent's field unless your opponent discards three cards."
        );
        
        setName("en_fan", "Can't stop Pretty!");
        setDescription("en_fan",
                "This game, your center LRIG gains:" +
                "@>@A -M -A @[Exceed 4]@: Target 1 of your opponent's SIGNI, and down it unless they discard 3 cards from their hand."
        );
        
		setName("zh_simplified", "Can’t stop Pretty!");
        setDescription("zh_simplified", 
                "这场游戏期间，你的核心分身得到以下的能力。（成长后的新的核心分身依然得到能力）\n" +
                "@>@A 主要阶段:攻击阶段@[超越 4]@（从你的分身的下面把牌合计4张放置到分身废弃区）对战对手的精灵1只作为对象，如果对战对手不把手牌3张舍弃，那么将其#D。@@\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().LRIG(), new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.permanent());
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(4), this::onAttachedActionEff);
            attachedAct.setActiveUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
            
            return attachedAct;
        }
        private void onAttachedActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
            
            if(getAbility().getSourceCardIndex().getIndexedInstance().discard(getOpponent(), 0,3, ChoiceLogic.BOOLEAN).size() != 3)
            {
                getAbility().getSourceCardIndex().getIndexedInstance().down(target);
            }
        }
    }
}
