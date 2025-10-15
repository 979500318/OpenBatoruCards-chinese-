package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

import java.util.List;

public final class LRIG_W3_DonaSUN extends Card {
    
    public LRIG_W3_DonaSUN()
    {
        setImageSets("WXDi-P07-009", "WXDi-P07-009U");
        
        setOriginalName("ドーナ　ＳＵＮ");
        setAltNames("ドーナサン Doona San");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのレベル３の白のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは@>@C：対戦相手のターンの間、このシグニは[[シャドウ（シグニ）]]を得る。@@を得る。\n" +
                "@A $G1 %W0：あなたのトラッシュからそれぞれ共通する色を持つシグニ２枚を対象とし、それらを手札に加える。"
        );
        
        setName("en", "Dona SUN");
        setDescription("en",
                "@U: At the beginning of your attack phase, target level three white SIGNI on your field gains@>@C: During your opponent's turn, this SIGNI gains [[Shadow -- SIGNI]].@@until the end of your opponent's next end phase.\n" +
                "@A $G1 %W0: Add two target SIGNI that share a color from your trash to your hand. "
        );
        
        setName("en_fan", "Dona SUN");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your level 3 white SIGNI, and until the end of your opponent's next turn, it gains:" +
                "@>@C: During your opponent's turn, this SIGNI gains [[Shadow (SIGNI)]].@@" +
                "@A $G1 %W0: Target 2 SIGNI that share a common color from your trash, and add them to your hand."
        );
        
		setName("zh_simplified", "多娜 SUN");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的等级3的白色的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到\n" +
                "@>@C :对战对手的回合期间，这只精灵得到[[暗影（精灵）]]。@@\n" +
                "@A $G1 %W0:从你的废弃区把持有共通颜色的精灵2张作为对象，将这些加入手牌。（无色不含有颜色）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.DONA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);
        setCoins(+2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withLevel(3).withColor(CardColor.WHITE)).get();
            
            if(target != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
                attachedConst.setCondition(this::onAttachedConstEffCond);
                
                attachAbility(target, attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private ConditionState onAttachedConstEffCond(CardIndex cardIndex)
        {
            return !cardIndex.getIndexedInstance().isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onActionEff()
        {
            DataTable<CardIndex> data = playerTargetCard(2, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor().fromTrash(), this::onActionEffTargetCond);
            addToHand(data);
        }
        private boolean onActionEffTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() < 2 || listPickedCards.get(0).getIndexedInstance().getColor().matches(listPickedCards.get(1).getIndexedInstance().getColor());
        }
    }
}
