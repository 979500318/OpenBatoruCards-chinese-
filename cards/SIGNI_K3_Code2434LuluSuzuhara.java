package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class SIGNI_K3_Code2434LuluSuzuhara extends Card {
    
    public SIGNI_K3_Code2434LuluSuzuhara()
    {
        setImageSets("WXDi-P00-041");
        
        setOriginalName("コード２４３４　鈴原るる");
        setAltNames("コードニジサンジスズハラルル Koodo Nijisanji Suzuhara Ruru");
        setDescription("jp",
                "=T ＜さんばか＞\n" +
                "^C：あなたのライフクロスはリフレッシュによってトラッシュに移動しない。\n" +
                "@A $T1 %K %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－１００００する。あなたのデッキの上からカードを１０枚トラッシュに置く。"
        );
        
        setName("en", "Lulu Suzuhara, Code 2434");
        setDescription("en",
                "=T <<Sanbaka>>\n" +
                "^C: You do not move cards from your Life Cloth to your trash due to refreshing.\n" +
                "@A $T1 %K %X: Target SIGNI on your opponent's field gets --10000 power until end of turn. Put the top ten cards of your deck into the trash."
        );
        
        setName("en_fan", "Code 2434 Lulu Suzuhara");
        setDescription("en_fan",
                "=T <<Sanbaka>>\n" +
                "^C: Your life cloth don't move to the trash by refreshing.\n" +
                "@A $T1 %K %X: Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power. Put the top 10 cards of your deck into the trash."
        );
        
		setName("zh_simplified", "2434代号 铃原露露");
        setDescription("zh_simplified", 
                "=T<<さんばか>>\n" +
                "^C:你的生命护甲不会因为重构往废弃区移动。\n" +
                "@A $T1 %K%X:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。从你的牌组上面把10张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond,
                new PlayerRuleCheckModifier<>(PlayerRuleCheckType.MUST_TRASH_LC_ON_REFRESH, TargetFilter.HINT_OWNER_OWN, this::onConstEffModRuleCheck)
            );
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onConstEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.SANBAKA) ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return RuleCheckState.BLOCK;
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(cardIndex, -10000, ChronoDuration.turnEnd());
            
            millDeck(10);
        }
    }
}
