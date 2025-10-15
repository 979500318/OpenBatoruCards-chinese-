package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;
import open.batoru.data.ability.stock.StockAbilityRide;
import open.batoru.game.gfx.GFXCardTextureColor;

public final class LRIG_R4_LaylaDeadEnd extends Card {

    public LRIG_R4_LaylaDeadEnd()
    {
        setImageSets("WXK01-001");

        setOriginalName("レイラ＝デッドエンド");
        setAltNames("レイラデッドエンド Reira Deddo Endo");
        setDescription("jp",
                "=I\n" +
                "@C：あなたのドライブ状態のシグニのパワーを＋3000し、それらは【ダブルクラッシュ】を得る。\n" +
                "@A $G1 @[@|ドーピング|@]@ #C #C：手札を２枚捨てる。この方法で手札を２枚捨てた場合、ターン終了時まで、このルリグは@>@C：対戦相手の効果を受けない。@@を得、このターン、対戦相手は【ガード】ができない。"
        );

        setName("en", "Layla-Dead End");
        setDescription("en",
                "=I\n" +
                "@C: All of your SIGNI in the drive state get +3000 power, and gain [[Double Crush]].\n" +
                "@A $G1 @[@|Doping|@]@ #C #C: Discard 2 cards from your hand. If you do, until end of turn, this LRIG gains:" +
                "@>@C: This LRIG is unaffected by your opponent's effects.@@" +
                "and, this turn, your opponent can't [[Guard]]."
        );

		setName("zh_simplified", "蕾拉=致死终结");
        setDescription("zh_simplified", 
                "[[骑乘]]\n" +
                "@C :你的驾驶状态的精灵的力量+3000，这些得到[[双重击溃]]。\n" +
                "@A $G1 兴奋剂#C #C:手牌2张舍弃。这样做的场合，直到回合结束时为止，这只分身得到\n" +
                "@>@C :不受对战对手的效果影响@@\n" +
                "，这个回合，对战对手不能[[防御]]。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setLevel(4);
        setLimit(11);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerStockAbility(new StockAbilityRide());
            
            registerConstantAbility(new TargetFilter().own().SIGNI().drive(), new PowerModifier(3000), new AbilityGainModifier(this::onConstEffSharedModGetSample));

            ActionAbility act = registerActionAbility(new CoinCost(2), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Doping");
        }
        
        private Ability onConstEffSharedModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityDoubleCrush());
        }
        
        private ConditionState onActionEffCond()
        {
            return getHandCount(getOwner()) >= 2 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            DataTable<CardIndex> data = discard(2);
            
            if(data.size() == 2)
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_AFFECTED, this::onAttachedConstEffModRuleCheck));
                GFXCardTextureColor.attachToAbility(attachedConst, new GFXCardTextureColor(getCardIndex(), new int[]{255,130,255}));
                attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
                
                addPlayerRuleCheck(PlayerRuleCheckType.CAN_GUARD, getOpponent(), ChronoDuration.turnEnd(), dataRC -> RuleCheckState.BLOCK);
            }
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(CardRuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}
